package ioc.beanService.beanDefinitions.dataBase.model;

import java.util.function.BiFunction;
import javax.persistence.*;

public class MethodSqlDefinition {

    private String methodName;
    private BiFunction<EntityManager, Object[], Object> methodInvoke;

    public MethodSqlDefinition(String methodName,
        BiFunction<EntityManager, Object[], Object> methodInvoke) {
        this.methodName = methodName;
        this.methodInvoke = methodInvoke;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public BiFunction<EntityManager, Object[], Object> getMethodInvoke() {
        return methodInvoke;
    }

    public void setMethodInvoke(
        BiFunction<EntityManager, Object[], Object> methodInvoke) {
        this.methodInvoke = methodInvoke;
    }
}
