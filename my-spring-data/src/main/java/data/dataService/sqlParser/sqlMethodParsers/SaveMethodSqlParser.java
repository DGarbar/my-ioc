package data.dataService.sqlParser.sqlMethodParsers;

import data.model.RepoType;
import java.lang.reflect.Method;
import java.util.function.BiFunction;
import javax.persistence.*;

public class SaveMethodSqlParser implements MethodSqlParser {

	@Override
	public BiFunction<EntityManager, Object[], Object> getEntityManagerExecuteFunction(
		Method method, RepoType repoType) {
		return (EntityManager e, Object... param) -> {
			e.persist(param[0]);
			return method.getReturnType().toString().equals("void") ? null : param[0];
		};
	}

	@Override
	public boolean support(Method method) {
		return method.getName().equals("save");
	}
}
