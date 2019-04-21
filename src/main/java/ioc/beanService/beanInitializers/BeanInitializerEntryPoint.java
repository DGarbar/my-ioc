package ioc.beanService.beanInitializers;

import ioc.Util.data.PersistenceCreator;
import ioc.beanService.beanDefinitions.BeanDefinition;
import ioc.beanService.beanDefinitions.ComponentBeanDefinition;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.*;

//SMELLY CODE
public class BeanInitializerEntryPoint {

    private List<BeanInitializer> beanInitializers;

    public BeanInitializerEntryPoint() {
        beanInitializers = List.of(new ComponentBeanInitializer(),
            new RepositoryBeanInitializer());
    }

    public List<BeanDefinition> initialize(List<Class<?>> classes) {
        List<BeanDefinition> result = beanInitializers.stream()
            .flatMap(beanInitializer -> initializeBean(classes, beanInitializer).stream())
            .collect(Collectors.toList());
        result.add(initializeEntityManagerFactory());
        return result;
    }

    public BeanDefinition initializeEntityManagerFactory() {
        ComponentBeanDefinition entityManagerFactory = new ComponentBeanDefinition(
            "entityManagerFactory", EntityManagerFactory.class, Collections.emptyList());
        entityManagerFactory.setNewInstanceFunction(PersistenceCreator::getEntityManagerFactory);
        return entityManagerFactory;
    }

    private List<BeanDefinition> initializeBean(List<Class<?>> classes,
        BeanInitializer beanInitializer) {
        return classes.stream()
            .filter(beanInitializer::validate)
            .map(beanInitializer::getBeanDefinition)
            .collect(Collectors.toList());
    }
}
