package ioc;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class JavaConfAppContext implements BeanFactory {

    private Map<String, Class<?>> config;
    private Map<String, Object> context = new HashMap<>();

    public JavaConfAppContext(Map<String, Class<?>> config) {
        this.config = config;
    }

    public JavaConfAppContext() {
        this.config = Collections.emptyMap()
        ;
    }

    protected void methodtest(){

    }

    @Override
    public <T> T getBean(String beanName) {
        Class<?> aClass = config.get(beanName);
        return (T) Optional.ofNullable(context.get(beanName))
            .orElseGet(() -> createFromClass(aClass));

    }

    private <T> Optional<T> createFromClass(Class<T> clazz) {
        try {
            Constructor<?>[] constructors = clazz.getConstructors();
            constructors[0].newInstance();
            return (Optional<T>) Optional.of(constructors[0].newInstance());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        return null;
    }

    @Override
    public BeanDefinition[] getBeanDefinitionNames() {
      return config.keySet().stream()
          .map(BeanDefinition::new).toArray(BeanDefinition[]::new);
    }
}
