package ioc;


import ioc.exception.MultipleBeanMatch;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class JavaConfAppContext implements BeanFactory {

    private List<BeanDefinition> context = new ArrayList<>();

    public JavaConfAppContext() {
    }

    public JavaConfAppContext(Map<String, Class<?>> config) {
        context = parseMapToList(config);
    }

    @Override
    public <T> T getBean(String beanName) {
        return (T) getBeanDefinition(beanName)
            .map(BeanDefinition::getBeanInstance)
            .orElse(null);
    }

    @Override
    public <T> T getBean(Class<T> clazz) {
        List<BeanDefinition> beanDefinitions = context.stream()
            .filter(beanDefinition -> beanDefinition.isAssignableFrom(clazz))
            .collect(Collectors.toList());
        if (beanDefinitions.size() > 1) {
            throw new MultipleBeanMatch();
        }
        return (T) beanDefinitions.get(0).getBeanInstance();
    }


    @Override
    public Optional<BeanDefinition> getBeanDefinition(String beanName) {
        return context.stream()
            .filter(beanDefinition -> beanDefinition.getName().equals(beanName))
            .findAny();
    }

    @Override
    public List<String> getBeanDefinitionNames() {
        return context.stream()
            .map(BeanDefinition::getName)
            .collect(Collectors.toList());
    }

    //Check if Map is appropriate
    private List<BeanDefinition> parseMapToList(Map<String, Class<?>> config) {
        return config.entrySet().stream()
            .map(stringClassEntry -> new BeanDefinition(stringClassEntry.getKey(),
                stringClassEntry.getValue()))
            .collect(Collectors.toList());
    }
}
