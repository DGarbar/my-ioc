package ioc;

public interface BeanFactory {

	<T> T getBean(String beanName);

	ioc.BeanDefinition getBeanDefinition(String beanName);

	BeanDefinition[] getBeanDefinitionNames();

}
