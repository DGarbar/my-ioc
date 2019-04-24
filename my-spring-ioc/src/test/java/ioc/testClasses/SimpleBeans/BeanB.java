package ioc.testClasses.SimpleBeans;

import ioc.annotation.Autowired;
import ioc.annotation.Component;

@Component(name = "b")
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
