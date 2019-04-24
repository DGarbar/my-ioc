package basic;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import testModel.repo.TestRepo;

class reflectionTests {

	@Test
	void givenTestRepoInterface_whenExtendRepoInterface_thenHaveReflectionGenericNames() {
		Class<TestRepo> testRepoClass = TestRepo.class;
		Type[] genericInterfaces = testRepoClass.getGenericInterfaces();
		String typeName = genericInterfaces[0].getTypeName();
		assertEquals("data.model.Repository<testModel.entity.UserTestEntity, java.lang.Long>",
			typeName);
	}

	@Test
	void givenTestRepoInterface_whenExtendRepoInterface_thenGetAllMethods() {
		Class<TestRepo> testRepoClass = TestRepo.class;
		String[] expectedMethodNames = {"getByName", "save"};
		String[] methodNames = Arrays.stream(testRepoClass.getMethods()).map(Method::getName)
			.toArray(String[]::new);
		assertArrayEquals(expectedMethodNames, methodNames);
	}


}
