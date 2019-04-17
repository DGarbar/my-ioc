package ioc;


import ioc.beanService.BeanGenerator;
import ioc.exception.MultipleBeanMatch;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class JavaConfAppContext implements BeanFactory {

    private List<BeanDefinition> context = new ArrayList<>();
    private Map<String, Object> createdBean = new HashMap<>();
    private BeanGenerator beanGenerator = new BeanGenerator();

    public JavaConfAppContext() {
    }

    public JavaConfAppContext(Map<String, Class<?>> config) {
        context = parseMapToList(config);
    }

    @Override
    public <T> T getBean(String beanName) {
        Object o = createdBean.get(beanName);
        if (o == null) {
            Object obj = context.stream()
                .filter(beanDefinition -> beanDefinition.getName().endsWith(beanName))
                .findAny()
                .map(beanDefinition -> beanGenerator.getInstanceOfBean(beanDefinition, context))
                .orElse(null);
            if (obj != null) {
                createdBean.put(beanName, obj);
            }
            return (T) obj;
        }
        return (T) o;
    }

    @Override
    public <T> T getBean(Class<T> clazz) {
        List<BeanDefinition> beanDefinitions = context.stream()
            .filter(beanDefinition -> beanDefinition.isAssignableFrom(clazz))
            .collect(Collectors.toList());
        if (beanDefinitions.size() > 1) {
            throw new MultipleBeanMatch();
        }
        BeanDefinition beanDefinition = beanDefinitions.get(0);
        return (T) beanGenerator.getInstanceOfBean(beanDefinition, context);
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
