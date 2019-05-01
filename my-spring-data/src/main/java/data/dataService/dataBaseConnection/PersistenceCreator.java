package data.dataService.dataBaseConnection;

import javax.persistence.*;

public class PersistenceCreator {

	private EntityManagerFactory entityManagerFactory;

	public EntityManager getEntityManager() {
		if (entityManagerFactory == null) {
			entityManagerFactory = getEntityManagerFactory();
		}
		return entityManagerFactory.createEntityManager();
	}

	private EntityManagerFactory getEntityManagerFactory() {
		String persistenceUnitName = PersistenceXmlParser.getPersistenceUnitName();
		return Persistence.createEntityManagerFactory(persistenceUnitName);
	}

}
