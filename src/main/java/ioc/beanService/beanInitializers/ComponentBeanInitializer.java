package ioc.beanService.beanInitializers;

import static java.util.stream.Collectors.toList;

import ioc.Util.ReflectionUtil;
import ioc.annotation.Autowired;
import ioc.annotation.Bean;
import ioc.annotation.Init;
import ioc.annotation.PostConstruct;
import ioc.beanService.beanDefinitions.ComponentBeanDefinition;
import ioc.exception.NotAppropriateConstructorProvidedException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ComponentBeanInitializer extends BeanInitializer {

    @Override
    public ComponentBeanDefinition getBeanDefinition(Class<?> clazz) {
        String beanName = getBeanName(clazz);
        List<Class<?>> parametersOfAutowiredConstructor = getParametersOfAutowiredConstructor(
            clazz);
        ComponentBeanDefinition componentBeanDefinition = new ComponentBeanDefinition(beanName,
            clazz, parametersOfAutowiredConstructor);
        componentBeanDefinition.getInitMethod()
            .addAll(getInitMethods(clazz));
        componentBeanDefinition.getPostConstructMethod()
            .addAll(getPostConstructMethods(clazz));
        return componentBeanDefinition;
    }

    @Override
    public boolean validate(Class<?> candidate) {
        return ReflectionUtil.isClassContainsAnnotation(candidate, Bean.class);
    }

    private List<Class<?>> getParametersOfAutowiredConstructor(Class<?> clazz) {
        return getAutowiredConstructor(clazz)
            .or(() -> findEmptyConstructor(clazz))
            .map(this::getConstructorParameterTypes)
            .orElseThrow(NotAppropriateConstructorProvidedException::new);
    }

    private List<Class<?>> getConstructorParameterTypes(Constructor<?> constructor) {
        return Arrays
            .stream(constructor.getParameterTypes())
            .collect(toList());
    }

    private Optional<Constructor<?>> findEmptyConstructor(Class<?> clazz) {
        return Arrays.stream(clazz.getConstructors())
            .filter(constructor -> constructor.getParameterCount() == 0)
            .findAny();
    }

    private Optional<Constructor<?>> getAutowiredConstructor(Class<?> clazz) {
        return Arrays.stream(clazz.getConstructors())
            .filter(constructor -> ReflectionUtil
                .isConstructorContainsAnnotation(constructor, Autowired.class))
            .findAny();
    }


    private List<Method> getInitMethods(Class<?> clazz) {
        return Arrays.stream(clazz.getMethods())
            .filter(method -> ReflectionUtil.isMethodContainsAnnotationName(method, Init.class))
            .filter(method -> method.getParameterCount() == 0)
            .collect(toList());
    }

    private List<Method> getPostConstructMethods(Class<?> clazz) {
        return Arrays.stream(clazz.getMethods())
            .filter(method -> ReflectionUtil
                .isMethodContainsAnnotationName(method, PostConstruct.class))
            .filter(method -> method.getParameterCount() == 0)
            .collect(toList());
    }

    private String getBeanName(Class<?> clazz) {
        return clazz.getAnnotation(Bean.class).name();
    }

}
