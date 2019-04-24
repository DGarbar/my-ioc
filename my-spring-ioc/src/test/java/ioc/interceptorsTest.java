package ioc;

import ioc.annotationWrappers.BenchmarkMethodInterceptor;
import ioc.exception.BadInvocationException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Supplier;
import org.junit.jupiter.api.Test;

public class interceptorsTest {

    @Test
    void name() throws NoSuchMethodException {
        BenchmarkMethodInterceptor benchmarkMethodInterceptor = new BenchmarkMethodInterceptor();
        Method method = benchmarkMethodInterceptor.getClass().getMethod("wrap");
        Supplier<Object> supplier = () -> {
            try {
                return method.invoke(benchmarkMethodInterceptor);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new BadInvocationException();
            }
        };

    }
}
