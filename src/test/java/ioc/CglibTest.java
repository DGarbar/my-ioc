package ioc;

import ioc.annotationHandler.BenchmarkMethodInterceptor;
import ioc.annotationHandler.KokoMethodInterceptor;
import ioc.exception.BadInvocationException;
import java.lang.reflect.Method;
import java.util.Arrays;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.jupiter.api.Test;
import testClasses.ProxyClassBench;

public class CglibTest {

    @Test
    void name() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(ProxyClassBench.class);
        BenchmarkMethodInterceptor benchmarkMethodInterceptor = new BenchmarkMethodInterceptor();
        KokoMethodInterceptor kokoMethodInterceptor = new KokoMethodInterceptor();

        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy)
                throws Throwable {
                return kokoMethodInterceptor
                    .wrap(method, benchmarkMethodInterceptor.wrap(method, () -> {
                        try {
                            return proxy.invokeSuper(obj, args);
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                            throw new BadInvocationException();
                        }
                    })).get();
            }
        });
        ProxyClassBench proxyClassBench = (ProxyClassBench) enhancer.create();
        System.out.println(proxyClassBench.getClass());
        System.out.println(Arrays.toString(proxyClassBench.getClass().getConstructors()));
    }
}
