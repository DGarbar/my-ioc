package data.dataService.dataBaseConnection;

import javax.persistence.*;

public class PersistenceCreator {

    public static EntityManagerFactory getEntityManagerFactory() {
        String persistenceUnitName = PersistenceXmlParser.getPersistenceUnitName();
        return Persistence.createEntityManagerFactory(persistenceUnitName);
    }

}
