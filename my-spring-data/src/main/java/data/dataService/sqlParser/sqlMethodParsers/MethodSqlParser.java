package data.dataService.sqlParser.sqlMethodParsers;

import data.model.RepoType;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import javax.persistence.*;

public interface MethodSqlParser {

	BiFunction<EntityManager, Object[], Object> getEntityManagerExecuteFunction(
		Method method, RepoType repoType);

	boolean support(Method method);

	default Supplier<Object> performReturnFromMethod(Method method, TypedQuery<?> typedQuery) {
		if (Collection.class.isAssignableFrom(method.getReturnType())) {
			return typedQuery::getResultList;
		} else {
			return typedQuery::getSingleResult;
		}
	}
}
