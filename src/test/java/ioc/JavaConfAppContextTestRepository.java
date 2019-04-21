package ioc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ioc.annotation.Transaction;
import ioc.exception.NotClassException;
import java.util.List;
import org.junit.jupiter.api.Test;
import testClasses.SimpleBeans.BeanA;
import testClasses.SimpleBeans.BeanB;
import testClasses.SimpleBeans.RepoBean;

class JavaConfAppContextTestRepository {


    @Test
    public void beanDefIsNotForContext() {
        BeanFactory javaConfAppContext = new JavaConfAppContext(List.of(RepoBean.class));
//        List<String> beanDefinitionNames = javaConfAppContext.getBeanDefinitionNames();
//        assertEquals(2, beanDefinitionNames.size());
    }

    @Test
    public void wrongConfigTypeException() {
        assertThrows(NotClassException.class,
            () -> new JavaConfAppContext(List.of(Transaction.class)));
    }

    @Test
    public void getBeanWithoutDeps() {
        BeanFactory javaConfAppContext = new JavaConfAppContext(List.of(BeanA.class));
        BeanA beanA = javaConfAppContext.getBean(BeanA.class);
        assertNotNull(beanA);
    }

    @Test
    public void getSameBean() {
        BeanFactory javaConfAppContext = new JavaConfAppContext(List.of(BeanA.class));
        Object shorterService1 = javaConfAppContext.getBean("beanA");
        Object shorterService2 = javaConfAppContext.getBean("beanA");
        assertEquals(shorterService1, shorterService2);
    }

    @Test
    public void getMultipleBeanException() {
//        BeanFactory javaConfAppContext = new JavaConfAppContext(List.of(BeanA.class));
//        BeanFactory javaConfAppContext = new JavaConfAppContext(config);
//        assertThrows(MultipleBeanMatch.class, () -> javaConfAppContext.getBean(List.class));
    }

    @Test
    public void tryCreateWithConstructorWithParameters() {
        BeanFactory javaConfAppContext = new JavaConfAppContext(List.of(BeanA.class, BeanB.class));
        BeanB bean = javaConfAppContext.getBean(BeanB.class);
        assertNotNull(bean);
    }

    @Test
    public void createWithConstructorWithParametersVerifyParameter() {
        BeanFactory javaConfAppContext = new JavaConfAppContext(List.of(BeanA.class, BeanB.class));
        BeanB bean = javaConfAppContext.getBean(BeanB.class);
        assertNotNull(bean.getBeanA());
    }

    @Test
    public void checkEqualsOfAutowiredBeanAndGetBean() {
        BeanFactory javaConfAppContext = new JavaConfAppContext(List.of(BeanA.class, BeanB.class));
        BeanB bean = javaConfAppContext.getBean(BeanB.class);
        assertEquals(bean.getBeanA(),javaConfAppContext.getBean(BeanA.class));
    }

//    @Test
//    public void testProxyMethod() {
//        Map<String, Class<?>> config = Map.of("ClassA", ProxyClassBench.class);
//        BeanFactory javaConfAppContext = new JavaConfAppContext(config);
//        ProxyClassBench bean = javaConfAppContext.getBean(ProxyClassBench.class);
////        bean.bench();
////        bean.voidBench();
//
//        bean.not();
//    }


}