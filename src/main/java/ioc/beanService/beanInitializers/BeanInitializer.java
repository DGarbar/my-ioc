package ioc.beanService.beanInitializers;

import ioc.Util.ReflectionUtil;
import ioc.annotation.Bean;
import ioc.annotation.Repository;
import ioc.beanService.beanDefinitions.BeanDefinition;
import java.util.Optional;

public abstract class BeanInitializer {


    public abstract BeanDefinition getBeanDefinition(Class<?> clazz);

    public abstract boolean validate(Class<?> candidate);
}
