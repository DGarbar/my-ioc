package ioc.classScannerService;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class СlassScanner {

	public List<Class> getClasses(String packageName) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		String path = packageName.replace('.', '/');

		return classLoader.resources(path)
			.map(this::toURI)
			.map(Path::of)
			.flatMap(p -> findClasses(p, packageName).stream())
			.collect(Collectors.toList());
	}

	private URI toURI(URL url) {
		try {
			return url.toURI();
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	private Class toClass(Path path, String rootDir) {
		String classFromDir = getClassFromDir(path, rootDir);
		try {
			return Class.forName(classFromDir);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	private String getClassFromDir(Path path, String rooDir) {
		String rootName = rooDir.replace(".", "\\");
		String fullPath = path.toString();
		int start = fullPath.lastIndexOf(rootName);
		return fullPath
			.substring(start)
			.replaceAll("[/\\\\]", ".")
			.replace(".class", "");
	}

	private List<Class> findClasses(Path directory, String packageName) {
		List<Class> classes = new ArrayList<>();
		if (!Files.exists(directory)) {
			return classes;
		}
		try (Stream<Path> walk = Files.walk(directory)) {
			return walk
				.filter(p -> p.toString().endsWith(".class"))
				.map(path -> toClass(path, packageName))
				.collect(Collectors.toList());
		} catch (IOException e) {
			throw new RuntimeException();
		}
//
//
//		File[] files = directory.listFiles();
//		for (File file : files) {
//			if (file.isDirectory()) {
//				assert !file.getName().contains(".");
//				classes.addAll(findClasses(file, packageName + "." + file.getName()));
//			} else if (file.getName().endsWith(".class")) {
//				try {
//					classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
//				} catch (ClassNotFoundException e) {
//					e.printStackTrace();
//				}
//			}
//		}
	}
}
