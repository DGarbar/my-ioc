package ioc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import shorter.service.IdentShorterService;

class JavaConfAppContextTest {

    @Test
    public void createEmptyContext() {
        BeanFactory javaConfAppContext = new JavaConfAppContext(new HashMap<>());
        assertNotNull(javaConfAppContext);
    }

    @Test
    public void beanDefIsEmptyAfterCreation() {
        BeanFactory javaConfAppContext = new JavaConfAppContext();
        List<String> beanDefinitionNames = javaConfAppContext.getBeanDefinitionNames();
        assertEquals(0, beanDefinitionNames.size());
    }
//
    @Test
    public void beanDefIsNotForContext() {
        Map<String, Class<?>> context = Map.of("a", String.class, "b", Class.class);
        BeanFactory javaConfAppContext = new JavaConfAppContext(context);
        List<String> beanDefinitionNames = javaConfAppContext.getBeanDefinitionNames();
        assertEquals(2, beanDefinitionNames.size());
    }

    @Test
    public void getBeanWithoutDeps() {
        Map<String, Class<?>> config = new HashMap<>() {{
            put("shorterService", IdentShorterService.class);
        }};
        BeanFactory javaConfAppContext = new JavaConfAppContext(config);
        Object shorterService = javaConfAppContext.getBean("shorterService");
        assertNotNull(shorterService);
    }

    @Test
    public void getSameBean() {
        HashMap<String, Class<?>> config = new HashMap<>() {{
            put("shorterService", IdentShorterService.class);
        }};

        BeanFactory javaConfAppContext = new JavaConfAppContext(config);
        Object shorterService1 = javaConfAppContext.getBean("shorterService");
        Object shorterService2 = javaConfAppContext.getBean("shorterService");
        assertEquals(shorterService1, shorterService2);
    }

}