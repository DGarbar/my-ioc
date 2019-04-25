package data.beanService.beanDefinitions;

import data.dataService.sqlParser.MethodSqlDefinition;
import ioc.beanService.beanDefinitions.BeanDefinition;
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

    public void setMethodSqlDefinitions(List<MethodSqlDefinition> methodSqlDefinitions) {
        this.methodSqlDefinitions = methodSqlDefinitions;
    }
}
