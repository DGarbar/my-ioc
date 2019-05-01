package data.beanService.beanDefinitions;

import data.dataService.sqlParser.MethodSqlDefinition;
import data.model.RepoType;
import ioc.beanService.beanDefinitions.BeanDefinition;
import java.util.List;

public class RepositoryBeanDefinition extends BeanDefinition {

	private final RepoType repoType;
	private List<MethodSqlDefinition> methodSqlDefinitions;

    public RepositoryBeanDefinition(String name, Class clazz, RepoType repoType,
        List<MethodSqlDefinition> methodSqlDefinitions) {
        super(name, clazz);
	    this.repoType = repoType;
	    this.methodSqlDefinitions = methodSqlDefinitions;
    }

	public RepoType getRepoType() {
		return repoType;
	}

	public List<MethodSqlDefinition> getMethodSqlDefinitions() {
        return methodSqlDefinitions;
    }
}
