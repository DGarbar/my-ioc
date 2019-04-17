package ioc.beanService;

import static java.util.stream.Collectors.toList;

import ioc.BeanDefinition;
import ioc.Util.ReflectionUtil;
import ioc.annotation.Autowired;
import ioc.annotation.Init;
import ioc.annotation.PostConstruct;
import ioc.annotationHandler.AnnotationWrapper;
import ioc.annotationHandler.BenchmarkMethodInterceptor;
import ioc.annotationHandler.KokoMethodInterceptor;
import ioc.exception.BadInvocationException;
import ioc.exception.MultipleBeanMatch;
import ioc.exception.NotAppropriateConstructorProvidedException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class BeanGenerator {

    private List<AnnotationWrapper> wrapperList;

    public BeanGenerator() {
        wrapperList = List.of(
            new BenchmarkMethodInterceptor(),
            new KokoMethodInterceptor());
    }


    public Object getInstanceOfBean(BeanDefinition beanDefinition, List<BeanDefinition> context) {
        Class clazz = beanDefinition.getOriginClass();
        List<Method> initMethods = getInitMethods(clazz);
        List<Method> postConstructMethods = getPostConstructMethods(clazz);
        Enhancer proxy = getProxy(beanDefinition);

        Function<Enhancer, Object> creatableObjectFunction = getCreatableObjectFunction(clazz, context);

        Object obj = creatableObjectFunction.apply(proxy);
        ReflectionUtil.invokeMethod(initMethods,obj);
        ReflectionUtil.invokeMethod(postConstructMethods,obj);
        return obj;
    }

    public Enhancer getProxy(BeanDefinition beanDefinition) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(beanDefinition.getOriginClass());
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) {
                return createMethodWrapper(method, () -> {
                    try {
                        return proxy.invokeSuper(obj, args);
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                        throw new BadInvocationException();
                    }
                }).get();
            }
        });


        return enhancer;
    }


    private Supplier<Object> createMethodWrapper(Method method, Supplier<Object> identity) {
        List<AnnotationWrapper> wrappers =
            Arrays.stream(method.getAnnotations())
            .flatMap((Annotation annotation) -> getWrapperForAnnotation(annotation).stream())
            //ToDO change
//            .reduce(identity,
//                (objectSupplier, annotationWrapper) ->
//                    annotationWrapper.wrap(method, objectSupplier),
//                (objectSupplier, objectSupplier2) -> objectSupplier);
            .collect(toList());
//
        if (wrappers.isEmpty()) {
            return identity;
        }
        AnnotationWrapper annotationWrapper = wrappers.get(0);
        Supplier<Object> prew = annotationWrapper.wrap(method, identity);

        for (int i = 1; i < wrappers.size(); i++) {
            prew = wrappers.get(i).wrap(method,prew);
        }
        return prew;

    }

    private Optional<AnnotationWrapper> getWrapperForAnnotation(Annotation annotation) {
        return wrapperList.stream()
            .filter(annotationWrapper -> annotationWrapper.isEqualsAnnotation(annotation))
            .findAny();

    }


    public Function<Enhancer, Object> getCreatableObjectFunction(Class<?> clazz,
        List<BeanDefinition> parameters) {
        return getAutowiredConstructor(clazz)
            .or(() -> findEmptyConstructor(clazz))
            .map(constructor -> getFunctionWhichCreateProxyByConstructorAndParameters(constructor,
                parameters))
            .orElseThrow(NotAppropriateConstructorProvidedException::new);
    }

    private Function<Enhancer, Object> getFunctionWhichCreateProxyByConstructorAndParameters(
        Constructor<?> constructor, List<BeanDefinition> context) {
        Class<?>[] parameterTypes = constructor.getParameterTypes();

        List<BeanDefinition> beanDefinitionsParameters = Arrays
            .stream(constructor.getParameterTypes())
            .map(c -> findUniqueBeanDefinitionByClass(c, context))
            .collect(toList());

        return (enhancer) -> {
            Object[] parameters = beanDefinitionsParameters.stream()
                .map(beanDefinition -> getInstanceOfBean(beanDefinition, context))
                .toArray(Object[]::new);
            return enhancer.create(parameterTypes, parameters);
        };
    }

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


    public static Optional<Constructor<?>> findEmptyConstructor(Class<?> clazz) {
        return Arrays.stream(clazz.getConstructors())
            .filter(constructor -> constructor.getParameterCount() == 0)
            .findAny();
    }

    private Optional<Constructor<?>> getAutowiredConstructor(Class<?> clazz) {
        return Arrays.stream(clazz.getConstructors())
            .filter(constructor -> ReflectionUtil
                .isConstructorContainsAnnotation(constructor, Autowired.class))
            .findAny();
    }

    private List<Method> getInitMethods(Class<?> clazz) {
        return Arrays.stream(clazz.getMethods())
            .filter(method -> ReflectionUtil.isMethodContainsAnnotationName(method, Init.class))
            .filter(this::validateMethodDontHaveParameters)
            .collect(toList());
    }

    private List<Method> getPostConstructMethods(Class<?> clazz) {
        return Arrays.stream(clazz.getMethods())
            .filter(method -> ReflectionUtil
                .isMethodContainsAnnotationName(method, PostConstruct.class))
            .filter(this::validateMethodDontHaveParameters)
            .collect(toList());
    }

    private boolean validateMethodDontHaveParameters(Method method) {
        return method.getParameterCount() == 0;
    }

}
