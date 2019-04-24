import static model.Link.linkTo;

import ioc.SimpleIocAppContext;
import java.util.List;
import java.util.Optional;
import model.Link;
import repo.InMemShortLinksRepo;
import repo.ShortLinksRepo;
import service.DefaultShortenLinkService;
import service.ShortenLinkService;
import service.ShorterService;
import service.ShorterServiceRandomNumber;

public class ShorterApp {

	public static void main(String[] args) {
		String url = "https://www.facebook.com/groups/KyivKUG/";
		SimpleIocAppContext simpleIocAppContext = new SimpleIocAppContext(
			List.of(InMemShortLinksRepo.class,
				ShorterServiceRandomNumber.class,
				ShorterService.class,
				DefaultShortenLinkService.class)
		);

		DefaultShortenLinkService shortenLinkService = simpleIocAppContext
			.getBean(DefaultShortenLinkService.class);
		Link shortLink = shortenLinkService.shortLink(linkTo(url));
		System.out.println("Short link: " + shortLink.link());

		Optional<Link> fullLink = shortenLinkService.fullLink(shortLink);
		System.out.println("Full link: " + fullLink.get().link());

	}

}
