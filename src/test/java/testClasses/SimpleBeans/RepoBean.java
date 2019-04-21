package testClasses.SimpleBeans;

import ioc.annotation.Repository;
import shorter.model.Link;

@Repository(name = "repo")
public interface RepoBean {

    void save(Link link);
}
