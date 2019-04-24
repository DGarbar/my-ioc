package ioc.beanService.beanGenerators;

import ioc.beanService.beanDefinitions.BeanDefinition;
import java.util.List;

public interface BeanGenerator {

	Object getInstanceOfBean(BeanDefinition beanDefinition,
		List<Object> parameters);


	public boolean canGenerateFromBeanDefinition(BeanDefinition beanDefinition);
}
