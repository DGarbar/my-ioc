package ioc.classScannerService;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;


import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import ioc.classScannerService.СlassScanner;
import java.util.stream.Collectors;
import javax.annotation.processing.SupportedSourceVersion;
import org.junit.jupiter.api.Test;

class СlassScannerTest {

	@Test
	void name() throws IOException , ClassNotFoundException {
		СlassScanner classScanner = new СlassScanner();
		List<Class> classes =  classScanner.getClasses("ioc.annotation");
		classes.stream().forEach(aClass -> System.out.println(aClass));
		List<String> expected = List.of("interface ioc.annotation.Autowired"
			,"interface ioc.annotation.Benchmark"
			,"interface ioc.annotation.Component"
			,"interface ioc.annotation.Init"
			,"interface ioc.annotation.Koko"
			,"interface ioc.annotation.PostConstruct"
			,"interface ioc.annotation.Repository");
		List<String> result = classes.stream()
			.map(Class::toString)
			.collect(Collectors.toList());
		assertArrayEquals(expected.toArray(),result.toArray());
	}

	@Test
	void getTest() {

	}

	@Test
	void urlSoutTest() throws URISyntaxException, ClassNotFoundException {
		URL ioc = СlassScannerTest.class.getClassLoader().getResource("ioc");
		System.out.println(ioc);
		System.out.println("PATH = " + ioc.getPath());
		System.out.println("FILE = " + ioc.getFile());
		System.out.println("HOST = " + ioc.getHost());
		System.out.println("REF = " + ioc.getRef());
		System.out.println("URI = " + ioc.toURI());
		System.out.println("URI PATH = " + ioc.toURI().getPath());
		System.out.println("URI File = " + ioc.toURI().getRawPath());

		Path path = Path.of(ioc.toURI());

		System.out.println("PATH p= " + path);
		System.out.println("PATH absolute = " + path.toAbsolutePath());
	}

	@Test
	void testPathWalk() throws URISyntaxException, IOException {
		String dir = "ioc/testClasses";
		String pack = dir.replaceAll("/", ".");
		URL ioc = СlassScannerTest.class.getClassLoader().getResource(dir);

		Path path = Path.of(ioc.toURI());

		List<Path> collect = Files.walk(path)
			.filter(p -> p.toString().endsWith(".class"))
			.collect(Collectors.toList());
		Path path1 = collect.get(0);
		String s = path1.toString();
		int start = s.indexOf(dir.replace("/","\\"));
		System.out.println(s.substring(start));
	}
}