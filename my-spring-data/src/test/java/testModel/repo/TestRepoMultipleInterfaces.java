package testModel.repo;

import data.model.Repository;
import java.util.logging.XMLFormatter;
import javax.persistence.metamodel.IdentifiableType;
import testModel.entity.UserTestEntity;

@ioc.annotation.Repository(name = "mulRepo")
public interface TestRepoMultipleInterfaces extends Repository<UserTestEntity, Long>,
	IdentifiableType<XMLFormatter> {

}
