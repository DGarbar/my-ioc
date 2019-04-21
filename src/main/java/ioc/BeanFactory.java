package ioc;

import ioc.beanService.beanDefinitions.BeanDefinition;
import java.util.List;
import java.util.Optional;

public interface BeanFactory {

	<T> T getBean(String beanName);

	<T> T getBean(Class<T> clazz);

	Optional<BeanDefinition> getBeanDefinition(String beanName);

	List<String> getBeanDefinitionNames();

}
