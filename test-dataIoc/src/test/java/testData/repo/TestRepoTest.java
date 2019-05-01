package testData.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import data.DataIocAppContext;
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

		List<TestModel> byNameAndId = testRepo.findByNameAndId("a", 1L);
		assertNotNull(byNameAndId);
	}

	@Test
	void testRepoIsFindByNatural() {
		DataIocAppContext testData = new DataIocAppContext("testData");
		TestRepo testRepo = testData.getBean("testRepo");
		TestModel entity = new TestModel();
		entity.setName("a");
		entity.setSurname("b");
		testRepo.save(entity);

		TestModel bySurname = testRepo.findBySurname("b");
		assertNotNull(bySurname);
	}

		@Test
		void testMyRepoIsInTransaction() {
			DataIocAppContext testData = new DataIocAppContext("testData");
			MyRepo repo = testData.getBean(MyRepo.class);
			repo.performInTc();
			repo.performInTc2();
			repo.performInTc2();
		}



}