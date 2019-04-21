package ioc.beanService.beanGenerators;

import ioc.beanService.beanDefinitions.RepositoryBeanDefinition;
import ioc.beanService.beanDefinitions.dataBase.model.MethodSqlDefinition;
import ioc.exception.BadInvocationException;
import java.lang.reflect.Method;
import javax.persistence.*;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class RepositoryBeanGenerator {

    public RepositoryBeanGenerator() {
    }

    public Object getInstanceOfBean(RepositoryBeanDefinition beanDefinition,
        EntityManagerFactory entityManagerFactory) {
        Enhancer proxy = getProxy(beanDefinition,entityManagerFactory);
        return proxy.create();
    }

    private Enhancer getProxy(RepositoryBeanDefinition beanDefinition,
        EntityManagerFactory entityManagerFactory) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(beanDefinition.getOriginClass());
        enhancer.setCallback(
            new MethodInterceptor() {
                @Override
                public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) {
                    EntityManager entityManager = entityManagerFactory.createEntityManager();
                    EntityTransaction transaction = entityManager.getTransaction();
                    try {
                        transaction.begin();
                        Object result = beanDefinition.getMethodSqlDefinitions().stream()
                            .filter(methodSqlDefinition -> methodSqlDefinition.getMethodName()
                                .equals(method.getName()))
                            .findFirst()
                            .map(MethodSqlDefinition::getMethodInvoke)
                            .map(func -> func.apply(entityManager, args))
                            .orElse(null);
                        transaction.commit();
                        return result;
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                        transaction.rollback();
                        throw new BadInvocationException();
                    }
                }
            });
        return enhancer;
    }


}
