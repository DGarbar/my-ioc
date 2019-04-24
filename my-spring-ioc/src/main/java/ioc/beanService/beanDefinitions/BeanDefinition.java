package ioc.beanService.beanDefinitions;

import ioc.exception.NotClassException;
import java.util.ArrayList;
import java.util.List;

public abstract class BeanDefinition {

	private final String name;
	protected Class clazz;
	private List<Class<?>> constructorParameters = new ArrayList<>();

	public BeanDefinition(String name, Class clazz,
		List<Class<?>> constructorParameters) {
		validateClass(clazz);
		this.name = name;
		this.clazz = clazz;
		this.constructorParameters = constructorParameters;
	}

	public BeanDefinition(String name, Class clazz) {
		validateClass(clazz);
		this.name = name;
		this.clazz = clazz;
	}

	public Class getClazz() {
		return clazz;
	}

	public void setClazz(Class clazz) {
		this.clazz = clazz;
	}

	public List<Class<?>> getConstructorParameters() {
		return constructorParameters;
	}

	public void setConstructorParameters(List<Class<?>> constructorParameters) {
		this.constructorParameters = constructorParameters;
	}

	public String getName() {
		return name;
	}

	public Class getOriginClass() {
		return clazz;
	}

	public boolean isAssignableFrom(Class<?> subClass) {
		return subClass.isAssignableFrom(clazz);
	}

	private void validateClass(Class clazz) {
		if (clazz.isAnnotation() || clazz.isArray() || clazz.isEnum()) {
			throw new NotClassException();
		}
	}

}
