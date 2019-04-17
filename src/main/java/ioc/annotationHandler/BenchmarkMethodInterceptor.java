package ioc.annotationHandler;

import java.lang.reflect.Method;
import java.util.function.Supplier;

public class BenchmarkMethodInterceptor implements AnnotationWrapper {

    private static final String annotationName = "ioc.annotation.Benchmark";

    public Supplier<Object> wrap(Method method, Supplier<Object> rootInvoke) {
        return () -> {
            Object result;
            if (isMethodContainsAnnotationName(method, annotationName)) {
                long start = System.nanoTime();
                System.out.println(start);
                result = rootInvoke.get();
                long finish = System.nanoTime();
                System.out.println(finish);
                System.out.println(finish - start);
            } else {
                result = rootInvoke.get();
            }
            return result;
        };
    }

//
//    @Override
//    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy)
//        throws Throwable {
//        Object result = null;
//        if (isMethodContainsAnnotationName(method, annotationName)) {
//            long start = System.nanoTime();
//            System.out.println(start);
//            result = proxy.invokeSuper(obj, args);
//            long finish = System.nanoTime();
//            System.out.println(finish);
//            System.out.println(finish - start);
//        } else {
//            proxy.invokeSuper(obj, args);
//        }
//        return result;
}
