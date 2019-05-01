import static shorter.model.Link.linkTo;

import ioc.SimpleIocAppContext;
import java.util.Optional;
import shorter.model.Link;
import shorter.service.DefaultShortenLinkService;

public class ShorterApp {

	public static void main(String[] args) {
		String url = "https://www.facebook.com/groups/KyivKUG/";
		SimpleIocAppContext simpleIocAppContext = new SimpleIocAppContext("shorter");

		DefaultShortenLinkService shortenLinkService = simpleIocAppContext
			.getBean(DefaultShortenLinkService.class);
		Link shortLink = shortenLinkService.shortLink(linkTo(url));
		System.out.println("Short link: " + shortLink.link());

		Optional<Link> fullLink = shortenLinkService.fullLink(shortLink);
		System.out.println("Full link: " + fullLink.get().link());

	}

}
