package ioc;

import ioc.beanService.beanGenerators.BeanGeneratorEntryPoint;
import ioc.beanService.beanInitializers.BeanInitializerEntryPoint;
import ioc.beanService.beanInitializers.ComponentBeanInitializer;
import ioc.classScannerService.ClassScanner;
import java.util.List;

public class SimpleIocAppContext extends IocBeanFactory {

	private static final BeanGeneratorEntryPoint beanGeneratorEntryPoint = new BeanGeneratorEntryPoint();
	private static final BeanInitializerEntryPoint beanInitializerEntryPoint = new BeanInitializerEntryPoint(
		new ComponentBeanInitializer());

	public SimpleIocAppContext(String basePackage) {
		super(new ClassScanner().getClasses(basePackage),
			beanGeneratorEntryPoint,
			beanInitializerEntryPoint);
	}

	public SimpleIocAppContext() {
		super(beanGeneratorEntryPoint, beanInitializerEntryPoint);
	}

	public SimpleIocAppContext(List<Class<?>> config) {
		super(config, beanGeneratorEntryPoint, beanInitializerEntryPoint);
	}
}
