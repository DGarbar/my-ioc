package data.beanService.beanInitializers;

import data.beanService.beanDefinitions.RepositoryBeanDefinition;
import data.dataService.sqlParser.MethodNameSqlParser;
import data.dataService.sqlParser.MethodSqlDefinition;
import ioc.annotation.Repository;
import ioc.beanService.beanInitializers.BeanInitializer;
import ioc.util.ReflectionUtil;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import javax.persistence.*;

public class RepositoryBeanInitializer extends BeanInitializer {

	@Override
	public RepositoryBeanDefinition getBeanDefinition(Class<?> clazz) {
		String beanName = getBeanName(clazz);
		List<MethodSqlDefinition> methodDefinitionsByClass = getMethodDefinitionsByClass(clazz);
		return new RepositoryBeanDefinition(beanName, clazz, methodDefinitionsByClass);
	}

	@Override
	public boolean validate(Class<?> candidate) {
		return ReflectionUtil.isClassContainsAnnotation(candidate, Repository.class);
	}

	private List<MethodSqlDefinition> getMethodDefinitionsByClass(Class<?> c) {
		return Arrays.stream(c.getMethods())
			.map(this::getMethodDefinitionByMethod)
			.collect(Collectors.toList());
	}

	private MethodSqlDefinition getMethodDefinitionByMethod(Method method) {
		BiFunction<EntityManager, Object[], Object> entityManagerExecuteFunction =
			MethodNameSqlParser.getEntityManagerExecuteFunction(method);
		return new MethodSqlDefinition(method.getName(),
			entityManagerExecuteFunction);
	}

	private String getBeanName(Class<?> clazz) {
		return clazz.getAnnotation(Repository.class).name();
	}

}
