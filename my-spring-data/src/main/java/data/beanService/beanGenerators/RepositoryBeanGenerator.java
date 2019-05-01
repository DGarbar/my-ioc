package data.beanService.beanGenerators;

import data.beanService.beanDefinitions.RepositoryBeanDefinition;
import data.dataService.sqlParser.MethodSqlDefinition;
import ioc.beanService.beanDefinitions.BeanDefinition;
import ioc.beanService.beanGenerators.BeanGenerator;
import ioc.exception.BadInvocationException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import javax.persistence.*;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class RepositoryBeanGenerator implements BeanGenerator {

	private Supplier<EntityManager> entityManagerSupplier;

	public RepositoryBeanGenerator(Supplier<EntityManager> entityManagerSupplier) {
		this.entityManagerSupplier = entityManagerSupplier;
	}

	@Override
	public boolean supports(BeanDefinition beanDefinition) {
		return beanDefinition instanceof RepositoryBeanDefinition;
	}

	@Override
	public Object getInstanceOfBean(BeanDefinition beanDefinition, List<Object> parameter) {
		RepositoryBeanDefinition originBeanDefinition = getOriginBeanDefinition(beanDefinition);
		Enhancer proxy = getProxy(originBeanDefinition);
		return proxy.create();
	}

	private Enhancer getProxy(RepositoryBeanDefinition beanDefinition) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(beanDefinition.getOriginClass());
		enhancer.setCallback(
			new MethodInterceptor() {
				@Override
				//TODO refactor
				public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy)
					throws Throwable {

					Optional<MethodSqlDefinition> methodSqlDefinition = beanDefinition
						.getMethodSqlDefinitions().stream()
						.filter(methodDeff -> methodDeff.getMethodName()
							.equals(method.getName()))
						.findFirst();
					//WARN after method result can be null SO Optional#OrElse is not appropriate
					if (methodSqlDefinition.isEmpty()) {
						System.out.println(method + " simple");
						return proxy.invokeSuper(obj, args);
					} else {
						System.out.println(method + " IS in transaction");
						Object resss;
						EntityManager entityManager = entityManagerSupplier.get();
						EntityTransaction transaction = entityManager.getTransaction();
						try {
							transaction.begin();
							System.out.println("transaction Begin");
							resss = methodSqlDefinition.get().getMethodInvoke()
								.apply(entityManager, args);
							transaction.commit();
						} catch (Throwable throwable) {
							transaction.rollback();
							throw new BadInvocationException(throwable);
						} finally {
							entityManager.close();
						}
						return resss;
					}
				}
			});
		return enhancer;
	}

	private RepositoryBeanDefinition getOriginBeanDefinition(BeanDefinition beanDefinition) {
		return (RepositoryBeanDefinition) beanDefinition;
	}

}
