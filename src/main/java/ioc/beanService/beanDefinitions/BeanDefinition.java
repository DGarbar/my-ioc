package ioc.beanService.beanDefinitions;

import ioc.exception.NotClassException;

public abstract class BeanDefinition {

    private final String name;
    protected Class clazz;

    public BeanDefinition(String name, Class clazz) {
        validateClass(clazz);
        this.name = name;
        this.clazz = clazz;
    }

    public String getName() {
        return name;
    }

    public boolean isAssignableFrom(Class<?> subClass) {
        return subClass.isAssignableFrom(clazz);
    }

    private void validateClass(Class clazz) {
        if (clazz.isAnnotation() || clazz.isArray() || clazz.isEnum()) {
            throw new NotClassException();
        }
    }

    public Class getOriginClass() {
        return clazz;
    }

}
