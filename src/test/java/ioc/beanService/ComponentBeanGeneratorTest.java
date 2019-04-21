package ioc.beanService;

import ioc.beanService.beanDefinitions.BeanDefinition;
import ioc.beanService.beanGenerators.ComponentBeanGenerator;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import testClasses.ProxyClassBench;

class ComponentBeanGeneratorTest {

//    @Test
//    void getInstanceOfBean() {
//        ComponentBeanGenerator componentBeanGenerator = new ComponentBeanGenerator();
//        BeanDefinition beanDefinition = new BeanDefinition("ClassA", ProxyClassBench.class);
//        ProxyClassBench instanceOfBean = (ProxyClassBench) componentBeanGenerator
//            .getInstanceOfBean(beanDefinition, new ArrayList<>());
//        instanceOfBean.koko();
//        instanceOfBean.voidBench();
//    }
//
//    @Test
//    void getInstanceOfTransactionalBean() {
//        ComponentBeanGenerator componentBeanGenerator = new ComponentBeanGenerator();
//        BeanDefinition beanDefinition = new BeanDefinition("ClassA", ProxyClassBench.class);
//        ProxyClassBench instanceOfBean = (ProxyClassBench) componentBeanGenerator
//            .getInstanceOfBean(beanDefinition, new ArrayList<>());
//        instanceOfBean.tr();
//    }
}