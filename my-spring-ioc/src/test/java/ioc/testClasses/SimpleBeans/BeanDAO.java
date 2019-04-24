package ioc.testClasses.SimpleBeans;

import ioc.annotation.Component;

@Component(name = "dao")
public class BeanDAO {

	//	@Transaction
	public void getBeanName() {
		System.out.println("MUST BEEN IN ENTITY");
	}

//	@TransactionHelper
//	public EntityManager getEntityManager() {
//		return null;
//	}

}
