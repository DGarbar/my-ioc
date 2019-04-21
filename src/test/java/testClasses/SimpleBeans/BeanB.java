package testClasses.SimpleBeans;

import ioc.annotation.Autowired;
import ioc.annotation.Bean;

@Bean(name = "b")
public class BeanB {

    private BeanA beanA;

    @Autowired
    public BeanB(BeanA beanA) {
        this.beanA = beanA;
    }

    public BeanA getBeanA() {
        return beanA;
    }
}
