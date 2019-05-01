package testData.repo;

import data.annotation.Transaction;
import data.annotation.TransactionHelper;
import ioc.annotation.Component;
import javax.persistence.*;

@Component(name = "myRepo")
public class MyRepo {

	@Transaction
	public void performInTc(){
		EntityManager entityManager = getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		System.out.println(entityManager +" class em");
		System.out.println(transaction +" class tr");
	}

	@Transaction
	public void performInTc2(){
		EntityManager entityManager = getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		System.out.println(entityManager +" class em");
		System.out.println(transaction +" class tr");
	}

	@TransactionHelper
	public EntityManager getEntityManager(){
		return null;
	}
}
