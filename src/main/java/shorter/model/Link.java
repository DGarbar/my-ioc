package shorter.model;

public class Link {

	private final String link;

	public static Link linkTo(String link) {
		return new Link(link);
	}

	public static Link HTTPLinkTo(String path) {
		return new Link("http://" + path);
	}

	public Link(String link) {
		check(link);
		this.link = link;
	}

	private void check(String link) {
	}

	public String getPath() {
		return link.substring(link.indexOf("//") + 2);
	}

	public String link() {
		return link;
	}
}
