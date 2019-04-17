package ioc;

import ioc.exception.NotAppropriateConstructorFound;
import ioc.exception.NotClassException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BeanDefinition {

    private Object instance;
    private final String name;
    private Class clazz;
    private Optional<Method> init;
    private Optional<Method> postConstrcut;

    public BeanDefinition(String name, Class clazz) {
        validateClass(clazz);
        fillMetaData(clazz);
        this.name = name;
        this.clazz = clazz;
    }

    private void fillMetaData(Class clazz) {
        init = findInitMethod(clazz);
        postConstrcut = findPostConstructMethod(clazz);
    }

    public String getName() {
        return name;
    }

    public Object getBeanInstance(List<BeanDefinition> context) {
        if (instance == null) {
            instance = createObjFromClass(context);
        }
        return instance;
    }

    public boolean isAssignableFrom(Class subClass) {
        return subClass.isAssignableFrom(clazz);
    }

    private Object createObjFromClass(List<BeanDefinition> context) {
        try {
            Constructor<?>[] constructors = clazz.getConstructors();
            Object o = createIfEmptyConstructorProvided(constructors)
                .orElseGet(() -> createFormConstructor(constructors, context));
            callInitMethod(o);
            return o;
        } catch (Exception e) {
            e.printStackTrace();
            throw new NotAppropriateConstructorFound();
        }
    }

    private void callInitMethod(Object o) {
        init.ifPresent(method -> {
            try {
                method.invoke(o);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }

    //TODO refactor
    private Object createFormConstructor(Constructor<?>[] constructors,
        List<BeanDefinition> context) {
        for (Constructor<?> constructor : constructors) {
            Optional<Object> obj = createFromConstructorAndProvidedBeans(
                constructor, context);
            if (obj.isPresent()) {
                return obj.get();
            }
        }
        throw new NotAppropriateConstructorFound();
    }

    private Optional<Object> createFromConstructorAndProvidedBeans(Constructor<?> constructor,
        List<BeanDefinition> context) {
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        List<BeanDefinition> beanDefinitions = new ArrayList<>();

        for (int i = 0; i < parameterTypes.length; i++) {
            Class<?> tmp = parameterTypes[i];
            Optional<BeanDefinition> any = findBeanDefinitionByClass(tmp, context);
            if (any.isPresent()) {
                beanDefinitions.add(any.get());
            } else {
                return Optional.empty();
            }
        }

        List<Object> obj = beanDefinitions.stream()
            .map(beanDefinition -> beanDefinition.getBeanInstance(context))
            .collect(Collectors.toList());
        return createObjectFromConstructor(constructor, obj);
    }

    private Optional<BeanDefinition> findBeanDefinitionByClass(Class<?> clazz,
        List<BeanDefinition> context) {
        return context.stream()
            .filter(beanDefinition -> beanDefinition.isAssignableFrom(clazz))
            .findAny();
    }

    private Optional<Object> createIfEmptyConstructorProvided(Constructor<?>[] constructors) {
        return Arrays.stream(constructors)
            .filter(constructor -> constructor.getParameterCount() == 0)
            .flatMap(constructor -> createObjectFromConstructor(constructor).stream())
            .findAny();
    }

    private Optional<Object> createObjectFromConstructor(Constructor<?> constructor,
        Object... parameters) {
        try {
            constructor.newInstance(parameters);
            return Optional.of(constructor.newInstance(parameters));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }


    private void validateClass(Class clazz) {
        if (clazz.isAnnotation() || clazz.isArray() || clazz.isEnum() || clazz.isInterface()) {
            throw new NotClassException();
        }
    }

    private Optional<Method> findInitMethod(Class<?> clazz) {
        return Arrays.stream(clazz.getMethods())
            .filter(method -> method.getName().equals("init"))
            //Todo change
//            .filter(method -> method.getParameterCount() == 0)
            .findAny();
    }


    private Optional<Method> findPostConstructMethod(Class<?> clazz) {
        return Arrays.stream(clazz.getMethods())
            .filter(this::isPostConstructAnnotation)
            //Todo change
//            .filter(method -> method.getParameterCount() == 0)
            .findAny();
    }

    private boolean isPostConstructAnnotation(Method method){
       return Arrays.stream(method.getDeclaredAnnotations())
            .anyMatch(annotation -> annotation.annotationType().getName().endsWith("PostConstruct"));
    }


}
