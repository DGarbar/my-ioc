package ioc.annotationHandler;

import java.lang.reflect.Method;
import java.util.function.Supplier;

public class KokoMethodInterceptor implements AnnotationWrapper {

    private static final String annotationName = "ioc.annotation.Koko";

    public Supplier<Object> wrap(Method method, Supplier<Object> rootInvoke) {
        return () -> {
            Object result = null;
            if (isMethodContainsAnnotationName(method, annotationName)) {
                result = rootInvoke.get();
                System.out.println("LOKOKOKOKKKOKOKO");
            }
            return result;
        };
    }
//    @Override
//    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy)
//        throws Throwable {
//        Object result = null;
//        if (isMethodContainsAnnotationName(method, annotationName)) {
//            result = proxy.invokeSuper(obj, args);
//            System.out.println("KOKOKO");
//        } else {
//            proxy.invokeSuper(obj, args);
//        }
//        return result;
//    }
}
