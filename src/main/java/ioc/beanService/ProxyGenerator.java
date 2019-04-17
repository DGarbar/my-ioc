package ioc.beanService;

import ioc.annotationHandler.AnnotationWrapper;
import ioc.annotationHandler.BenchmarkMethodInterceptor;
import ioc.annotationHandler.KokoMethodInterceptor;
import java.util.List;

public class ProxyGenerator {

    private List<AnnotationWrapper> wrapperList;

    public ProxyGenerator() {
        wrapperList = List.of(new BenchmarkMethodInterceptor(), new KokoMethodInterceptor());
    }

//    public getProxy()
}
