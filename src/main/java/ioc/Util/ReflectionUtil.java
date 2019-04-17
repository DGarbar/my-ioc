package ioc.Util;

import ioc.annotation.Autowired;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ReflectionUtil {

    public static boolean isMethodContainsAnnotationName(Method method, Class<?> annotationClazz) {
        return Arrays.stream(method.getDeclaredAnnotations())
            .anyMatch(annotation -> annotation.annotationType().equals(annotationClazz));
    }

    public static boolean isConstructorContainsAnnotation(Constructor<?> constructor,
        Class<?> annotationClazz) {
        return Arrays.stream(constructor.getDeclaredAnnotations())
            .anyMatch(annotation -> annotation.annotationType().equals(annotationClazz));
    }

    public static void invokeMethod(List<Method> methods, Object o){
        methods.forEach(method -> {
            try {
                method.invoke(o);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }
}



