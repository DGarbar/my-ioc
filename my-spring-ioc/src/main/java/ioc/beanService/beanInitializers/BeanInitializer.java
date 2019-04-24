package ioc.beanService.beanInitializers;

import ioc.beanService.beanDefinitions.BeanDefinition;

public abstract class BeanInitializer {

	public abstract BeanDefinition getBeanDefinition(Class<?> clazz);

	public abstract boolean validate(Class<?> candidate);
}
