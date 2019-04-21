package ioc.beanService.beanGenerators;

import static java.util.stream.Collectors.toList;

import ioc.Util.ReflectionUtil;
import ioc.annotationWrappers.AnnotationMethodWrapper;
import ioc.annotationWrappers.BenchmarkMethodInterceptor;
import ioc.annotationWrappers.KokoMethodInterceptor;
import ioc.annotationWrappers.TransactionMethodWrapper;
import ioc.beanService.beanDefinitions.BeanDefinition;
import ioc.beanService.beanDefinitions.ComponentBeanDefinition;
import ioc.exception.BadInvocationException;
import ioc.exception.MultipleBeanMatch;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javax.persistence.*;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

public class ComponentBeanGenerator {

    private List<AnnotationMethodWrapper> wrapperList;

    public ComponentBeanGenerator() {
        wrapperList = List.of(
            new BenchmarkMethodInterceptor(),
            new KokoMethodInterceptor(),
            new TransactionMethodWrapper(
                Persistence.createEntityManagerFactory("TestManager")));
    }


    public Object getInstanceOfBean(ComponentBeanDefinition beanDefinition,
        List<Object> parameters) {
        Class clazz = beanDefinition.getOriginClass();
        Enhancer proxy = getProxy(clazz);

        Object o = getObjectByProxyByBeanDefinitionAndParameters(
            proxy, beanDefinition, parameters);
        ReflectionUtil.invokeMethods(beanDefinition.getInitMethod(), o);
        ReflectionUtil.invokeMethods(beanDefinition.getPostConstructMethod(), o);
        return o;
    }

    private Enhancer getProxy(Class<?> clazz) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(
            (MethodInterceptor) (obj, method, args, proxy) -> createMethodWrapper(method, () -> {
                try {
                    return proxy.invokeSuper(obj, args);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                    throw new BadInvocationException();
                }
            }).get());
        return enhancer;
    }

    private Supplier<Object> createMethodWrapper(Method method, Supplier<Object> identity) {
        List<AnnotationMethodWrapper> wrappers =
            Arrays.stream(method.getAnnotations())
                .flatMap((Annotation annotation) -> getWrapperForAnnotation(annotation).stream())
                //ToDO change
//            .reduce(identity,
//                (objectSupplier, annotationMethodWrapper) ->
//                    annotationMethodWrapper.wrap(method, objectSupplier),
//                (objectSupplier, objectSupplier2) -> objectSupplier);
                .collect(toList());
//
        if (wrappers.isEmpty()) {
            return identity;
        }
        AnnotationMethodWrapper annotationMethodWrapper = wrappers.get(0);
        Supplier<Object> prew = annotationMethodWrapper.wrap(method, identity);

        for (int i = 1; i < wrappers.size(); i++) {
            prew = wrappers.get(i).wrap(method, prew);
        }
        return prew;
    }

    private Optional<AnnotationMethodWrapper> getWrapperForAnnotation(Annotation annotation) {
        return wrapperList.stream()
            .filter(
                annotationMethodWrapper -> annotationMethodWrapper.isEqualsAnnotation(annotation))
            .findAny();

    }

    private Object getObjectByProxyByBeanDefinitionAndParameters(Enhancer enhancer,
        ComponentBeanDefinition beanDefinition, List<Object> parameters) {
        Class<?>[] arrayParametersClass = beanDefinition.getConstructorParameters()
            .toArray(new Class<?>[0]);
        Object[] arrayParametersObjects = parameters.toArray();
        return enhancer.create(arrayParametersClass, arrayParametersObjects);
    }

//    private Object getObjectByProxyByBeanDefinitionAndParameters(Enhancer enchancer,
//        BeanDefinition beanDefinition, List<Object> parameters) {
//        Class<?>[] parameterTypes = constructor.getParameterTypes();
//
//        List<BeanDefinition> beanDefinitionsParameters = Arrays
//            .stream(constructor.getParameterTypes())
//            .map(c -> findUniqueBeanDefinitionByClass(c, context))
//            .collect(toList());
//
//        return enhancer -> {
//            Object[] parameters = beanDefinitionsParameters.stream()
//                .map(beanDefinition -> getInstanceOfBean(beanDefinition, context))
//                .toArray(Object[]::new);
//            return enhancer.create(parameterTypes, parameters);
//        };
//    }

    private BeanDefinition findUniqueBeanDefinitionByClass(Class<?> clazz,
        List<BeanDefinition> context) {
        List<BeanDefinition> matchingBeanDefinitions = context.stream()
            .filter(beanDefinition -> beanDefinition.isAssignableFrom(clazz))
            .collect(Collectors.toList());
        if (matchingBeanDefinitions.size() == 1) {
            return matchingBeanDefinitions.get(0);
        } else {
            throw new MultipleBeanMatch();
        }
    }
}
