package data.model;

import java.util.Objects;

public class RepoType {

	private final Class<?> entityClass;
	private final Class<?>idClass;

	public RepoType(Class<?> entityClass, Class<?> idClass) {
		this.entityClass = entityClass;
		this.idClass = idClass;
	}

	public RepoType(String entityClass, String idClass) throws ClassNotFoundException {
		this.entityClass = Class.forName(entityClass);
		this.idClass = Class.forName(idClass);
	}

	public Class<?> getEntityClass() {
		return entityClass;
	}

	public Class<?> getIdClass() {
		return idClass;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof RepoType)) {
			return false;
		}
		RepoType repoType = (RepoType) o;
		return Objects.equals(entityClass, repoType.entityClass) &&
			Objects.equals(idClass, repoType.idClass);
	}

	@Override
	public int hashCode() {
		return Objects.hash(entityClass, idClass);
	}
}
