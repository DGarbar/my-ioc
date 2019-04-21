package ioc;

import static org.junit.jupiter.api.Assertions.assertTrue;

import ioc.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import shorter.service.ShorterServiceRandomNumber;
import testClasses.ProxyClassBench;

public class reflectionTest {

    @Test
    void testParent() {
        Class<?>[] classes = ArrayList.class.getInterfaces();
        System.out.println(Arrays.toString(classes));
    }

    @Test
    void testAssignableFrom() throws ClassNotFoundException {
        System.out.println(Class.forName("Asd"));
    }

    @Test
    void testMethodName() {
        Arrays.stream(ShorterServiceRandomNumber.class.getMethods()).map(Method::getName)
            .forEach(System.out::println);
    }

    @Test
    void findPostConstructMethod() {
        Optional<Method> any = Arrays.stream(ProxyClassBench.class.getMethods())
            .filter(this::isPostConstructAnnotation)
            //Todo change
//            .filter(method -> method.getParameterCount() == 0)
            .findAny();

        assertTrue(any.isPresent());
    }

    private boolean isPostConstructAnnotation(Method method) {
        return Arrays.stream(method.getDeclaredAnnotations())
            .anyMatch(annotation -> annotation.annotationType().equals(PostConstruct.class));
    }

    @Test
    void splitByUpperCase() {
        System.out.println(Arrays.toString("findAllByNameOrEmail".split("(?=\\p{Upper})")));
    }

}
