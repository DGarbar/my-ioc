package ioc.beanService.beanGenerators;

import ioc.JavaConfAppContext;
import ioc.beanService.beanDefinitions.BeanDefinition;
import ioc.beanService.beanDefinitions.ComponentBeanDefinition;
import ioc.beanService.beanDefinitions.RepositoryBeanDefinition;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.*;

public class BeanGeneratorEntyPoint {

    private ComponentBeanGenerator componentBeanGenerator;
    private RepositoryBeanGenerator repositoryBeanGenerator;

    public BeanGeneratorEntyPoint() {
        this.repositoryBeanGenerator = new RepositoryBeanGenerator();
        componentBeanGenerator = new ComponentBeanGenerator();
    }

    public Object getInstance(BeanDefinition beanDefinition,
        JavaConfAppContext javaConfAppContext) {
        if (beanDefinition instanceof ComponentBeanDefinition) {
            ComponentBeanDefinition componentBeanDefinition = (ComponentBeanDefinition) beanDefinition;
            return Optional.ofNullable(componentBeanDefinition.getNewInstanceFunction().get())
                .orElseGet(() -> getComponentInstance(componentBeanDefinition,
                    javaConfAppContext));
        } else if (beanDefinition instanceof RepositoryBeanDefinition) {
            return getRepositoryInstance((RepositoryBeanDefinition) beanDefinition,
                javaConfAppContext.getBean(EntityManagerFactory.class));
        }

        throw new RuntimeException("unnable to create an instance");
    }

    private Object getComponentInstance(ComponentBeanDefinition beanDefinition,
        JavaConfAppContext javaConfAppContext) {
        List<Class<?>> constructorParameters = beanDefinition.getConstructorParameters();
        List<Object> parameters = constructorParameters.stream()
            .map(javaConfAppContext::getBean)
            .collect(Collectors.toList());
        return componentBeanGenerator.getInstanceOfBean(beanDefinition, parameters);
    }

    private Object getRepositoryInstance(RepositoryBeanDefinition beanDefinition,
        EntityManagerFactory entityManagerFactory) {
        return repositoryBeanGenerator.getInstanceOfBean(beanDefinition, entityManagerFactory);
    }
}
