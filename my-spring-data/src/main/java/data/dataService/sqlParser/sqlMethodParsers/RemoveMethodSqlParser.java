package data.dataService.sqlParser.sqlMethodParsers;

import data.model.RepoType;
import java.lang.reflect.Method;
import java.util.function.BiFunction;
import javax.persistence.*;

public class RemoveMethodSqlParser implements MethodSqlParser {

	@Override
	public BiFunction<EntityManager, Object[], Object> getEntityManagerExecuteFunction(
		Method method, RepoType repoType) {
		return (EntityManager e, Object... param) -> {
			e.remove(param);
			return null;
		};
	}

	@Override
	public boolean support(Method method) {
		return method.getName().startsWith("remove");
	}
}
