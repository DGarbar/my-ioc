package ioc.beanService.beanDefinitions;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ComponentBeanDefinition extends BeanDefinition {

	private List<Method> initMethod = new ArrayList<>();
	private List<Method> postConstructMethod = new ArrayList<>();

	public ComponentBeanDefinition(String name, Class clazz,
		List<Class<?>> constructorParameters) {
		super(name, clazz, constructorParameters);
	}

	public void setInitMethod(List<Method> initMethod) {
		this.initMethod = initMethod;
	}

	public void setPostConstructMethod(List<Method> postConstructMethod) {
		this.postConstructMethod = postConstructMethod;
	}

	public List<Method> getInitMethod() {
		return initMethod;
	}

	public List<Method> getPostConstructMethod() {
		return postConstructMethod;
	}


}
