package data.dataService.repoTypeParser;

import data.dataService.repoTypeParser.exception.IllegalClassTypeException;
import data.model.RepoType;
import data.model.Repository;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Optional;

public class RepoTypeParser {

	public RepoType getRepoTypeByClass(Class<?> repo) {
		return getRepoInterfaceWithTypes(repo)
			.map(this::getRepoTypeFromInterfaceName)
			.orElseThrow(IllegalClassTypeException::new);
	}

	private Optional<String> getRepoInterfaceWithTypes(Class<?> repo) {
		Type[] genericInterfaces = repo.getGenericInterfaces();
		return Arrays.stream(genericInterfaces)
			.map(Type::getTypeName)
			.filter(s -> s.startsWith(Repository.class.getName()))
			.findAny();
	}

	private RepoType getRepoTypeFromInterfaceName(String name) {
		int startFirstType = name.indexOf("<") + 1;
		int endFirstType = name.indexOf(",");
		int startSecondType = name.indexOf(",")+1;
		int endIndex = name.length() - 1;
		String entityType = name.substring(startFirstType, endFirstType).trim();
		String idType = name.substring(startSecondType, endIndex).trim();
		try {
			return new RepoType(entityType, idType);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}
