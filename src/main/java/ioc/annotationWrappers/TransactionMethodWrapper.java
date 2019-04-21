package ioc.annotationWrappers;

import ioc.Util.ReflectionUtil;
import ioc.annotation.Transaction;
import ioc.exception.BadInvocationException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.function.Supplier;
import javax.persistence.*;

public class TransactionMethodWrapper implements AnnotationMethodWrapper {
    private final static Class<Transaction> TRANSACTION_CLASS = Transaction.class;
    private final EntityManagerFactory emf;

    public TransactionMethodWrapper(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Supplier<Object> wrap(Method method, Supplier<Object> rootInvoke) {
        return () -> {
            if (ReflectionUtil.isMethodContainsAnnotationName(method, TRANSACTION_CLASS)){
                System.out.println("Transaction Start");
                EntityManager entityManager = emf.createEntityManager();
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
            }else {
                System.out.println("NOT ");
                return rootInvoke.get();
            }
        };
    }

    @Override
    public boolean isEqualsAnnotation(Annotation annotationClass) {
        return  annotationClass.annotationType().equals(TRANSACTION_CLASS);
    }
}
