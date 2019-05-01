package shorter.service;

import ioc.annotation.Component;
import ioc.annotation.PostConstruct;

//@Component(name = "identShorterService")
public class IdentShorterService implements ShorterService {

	@PostConstruct
	public void method(){
		System.out.println("Ident PostConstruct");
	}


	@Override
	public String shorten(String path) {
		return path;
	}
}
