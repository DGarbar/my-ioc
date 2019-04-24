package ioc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ioc.exception.MultipleBeanMatch;
import ioc.testClasses.SimpleBeans.BeanA;
import ioc.testClasses.SimpleBeans.BeanB;
import java.util.List;
import org.apache.tools.ant.taskdefs.SQLExec.Transaction;
import org.junit.jupiter.api.Test;

class SimpleIocAppContextTest {

	@Test
	public void createEmptyContext() {
		BeanFactory javaConfAppContext = new SimpleIocAppContext(List.of());
		assertNotNull(javaConfAppContext);
	}

	@Test
	public void beanDefIsEmptyAfterCreation() {
		BeanFactory javaConfAppContext = new SimpleIocAppContext(List.of());
		List<String> beanDefinitionNames = javaConfAppContext.getBeanDefinitionNames();
		assertEquals(0, beanDefinitionNames.size());
	}

	//
	@Test
	public void beanDefIsNotForContext() {
		BeanFactory javaConfAppContext = new SimpleIocAppContext(List.of(BeanA.class));
		List<String> beanDefinitionNames = javaConfAppContext.getBeanDefinitionNames();
		assertEquals(1, beanDefinitionNames.size());
	}

	@Test
	public void emptyConfigWhenUnappropriatedType() {
		SimpleIocAppContext simpleIocAppContext = new SimpleIocAppContext(
			List.of(Transaction.class));
		assertTrue(simpleIocAppContext.getBeanDefinitionNames().isEmpty());
	}

	@Test
	public void getBeanWithoutDeps() {
		BeanFactory javaConfAppContext = new SimpleIocAppContext(List.of(BeanB.class, BeanA.class));
		BeanA beanA = javaConfAppContext.getBean(BeanA.class);
		assertNotNull(beanA);
	}

	@Test
	public void getSameBean() {
		BeanFactory javaConfAppContext = new SimpleIocAppContext(List.of(BeanA.class));
		Object shorterService1 = javaConfAppContext.getBean("beanA");
		Object shorterService2 = javaConfAppContext.getBean("beanA");
		assertEquals(shorterService1, shorterService2);
	}

	@Test
	public void getMultipleBeanException() {
//        BeanFactory javaConfAppContext = new SimpleIocAppContext(List.of(BeanA.class));
//        BeanFactory javaConfAppContext = new SimpleIocAppContext(config);
//        assertThrows(MultipleBeanMatch.class, () -> javaConfAppContext.getBean(List.class));
	}

	@Test
	public void tryCreateWithConstructorWithParameters() {
		BeanFactory javaConfAppContext = new SimpleIocAppContext(List.of(BeanA.class, BeanB.class));
		BeanB bean = javaConfAppContext.getBean(BeanB.class);
		assertNotNull(bean);
	}


	@Test
	public void checkEqualsOfAutowiredBeanAndGetBean() {
		BeanFactory javaConfAppContext = new SimpleIocAppContext(List.of(BeanA.class, BeanB.class));
		BeanB bean = javaConfAppContext.getBean(BeanB.class);
		assertEquals(bean.getBeanA(), javaConfAppContext.getBean(BeanA.class));
	}
}