package ioc.annotationWrappers;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.annotation.Annotation;
import javax.persistence.*;
import org.junit.jupiter.api.Test;
import testClasses.ProxyClassBench;

class TransactionMethodWrapperTest {

    @Test
    void testAnnotationEquivalents() throws NoSuchMethodException {
        TransactionMethodWrapper transactionalAnnotationWrapper = new TransactionMethodWrapper(
            Persistence.createEntityManagerFactory("TestManager"));
        Annotation tr = ProxyClassBench.class.getMethod("tr").getAnnotations()[0];
        assertTrue(transactionalAnnotationWrapper.isEqualsAnnotation(tr));
    }
}