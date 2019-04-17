package ioc.annotationHandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.function.Supplier;

public interface AnnotationWrapper {

    Supplier<Object> wrap(Method method, Supplier<Object> rootInvoke);

    boolean isEqualsAnnotation(Annotation annotationClass);
}
