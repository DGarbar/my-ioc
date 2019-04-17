package ioc.annotationHandler;

import ioc.Util.ReflectionUtil;
import ioc.annotation.Benchmark;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.function.Supplier;

public class BenchmarkMethodInterceptor implements AnnotationWrapper {

    private static final Class<?> BENCHMARK_CLASS = Benchmark.class;

    public Supplier<Object> wrap(Method method, Supplier<Object> rootInvoke) {
        return () -> {
            Object result;
            if (ReflectionUtil.isMethodContainsAnnotationName(method, BENCHMARK_CLASS)) {
                long start = System.nanoTime();
                result = rootInvoke.get();
                long finish = System.nanoTime();
                System.out.println("diff = " + (finish - start));
            } else {
                result = rootInvoke.get();
            }
            return result;
        };
    }

    public boolean isEqualsAnnotation(Annotation annotationClass){
        return annotationClass.annotationType().equals(BENCHMARK_CLASS);
    }
}
