package ioc.beanService.beanInitializers;

import ioc.beanService.beanDefinitions.BeanDefinition;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//SMELLY CODE
public class BeanInitializerEntryPoint {

	private List<BeanInitializer> beanInitializers;

	public BeanInitializerEntryPoint(BeanInitializer... beanInitializers) {
		this.beanInitializers = Arrays.asList(beanInitializers);
	}

	public List<BeanDefinition> initialize(List<Class<?>> classes) {
		return classes.stream()
			.flatMap(aClass -> initializeBean(aClass).stream())
			.collect(Collectors.toList());
	}

//Todo add to DATA SPRING
//	public BeanDefinition initializeEntityManagerFactory() {
//		ComponentBeanDefinition entityManagerFactory = new ComponentBeanDefinition(
//			"entityManagerFactory", EntityManagerFactory.class, Collections.emptyList());
//		entityManagerFactory.setNewInstanceFunction(PersistenceCreator::getEntityManagerFactory);
//		return entityManagerFactory;
//	}

	private Optional<BeanDefinition> initializeBean(Class<?> clazz) {
		return beanInitializers.stream()
			.filter(beanInitializer -> beanInitializer.validate(clazz))
			.map(beanInitializer -> beanInitializer.getBeanDefinition(clazz))
			.findAny();
	}
}
