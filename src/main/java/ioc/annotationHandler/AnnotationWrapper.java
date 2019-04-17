package ioc.annotationHandler;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Supplier;

public interface AnnotationWrapper {

//    Object addAnnotationCustomLogic(String annotationName, Method method,
//        Supplier<Object> methodExecution);

    default boolean isMethodContainsAnnotationName(Method method, String name) {
        return Arrays.stream(method.getDeclaredAnnotations())
            .anyMatch(annotation -> annotation.annotationType().getName().equals(name));
    }
}
