package ioc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ioc.annotation.Transaction;
import ioc.exception.NotClassException;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import shorter.model.Link;
import testClasses.SimpleBeans.BeanA;
import testClasses.SimpleBeans.BeanB;
import testClasses.SimpleBeans.RepoBean;

class JavaConfAppContextTestRepository {


    @Test
    public void beanDefIsNotForContext() {
        BeanFactory javaConfAppContext = new JavaConfAppContext(List.of(RepoBean.class));
        List<String> beanDefinitionNames = javaConfAppContext.getBeanDefinitionNames();
        assertEquals(2, beanDefinitionNames.size());
    }

    @Test
    public void getBeanWithoutDeps() {
        BeanFactory javaConfAppContext = new JavaConfAppContext(List.of(RepoBean.class));
        RepoBean beanA = javaConfAppContext.getBean(RepoBean.class);
        assertNotNull(beanA);
    }

    @Test
    public void getBeanByName() {
        JavaConfAppContext javaConfAppContext = new JavaConfAppContext(List.of(RepoBean.class));
        Object repo = javaConfAppContext.getBean("repo");
        assertNotNull(repo);
    }

    @Test
    public void getSameBean() {
        JavaConfAppContext javaConfAppContext = new JavaConfAppContext(List.of(RepoBean.class));
        RepoBean repo = javaConfAppContext.getBean("repo");
        RepoBean repo1 = javaConfAppContext.getBean("repo");
        assertEquals(repo, repo1);
    }


    @Test
    public void executeSave() {
        JavaConfAppContext javaConfAppContext = new JavaConfAppContext(List.of(RepoBean.class));
        RepoBean repo = javaConfAppContext.getBean("repo");
        repo.save(new Link());
    }





}