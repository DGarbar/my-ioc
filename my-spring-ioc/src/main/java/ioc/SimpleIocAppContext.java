package ioc;

import ioc.beanService.beanDefinitions.BeanDefinition;
import ioc.beanService.beanGenerators.BeanGeneratorEntryPoint;
import ioc.beanService.beanInitializers.BeanInitializerEntryPoint;
import ioc.beanService.beanInitializers.ComponentBeanInitializer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleIocAppContext extends IocBeanFactory {

	public SimpleIocAppContext(List<Class<?>> config) {
		super(config,
			new BeanGeneratorEntryPoint(),
			new BeanInitializerEntryPoint(new ComponentBeanInitializer()));
	}
}
