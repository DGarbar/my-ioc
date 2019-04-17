package ioc;

import ioc.exception.MultipleBeanMatch;
import ioc.exception.NotAppropriateConstructorFound;
import ioc.exception.NotClassException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BeanDefinition {

    private Object instance;
    private final String name;
    private Class clazz;


    public BeanDefinition(String name, Class clazz) {
        validateClass(clazz);
        fillMetaData(clazz);
        this.name = name;
        this.clazz = clazz;
    }

    private void fillMetaData(Class clazz) {
//        init = findInitMethod(clazz);
//        postConstrcut = findPostConstructMethod(clazz);
    }

    public String getName() {
        return name;
    }

//    private Object getBeanInstance(List<BeanDefinition> context) {
//        if (instance == null) {
//            instance = createObjFromClass(context);
//        }
//        return instance;
//    }

    public boolean isAssignableFrom(Class<?> subClass) {
        return subClass.isAssignableFrom(clazz);
    }

//    private Object createObjFromClass(List<BeanDefinition> context) {
//        try {
//            Constructor<?>[] constructors = clazz.getConstructors();
//            Object o = createIfEmptyConstructorProvided(constructors)
//                .orElseGet(() -> createFormConstructor(constructors, context));
//            callInitMethod(o);
////            return getProxy();
//            return o;
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new NotAppropriateConstructorFound();
//        }
//    }

    private void validateClass(Class clazz) {
        if (clazz.isAnnotation() || clazz.isArray() || clazz.isEnum() || clazz.isInterface()) {
            throw new NotClassException();
        }
    }

    public Object getInstance() {
        return instance;
    }

    public Class getOriginClass() {
        return clazz;
    }

}
