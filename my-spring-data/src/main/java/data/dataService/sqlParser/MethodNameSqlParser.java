package data.dataService.sqlParser;

import java.lang.reflect.Method;
import java.util.function.BiFunction;
import javax.persistence.*;
import org.hibernate.annotations.QueryHints;

//TODO refactor
public class MethodNameSqlParser {

	public static BiFunction<EntityManager, Object[], Object> getEntityManagerExecuteFunction(
		Method method) {
		String name = method.getName();
		Class<?>[] parameterTypes = method.getParameterTypes();
		Class<?> returnType = method.getReturnType();
		if (name.equals("findAll")) {
			return (EntityManager e, Object... param) -> e
				.createQuery("select x from " + getNameOfTable(returnType) + " x", returnType)
				.setHint(QueryHints.READ_ONLY, true)
				.getResultList();
		} else if (name.equals("remove")) {
			return (EntityManager e, Object... param) -> {
				e.remove(param);
				return null;
			};
		} else if (name.equals("findById")) {
			return (EntityManager e, Object... param) -> e.find(returnType, param[0]);
		} else if (name.equals("save")) {
			return (EntityManager e, Object... param) -> {
				e.persist(param[0]);
				return null;
			};
		}
		return null;
	}

	private static String getNameOfTable(Class<?> clazz) {
		String name = clazz.getName();
		int i = name.lastIndexOf(".") + 1;
		return name.substring(i);
	}

}
