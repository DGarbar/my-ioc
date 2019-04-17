package ioc;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import shorter.service.IdentShorterService;
import shorter.service.ShorterServiceRandomNumber;
import testClasses.ProxyClassBench;

public class reflectionTest {

    @Test
    void testParent() {
        Class<?>[] classes = ArrayList.class.getInterfaces();
        System.out.println(Arrays.toString(classes));
    }

    @Test
    void testAssignableFrom() {
        boolean assignableFrom = List.class.isAssignableFrom(ArrayList.class);
        assertTrue(assignableFrom);
    }

    @Test
    void testAssignableFroma() throws ClassNotFoundException {
        System.out.println(Class.forName("Asd"));
    }

    @Test
    void testMethodName() throws ClassNotFoundException {
        Arrays.stream(ShorterServiceRandomNumber.class.getMethods()).map(Method::getName).forEach(System.out::println);
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

    private boolean isPostConstructAnnotation(Method method){
        return Arrays.stream(method.getDeclaredAnnotations())
            .anyMatch(annotation -> annotation.annotationType().getName().endsWith("PostConstruct"));
    }
}
