package ioc;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ioc.beanService.beanDefinitions.BeanDefinition;
import ioc.exception.NotClassException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class BeanInitializerTest {
//
//    @Test
//    void creatingBeanWithClass() {
//        new BeanDefinition("list", ArrayList.class);
//    }
//
//    @Test
//    void errorWhenCreatingBeanWithInterface() {
//        assertThrows(NotClassException.class, () -> {
//            new BeanDefinition("list", List.class);
//        });
//    }
//
//    @Test
//    void errorWhenCreatingBeanWithAnnotation() {
//        assertThrows(NotClassException.class, () -> {
//            new BeanDefinition("list", Test.class);
//        });
//    }
//
//    @Test
//    void checkTrueAssignable() {
//        BeanDefinition list = new BeanDefinition("list", ArrayList.class);
//        boolean assignableFrom = list.isAssignableFrom(List.class);
//        assertTrue(assignableFrom);
//    }
//
//    @Test
//    void checkFalseAssignable() {
//        BeanDefinition list = new BeanDefinition("list", ArrayList.class);
//        boolean assignableFrom = list.isAssignableFrom(Enum.class);
//        assertFalse(assignableFrom);
//    }
}