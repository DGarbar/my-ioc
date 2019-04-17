package ioc.beanService;

import static org.junit.jupiter.api.Assertions.*;

import ioc.BeanDefinition;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import testClasses.ProxyClassBench;

class BeanGeneratorTest {

    @Test
    void getInstanceOfBean() {
        BeanGenerator beanGenerator = new BeanGenerator();
        BeanDefinition beanDefinition = new BeanDefinition("a", ProxyClassBench.class);
        ProxyClassBench instanceOfBean =(ProxyClassBench) beanGenerator.getInstanceOfBean(beanDefinition, new ArrayList<>());
        instanceOfBean.koko();
        instanceOfBean.voidBench();
    }
}