package data.dataService.sqlParser.sqlMethodParsers.util;

import data.dataService.dataBaseConnection.exception.SqlMethodNameParseException;
import java.lang.reflect.Method;

public class MethodNameSqlParserUtil {

	public static String getParametersNameBy(Method method, String alias) {
		String name = method.getName();
		int parameterCount = method.getParameterCount();

		int byIndex = name.indexOf("By") + 2;
		String[] allUpperCaseWords = name.substring(byIndex).split("(?=\\p{Upper})");
		StringBuilder where = new StringBuilder("Where ");
		int increment = 1;
		for (String parameter : allUpperCaseWords) {
			if (parameter.equals("And")) {
				where.append("And ");
			} else if (parameter.equals("Or")) {
				where.append("Or ");
			} else {
				String parameterSql = parameter.toLowerCase();
				where.append(alias).append(".").append(parameterSql).append(" = ?").append(increment).append(" ");
				increment++;
			}
		}

		if (increment-1 != parameterCount) {
			throw new SqlMethodNameParseException();
		}
		return where.toString();
	}


}
