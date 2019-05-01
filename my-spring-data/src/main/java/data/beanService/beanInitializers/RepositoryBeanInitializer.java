package data.beanService.beanInitializers;

import data.beanService.beanDefinitions.RepositoryBeanDefinition;
import data.dataService.dataBaseConnection.exception.SqlMethodNameParseException;
import data.dataService.repoTypeParser.RepoTypeParser;
import data.dataService.sqlParser.MethodSqlDefinition;
import data.dataService.sqlParser.sqlMethodParsers.FindMethodSqlParser;
import data.dataService.sqlParser.sqlMethodParsers.MethodSqlParser;
import data.dataService.sqlParser.sqlMethodParsers.RemoveMethodSqlParser;
import data.dataService.sqlParser.sqlMethodParsers.SaveMethodSqlParser;
import data.model.RepoType;
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

	private RepoTypeParser repoTypeParser;
	private List<MethodSqlParser> nameSqlParsers;

	public RepositoryBeanInitializer() {
		this.repoTypeParser = new RepoTypeParser();
		nameSqlParsers = List.of(
			new SaveMethodSqlParser(),
			new RemoveMethodSqlParser(),
			new FindMethodSqlParser()
		);
	}

	@Override
	public RepositoryBeanDefinition getBeanDefinition(Class<?> clazz) {
		String beanName = getBeanName(clazz);
		RepoType repoType = getRepoType(clazz);
		List<MethodSqlDefinition> methodDefinitionsByClass = getMethodDefinitionsByClass(clazz,
			repoType);
		return new RepositoryBeanDefinition(beanName, clazz, repoType, methodDefinitionsByClass);
	}

	@Override
	public boolean validate(Class<?> candidate) {
		return ReflectionUtil.isClassContainsAnnotation(candidate, Repository.class);
	}

	private RepoType getRepoType(Class<?> repoClass) {
		return repoTypeParser.getRepoTypeByClass(repoClass);
	}

	private List<MethodSqlDefinition> getMethodDefinitionsByClass(Class<?> c, RepoType repoType) {
		return Arrays.stream(c.getMethods())
			.map((Method method) -> getMethodDefinitionByMethod(method, repoType))
			.collect(Collectors.toList());
	}

	private MethodSqlDefinition getMethodDefinitionByMethod(Method method, RepoType repoType) {
		BiFunction<EntityManager, Object[], Object> entityManagerExecuteFunction = getEntityManagerExecuteFunction(
			method, repoType);
		return new MethodSqlDefinition(method.getName(),
			entityManagerExecuteFunction);
	}

	private BiFunction<EntityManager, Object[], Object> getEntityManagerExecuteFunction(
		Method method, RepoType repoType) {
		return nameSqlParsers.stream()
			.filter(methodNameSqlParser -> methodNameSqlParser.support(method))
			.findFirst()
			.map(methodNameSqlParser -> methodNameSqlParser
				.getEntityManagerExecuteFunction(method, repoType))
			.orElseThrow(() -> new SqlMethodNameParseException(method.toString()));
	}

	private String getBeanName(Class<?> clazz) {
		return clazz.getAnnotation(Repository.class).name();
	}

}
