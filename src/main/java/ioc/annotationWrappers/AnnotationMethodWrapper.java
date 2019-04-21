package ioc.annotationWrappers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.function.Supplier;

public interface AnnotationMethodWrapper {

    Supplier<Object> wrap(Method method, Supplier<Object> rootInvoke);

    boolean isEqualsAnnotation(Annotation annotationClass);
}
