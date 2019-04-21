package ioc.beanService.beanInitializers;

import ioc.Util.ReflectionUtil;
import ioc.annotation.Repository;
import ioc.beanService.beanDefinitions.RepositoryBeanDefinition;
import ioc.beanService.beanDefinitions.dataBase.model.MethodSqlDefinition;
import ioc.beanService.dataSql.dataService.MethodNameSqlParser;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import javax.persistence.*;

public class RepositoryBeanInitializer extends BeanInitializer {

    @Override
    public RepositoryBeanDefinition getBeanDefinition(Class<?> clazz) {
        String beanName = getBeanName(clazz);
        List<MethodSqlDefinition> methodDefinitionsByClass = getMethodDefinitionsByClass(clazz);
        return new RepositoryBeanDefinition(beanName, clazz, methodDefinitionsByClass);
    }

    @Override
    public boolean validate(Class<?> candidate) {
        return ReflectionUtil.isClassContainsAnnotation(candidate, Repository.class);
    }

    private List<MethodSqlDefinition> getMethodDefinitionsByClass(Class<?> c) {
        return Arrays.stream(c.getMethods())
            .map(this::getMethodDefinitionByMethod)
            .collect(Collectors.toList());
    }

    private MethodSqlDefinition getMethodDefinitionByMethod(Method method) {
        BiFunction<EntityManager, Object[], Object> entityManagerExecuteFunction = MethodNameSqlParser
            .getEntityManagerExecuteFunction(method);
        return new MethodSqlDefinition(method.getName(),
            entityManagerExecuteFunction);
    }

    private String getBeanName(Class<?> clazz) {
        return clazz.getAnnotation(Repository.class).name();
    }

}
