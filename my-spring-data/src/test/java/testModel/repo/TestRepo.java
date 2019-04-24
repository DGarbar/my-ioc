package testModel.repo;

import data.model.Repository;
import testModel.entity.UserTestEntity;

public interface TestRepo extends Repository<UserTestEntity, Long> {

	UserTestEntity getByName(String name);
}
