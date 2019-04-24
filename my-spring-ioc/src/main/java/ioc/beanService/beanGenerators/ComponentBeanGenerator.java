package ioc.beanService.beanGenerators;

import static java.util.stream.Collectors.toList;

import ioc.annotationWrappers.AnnotationMethodWrapper;
import ioc.beanService.beanDefinitions.BeanDefinition;
import ioc.beanService.beanDefinitions.ComponentBeanDefinition;
import ioc.exception.BadInvocationException;
import ioc.util.ReflectionUtil;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

public class ComponentBeanGenerator implements BeanGenerator {

	private List<AnnotationMethodWrapper> wrapperList;

	public ComponentBeanGenerator(AnnotationMethodWrapper... methodInterceptors) {
		wrapperList = Arrays.asList(methodInterceptors);
	}

	public Object getInstanceOfBean(BeanDefinition beanDefinition,
		List<Object> parameters) {
		ComponentBeanDefinition componentBeanDefinition = getOriginBeanDefinition(beanDefinition);
		Class clazz = beanDefinition.getOriginClass();
		Enhancer proxy = getProxy(clazz);

		Object o = getObjectByProxyByBeanDefinitionAndParameters(
			proxy, componentBeanDefinition, parameters);
		ReflectionUtil.invokeMethods(componentBeanDefinition.getInitMethod(), o);
		ReflectionUtil.invokeMethods(componentBeanDefinition.getPostConstructMethod(), o);
		return o;
	}

	private ComponentBeanDefinition getOriginBeanDefinition(BeanDefinition beanDefinition) {
		return (ComponentBeanDefinition) beanDefinition;
	}

	//Smelly
	public boolean canGenerateFromBeanDefinition(BeanDefinition beanDefinition) {
		return beanDefinition instanceof ComponentBeanDefinition;
	}

	private Enhancer getProxy(Class<?> clazz) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(clazz);
		enhancer.setCallback(
			(MethodInterceptor) (obj, method, args, proxy) -> createMethodWrapper(method, () -> {
				try {
					//For transactional we need wrap in our Transactional method
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
}
