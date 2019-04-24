package data.dataService.repoTypeParser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import data.model.RepoType;
import org.junit.jupiter.api.Test;
import testModel.repo.TestRepo;
import testModel.repo.TestRepoMultipleInterfaces;

class RepoTypeParserTest {

	RepoTypeParser repoTypeParser = new RepoTypeParser();

	@Test
	void givenRepoClass_whenGetRepoType_thanReturnRepoTypes() throws ClassNotFoundException {
		RepoType repoInterfaceWithTypes = repoTypeParser
			.getRepoTypeByClass(TestRepo.class);

		RepoType expected = new RepoType("testModel.entity.UserTestEntity", "java.lang.Long");

		assertEquals(expected, repoInterfaceWithTypes);
	}

	@Test
	void givenRepoClassMultipleInterfaces_whenGetRepoType_thanReturnRepoTypes()
		throws ClassNotFoundException {
		RepoType repoInterfaceWithTypes = repoTypeParser
			.getRepoTypeByClass(TestRepoMultipleInterfaces.class);

		RepoType expected = new RepoType("testModel.entity.UserTestEntity", "java.lang.Long");

		assertEquals(expected, repoInterfaceWithTypes);
	}


}