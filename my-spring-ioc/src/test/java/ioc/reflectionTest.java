package ioc;

import ioc.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

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

	private boolean isPostConstructAnnotation(Method method) {
		return Arrays.stream(method.getDeclaredAnnotations())
			.anyMatch(annotation -> annotation.annotationType().equals(PostConstruct.class));
	}

	@Test
	void splitByUpperCase() {
		System.out.println(Arrays.toString("findAllByNameOrEmail".split("(?=\\p{Upper})")));
	}

}
