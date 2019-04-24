package ioc.Util;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import ioc.BeanFactory;
import ioc.SimpleIocAppContext;
import ioc.testClasses.SimpleBeans.BeanDAO;
import java.util.List;
import org.junit.jupiter.api.Test;

class SimpleIocAppContextTestTransactionAnnotation {

	@Test
	void createTransactionalBeanEmptyContext() {
		BeanFactory javaConfAppContext = new SimpleIocAppContext(List.of(BeanDAO.class));
		assertNotNull(javaConfAppContext);
	}

	@Test
	void useTransactionalBeanEmptyContext() {
		BeanFactory javaConfAppContext = new SimpleIocAppContext(List.of(BeanDAO.class));
		BeanDAO bean = javaConfAppContext.getBean(BeanDAO.class);
		bean.getBeanName();
		assertNotNull(javaConfAppContext);
	}


}