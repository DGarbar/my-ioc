package ioc.beanService.beanDefinitions;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class ComponentBeanDefinition extends BeanDefinition {

//    private Consumer<Object> beanConstruct;

    private List<Method> initMethod = new ArrayList<>();
    private List<Method> postConstructMethod = new ArrayList<>();
    private List<Class<?>> constructorParameters;
    private Supplier<Object> newInstanceFunction = () -> null;

    public ComponentBeanDefinition(String name, Class clazz,
        List<Class<?>> constructorParameters) {
        super(name, clazz);
        this.constructorParameters = constructorParameters;
    }

    public List<Method> getInitMethod() {
        return initMethod;
    }

    public List<Method> getPostConstructMethod() {
        return postConstructMethod;
    }

    public List<Class<?>> getConstructorParameters() {
        return constructorParameters;
    }


    public Supplier<Object> getNewInstanceFunction() {
        return newInstanceFunction;
    }

    public void setNewInstanceFunction(Supplier<Object> newInstanceFunction) {
        this.newInstanceFunction = newInstanceFunction;
    }
}
