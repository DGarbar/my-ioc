package data.annotationWrappers;

import data.annotation.Transaction;
import data.annotation.TransactionHelper;
import ioc.annotationWrappers.AnnotationMethodWrapper;
import ioc.exception.BadInvocationException;
import ioc.util.ReflectionUtil;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.function.Supplier;
import javax.persistence.*;

public class TransactionMethodWrapper implements AnnotationMethodWrapper {

	private final static Class<Transaction> TRANSACTION_CLASS = Transaction.class;
	private final Supplier<EntityManagerFactory> emf;
	private ThreadLocal<EntityManager> buffer = new ThreadLocal<>();

	public TransactionMethodWrapper(Supplier<EntityManagerFactory> entityManagerFactorySupplier) {
		this.emf = entityManagerFactorySupplier;
	}

	@Override
	public Supplier<Object> wrap(Method method, Supplier<Object> rootInvoke) {
		return () -> {
			if (ReflectionUtil.isMethodContainsAnnotationName(method, TransactionHelper.class)) {
				EntityManager entityManager = buffer.get();
				if (entityManager != null) {
					return entityManager;
				} else {
					return emf.get().createEntityManager();
				}
			} else if (ReflectionUtil.isMethodContainsAnnotationName(method, TRANSACTION_CLASS)) {
				System.out.println("Transaction Start");
				EntityManager entityManager = buffer.get();
				if (entityManager == null) {
					entityManager = emf.get().createEntityManager();
					buffer.set(entityManager);
				}
				EntityTransaction transaction = entityManager.getTransaction();
				try {
					transaction.begin();
					Object o = rootInvoke.get();
					transaction.commit();
					System.out.println("Transaction Commit");
					return o;
				} catch (Exception e) {
					transaction.rollback();
					e.printStackTrace();
					System.out.println("Transaction Rollback");
					throw new BadInvocationException();
				} finally {
					entityManager.close();
				}
			} else {
				return rootInvoke.get();
			}
		};
	}

	@Override
	public boolean isEqualsAnnotation(Annotation annotationClass) {
//        return annotationClass.annotationType().equals(TRANSACTION_CLASS);
		return true;
	}
}
