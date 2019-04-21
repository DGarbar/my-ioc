package ioc.beanService.beanGenerators;

import ioc.beanService.beanDefinitions.RepositoryBeanDefinition;
import ioc.beanService.beanDefinitions.dataBase.model.MethodSqlDefinition;
import ioc.exception.BadInvocationException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Optional;
import javax.persistence.*;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.FixedValue;
import net.sf.cglib.proxy.InvocationHandler;
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
                public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy)
                    throws Throwable {
                    Optional<String> any = Arrays.stream(Object.class.getMethods())
                        .map(method1 -> method1.getName())
                        .filter(s -> s.equals(method.getName()))
                        .findAny();
                    if (any.isPresent()) {
                        System.out.println(method);
                        Object invoke = method.invoke(proxy, args);
                        System.out.println(invoke);
                        return invoke;
                    }
                    EntityManager entityManager = entityManagerFactory.createEntityManager();
                    EntityTransaction transaction = entityManager.getTransaction();
                    try {
                        transaction.begin();
                        System.out.println("transaction Begin");
                        Object result = beanDefinition.getMethodSqlDefinitions().stream()
                            .filter(methodSqlDefinition -> methodSqlDefinition.getMethodName()
                                .equals(method.getName()))
                            .findFirst()
                            .map(MethodSqlDefinition::getMethodInvoke)
                            .map(func -> func.apply(entityManager, args))
                            //Without this, dont create Proxy
                            .orElse(null);
                        transaction.commit();
                        return result;
                    } catch (Throwable throwable) {
                        transaction.rollback();
                        throw new BadInvocationException(throwable);
                    }
                }
            });
        return enhancer;
    }

}
