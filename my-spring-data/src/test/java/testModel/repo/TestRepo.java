package testModel.repo;

import data.model.Repository;
import testModel.entity.UserTestEntity;

@ioc.annotation.Repository(name = "testRepo")
public interface TestRepo extends Repository<UserTestEntity, Long> {

	UserTestEntity getByName(String name);
}
