package shorter.service;

import ioc.annotation.PostConstruct;

public class IdentShorterService implements ShorterService {

	@PostConstruct
	public String method(){
		return null;
	}


	@Override
	public String shorten(String path) {
		return path;
	}
}
