package ioc.beanService.beanDefinitions;

import ioc.beanService.beanDefinitions.dataBase.model.MethodSqlDefinition;
import java.util.List;

public class RepositoryBeanDefinition extends BeanDefinition {
    private List<MethodSqlDefinition> methodSqlDefinitions;

    public RepositoryBeanDefinition(String name, Class clazz,
        List<MethodSqlDefinition> methodSqlDefinitions) {
        super(name, clazz);
        this.methodSqlDefinitions = methodSqlDefinitions;
    }

    public List<MethodSqlDefinition> getMethodSqlDefinitions() {
        return methodSqlDefinitions;
    }

    public void setMethodSqlDefinitions(
        List<MethodSqlDefinition> methodSqlDefinitions) {
        this.methodSqlDefinitions = methodSqlDefinitions;
    }
}
