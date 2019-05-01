package testData.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import data.DataIocAppContext;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.Test;
import testData.model.TestModel;

class TestRepoTest {

	@Test
	void testRepoGeneration() {
		DataIocAppContext testData = new DataIocAppContext("testData");
	}

	@Test
	void testRepoIsInitialized() {
		DataIocAppContext testData = new DataIocAppContext("testData");
		int result = testData.getBeanDefinitionNames().size();
		assertEquals(1, result);
	}

	@Test
	void testRepoIsCreated() {
		DataIocAppContext testData = new DataIocAppContext("testData");
		TestRepo testRepo = testData.getBean("testRepo");
		assertNotNull(testRepo);
	}

	@Test
	void testRepoIsCreatedWithMethods() {
		DataIocAppContext testData = new DataIocAppContext("testData");
		TestRepo testRepo = testData.getBean("testRepo");
		testRepo.save(new TestModel());
	}

	@Test
	void testRepoIsFindByOneWithMethods() {
		DataIocAppContext testData = new DataIocAppContext("testData");
		TestRepo testRepo = testData.getBean("testRepo");
		TestModel byId = testRepo.findById(1L);
	}

	@Test
	void testRepoIsFindByTwoWithMethods() {
		DataIocAppContext testData = new DataIocAppContext("testData");
		TestRepo testRepo = testData.getBean("testRepo");
		TestModel entity = new TestModel();
		entity.setName("a");
		testRepo.save(entity);

		TestModel a = testRepo.findByNameAndId("a", 1L);
	}

}