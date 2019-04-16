package ioc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
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
        BeanDefinition[] beanDefinitionNames = javaConfAppContext.getBeanDefinitionNames();
        assertEquals(0, beanDefinitionNames.length);
    }

    @Test
    public void beanDefIsEmptyForContext() {
//        Map<String, String> context = Map.of("a", "a", "b", "b");
//        BeanFactory javaConfAppContext = new JavaConfAppContext(context);
//        BeanDefinition[] beanDefinitionNames = javaConfAppContext.getBeanDefinitionNames();
//        assertEquals(1, beanDefinitionNames.length);
    }

    @Test
    public void getBeanDefNames() {
        BeanFactory javaConfAppContext = new JavaConfAppContext();
        BeanDefinition[] beanDefinitionNames = javaConfAppContext.getBeanDefinitionNames();
        assertEquals(0, beanDefinitionNames.length);
    }

    @Test
    public void getBeanWithoutDeps() {
        HashMap<String, Class<?>> config = new HashMap<>() {{
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