package ioc;

import ioc.beanService.beanDefinitions.BeanDefinition;
import ioc.beanService.beanGenerators.BeanGeneratorEntyPoint;
import ioc.beanService.beanInitializers.BeanInitializerEntryPoint;
import ioc.exception.MultipleBeanMatch;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class JavaConfAppContext implements BeanFactory {

    private Map<BeanDefinition, Object> context;
    private BeanGeneratorEntyPoint beanGeneratorEntyPoint = new BeanGeneratorEntyPoint();
    private BeanInitializerEntryPoint beanInitializerEntryPoint = new BeanInitializerEntryPoint();


    public JavaConfAppContext(List<Class<?>> config) {
        context = parseConfigToContext(config);
    }

    @Override
    public <T> T getBean(String beanName) {
        return (T) context.keySet().stream()
            .filter(beanDefinition -> beanDefinition.getName().equals(beanName))
            .findAny()
            .map(this::getBeanObject)
            .orElse(null);
    }

    @Override
    public <T> T getBean(Class<T> clazz) {
        List<BeanDefinition> beanDefinitions = context.keySet().stream()
            .filter(beanDefinition -> beanDefinition.isAssignableFrom(clazz))
            .collect(Collectors.toList());
        if (beanDefinitions.size() > 1) {
            throw new MultipleBeanMatch();
        }
        return (T) getBeanObject(beanDefinitions.get(0));
    }

    private Object getBeanObject(BeanDefinition beanDefinition) {
      return   context.computeIfAbsent(beanDefinition,bdef ->  beanGeneratorEntyPoint.getInstance(bdef, this));
    }

    @Override
    public Optional<BeanDefinition> getBeanDefinition(String beanName) {
        return context.keySet().stream()
            .filter(beanDefinition -> beanDefinition.getName().equals(beanName))
            .findAny();
    }

    @Override
    public List<String> getBeanDefinitionNames() {
        return context.keySet().stream()
            .map(BeanDefinition::getName)
            .collect(Collectors.toList());
    }

    private Map<BeanDefinition, Object> parseConfigToContext(List<Class<?>> config) {
        Map<BeanDefinition, Object> beanDefinitionObjectHashMap = new HashMap<>();
        beanInitializerEntryPoint.initialize(config)
            .forEach(beanDefinition -> beanDefinitionObjectHashMap.put(beanDefinition, null));
        return beanDefinitionObjectHashMap;
    }


}
