package data.dataService.sqlParser.sqlMethodParsers;

import data.dataService.dataBaseConnection.exception.SqlMethodNameParseException;
import data.dataService.sqlParser.sqlMethodParsers.util.MethodNameSqlParserUtil;
import data.model.RepoType;
import java.lang.reflect.Method;
import java.util.function.BiFunction;
import javax.persistence.*;
import org.hibernate.annotations.QueryHints;

public class FindMethodSqlParser implements MethodSqlParser {

	@Override
	public BiFunction<EntityManager, Object[], Object> getEntityManagerExecuteFunction(
		Method method, RepoType repoType) {
		String name = method.getName();
		Class<?> entityClass = repoType.getEntityClass();
		if (name.equals("findAll")) {
			return (EntityManager e, Object... param) -> e
				.createQuery("select x from " + repoType.getTableName() + " x", entityClass)
				.setHint(QueryHints.READ_ONLY, true)
				.getResultList();
		} else if (name.equals("findById")) {
			return (EntityManager e, Object... param) -> e.find(entityClass, param[0]);
		} else if (name.contains("By")) {
			return (EntityManager e, Object... param) -> {
				String alias = "x";
				String whereSql = MethodNameSqlParserUtil.getParametersNameBy(method, alias);
				TypedQuery<?> typedQuery = e
					.createQuery("select x from " + repoType.getTableName() + " x " + whereSql,
						entityClass)
					.setHint(QueryHints.READ_ONLY, true);
				for (int i = 0; i < param.length; i++) {
					typedQuery = typedQuery.setParameter(i+1, param[i]);
				}
				return performReturnFromMethod(method, typedQuery).get();
			};
		}
		throw new SqlMethodNameParseException("Cant parse name for method " + method.getName());
	}

	@Override
	public boolean support(Method method) {
		return method.getName().startsWith("find");
	}

}
