package data;

import data.annotationWrappers.TransactionMethodWrapper;
import data.beanService.beanGenerators.RepositoryBeanGenerator;
import data.beanService.beanInitializers.RepositoryBeanInitializer;
import data.dataService.dataBaseConnection.PersistenceCreator;
import ioc.IocBeanFactory;
import ioc.annotationWrappers.BenchmarkMethodInterceptor;
import ioc.annotationWrappers.KokoMethodInterceptor;
import ioc.beanService.beanGenerators.BeanGenerator;
import ioc.beanService.beanGenerators.BeanGeneratorEntryPoint;
import ioc.beanService.beanGenerators.ComponentBeanGenerator;
import ioc.beanService.beanInitializers.BeanInitializerEntryPoint;
import ioc.beanService.beanInitializers.ComponentBeanInitializer;
import ioc.classScannerService.ClassScanner;
import java.util.List;
import java.util.Set;

public class DataIocAppContext extends IocBeanFactory {


	private static final PersistenceCreator persistenceCreator = new PersistenceCreator();
	private static final BeanGeneratorEntryPoint beanGeneratorEntryPoint = new BeanGeneratorEntryPoint(
		new ComponentBeanGenerator(
			new BenchmarkMethodInterceptor(),
			new KokoMethodInterceptor(),
			new TransactionMethodWrapper(persistenceCreator::getEntityManager)),
		Set.of(new RepositoryBeanGenerator(persistenceCreator::getEntityManager))
	);

	private static final BeanInitializerEntryPoint beanInitializerEntryPoint =
		new BeanInitializerEntryPoint(
			new ComponentBeanInitializer(),
			new RepositoryBeanInitializer());

	public DataIocAppContext(String basePackage) {
		super(new ClassScanner().getClasses(basePackage),
			beanGeneratorEntryPoint,
			beanInitializerEntryPoint);
	}

	public DataIocAppContext() {
		super(beanGeneratorEntryPoint, beanInitializerEntryPoint);
	}

	public DataIocAppContext(List<Class<?>> config) {
		super(config, beanGeneratorEntryPoint, beanInitializerEntryPoint);
	}
}
