package ioc;

import ioc.exception.NotClassException;
import ioc.exception.NotEmptyConstructorProvidedException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class BeanDefinition {

    private Object instance;
    private final String name;
    private Class clazz;

    public BeanDefinition(String name, Class clazz) {
        validateClass(clazz);
        this.name = name;
        this.clazz = clazz;
    }

    public String getName() {
        return name;
    }

    public Object getBeanInstance() {
        if (instance == null) {
            instance = createObjFromClass();
        }
        return instance;
    }

    public boolean isAssignableFrom(Class subClass){
        return subClass.isAssignableFrom(clazz);
    }

    private Object createObjFromClass() {
        try {
            Constructor<?>[] constructors = clazz.getConstructors();
            constructors[0].newInstance();
            return constructors[0].newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new NotEmptyConstructorProvidedException(e);
        }
    }

    private void validateClass(Class clazz) {
        if (clazz.isAnnotation() || clazz.isArray() || clazz.isEnum() || clazz.isInterface()) {
            throw new NotClassException();
        }
    }

}
