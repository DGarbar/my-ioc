package ioc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ioc.exception.MultipleBeanMatch;
import ioc.exception.NotClassException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import shorter.service.IdentShorterService;
import testClasses.NeedListInConstructor;
import testClasses.ProxyClassBench;

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
    public void wrongConfigTypeException() {
        Map<String, Class<?>> context = Map.of("a", List.class, "b", Class.class);
        assertThrows(NotClassException.class, () -> new JavaConfAppContext(context));
    }

    @Test
    public void getBeanWithoutDeps() {
        Map<String, Class<?>> config = Map.of("shorterService", IdentShorterService.class);
        BeanFactory javaConfAppContext = new JavaConfAppContext(config);
        Object shorterService = javaConfAppContext.getBean("shorterService");
        assertNotNull(shorterService);
    }

    @Test
    public void getSameBean() {
        Map<String, Class<?>> config = Map.of("shorterService", IdentShorterService.class);

        BeanFactory javaConfAppContext = new JavaConfAppContext(config);
        Object shorterService1 = javaConfAppContext.getBean("shorterService");
        Object shorterService2 = javaConfAppContext.getBean("shorterService");
        assertEquals(shorterService1, shorterService2);
    }

    @Test
    public void getMultipleBeanException() {
        Map<String, Class<?>> config = Map.of("a", ArrayList.class, "b", LinkedList.class);

        BeanFactory javaConfAppContext = new JavaConfAppContext(config);
        assertThrows(MultipleBeanMatch.class, () -> javaConfAppContext.getBean(List.class));
    }

    @Test
    public void tryCreateWithConstructorWithParameters() {
        Map<String, Class<?>> config = Map
            .of("a", ArrayList.class, "b", NeedListInConstructor.class);
        BeanFactory javaConfAppContext = new JavaConfAppContext(config);
        NeedListInConstructor bean = javaConfAppContext.getBean(NeedListInConstructor.class);
        assertNotNull(bean.getList());
    }


    @Test
    public void testProxyMethod() {
        Map<String, Class<?>> config = Map.of("a", ProxyClassBench.class);
        BeanFactory javaConfAppContext = new JavaConfAppContext(config);
        ProxyClassBench bean = javaConfAppContext.getBean(ProxyClassBench.class);
//        bean.bench();
//        bean.voidBench();

        bean.not();
    }


}