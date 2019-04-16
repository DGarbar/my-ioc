package ioc;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

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
}
