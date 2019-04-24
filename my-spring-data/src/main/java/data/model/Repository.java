package data.model;

public interface Repository<T,Id> {

	void save(T entity);
}
