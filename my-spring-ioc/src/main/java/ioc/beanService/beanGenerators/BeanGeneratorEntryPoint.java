package ioc.beanService.beanGenerators;

import ioc.BeanFactory;
import ioc.SimpleIocAppContext;
import ioc.annotationWrappers.BenchmarkMethodInterceptor;
import ioc.annotationWrappers.KokoMethodInterceptor;
import ioc.beanService.beanDefinitions.BeanDefinition;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BeanGeneratorEntryPoint {

	private ComponentBeanGenerator componentBeanGenerator;
	private Set<BeanGenerator> specialBeanGenerators = new HashSet<>();

	public BeanGeneratorEntryPoint() {
		componentBeanGenerator = new ComponentBeanGenerator(
			new BenchmarkMethodInterceptor(),
			new KokoMethodInterceptor());
	}

	public Object getInstance(BeanDefinition beanDefinition,
		BeanFactory beanFactory) {
		BeanGenerator beanGenerator = getBeanGeneratorByBeanDefinition(
			beanDefinition);
		return getComponentInstance(beanGenerator, beanDefinition, beanFactory);
	}

	private Object getComponentInstance(BeanGenerator beanGenerator, BeanDefinition beanDefinition,
		BeanFactory beanFactory) {
		List<Class<?>> constructorParameters = beanDefinition.getConstructorParameters();
		List<Object> parameters = constructorParameters.stream()
			.map(beanFactory::getBean)
			.collect(Collectors.toList());
		return beanGenerator.getInstanceOfBean(beanDefinition, parameters);
	}

	private BeanGenerator getBeanGeneratorByBeanDefinition(BeanDefinition beanDefinition) {
		return specialBeanGenerators.stream()
			.filter(beanGenerator -> beanGenerator.supports(beanDefinition))
			.findAny()
			.orElseGet(() -> componentBeanGenerator);
	}

	public void addBeanGenerator(BeanGenerator beanGenerator) {
		specialBeanGenerators.add(beanGenerator);
	}
}
