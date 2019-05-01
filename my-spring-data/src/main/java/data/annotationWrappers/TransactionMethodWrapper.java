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
	private final static Class<TransactionHelper> TRANSACTION_HELPER_CLASS = TransactionHelper.class;
	private final Supplier<EntityManager> emSupplier;
	private ThreadLocal<EntityManager> buffer = new ThreadLocal<>();

	public TransactionMethodWrapper(Supplier<EntityManager> entityManagerSupplier) {
		this.emSupplier = entityManagerSupplier;
	}

	@Override
	public Supplier<Object> wrap(Method method, Supplier<Object> rootInvoke) {
		return () -> {
			if (ReflectionUtil.isMethodContainsAnnotationName(method, TRANSACTION_HELPER_CLASS)) {
				EntityManager entityManager = buffer.get();
				if (entityManager != null) {
					return entityManager;
				} else {
					return emSupplier.get();
				}
			} else if (ReflectionUtil.isMethodContainsAnnotationName(method, TRANSACTION_CLASS)) {
				System.out.println("Transaction Start");
				EntityManager entityManager = buffer.get();
				if (entityManager == null) {
					entityManager = emSupplier.get();
					buffer.set(entityManager);
				}
				EntityTransaction transaction = entityManager.getTransaction();
				try {
					System.out.println(entityManager +" class em in wrapper");
					System.out.println(transaction +" class tr in wrapper");;
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
					buffer.set(null);
				}
			} else {
				return rootInvoke.get();
			}
		};
	}

	@Override
	public boolean isEqualsAnnotation(Annotation annotationClass) {
		return annotationClass.annotationType().equals(TRANSACTION_CLASS) ||
			annotationClass.annotationType().equals(TRANSACTION_HELPER_CLASS);
	}
}
