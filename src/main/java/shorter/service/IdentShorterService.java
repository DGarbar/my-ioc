package shorter.service;

public class IdentShorterService implements ShorterService {

	@Override
	public String shorten(String path) {
		return path;
	}
}
