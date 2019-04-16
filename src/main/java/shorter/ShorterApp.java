package shorter;

import static shorter.model.Link.linkTo;

import java.util.Optional;
import shorter.model.Link;
import shorter.repo.InMemShortLinksRepo;
import shorter.repo.ShortLinksRepo;
import shorter.service.DefaultShortenLinkService;
import shorter.service.ShortenLinkService;
import shorter.service.ShorterService;
import shorter.service.ShorterServiceRandomNumber;

public class ShorterApp {

	public static void main(String[] args) {
		String url = "https://www.facebook.com/groups/KyivKUG/";

		ShortLinksRepo shortLinksRepo = new InMemShortLinksRepo();
		ShorterService shorterService = new ShorterServiceRandomNumber();
		ShortenLinkService shortenLinkService = new DefaultShortenLinkService(shortLinksRepo,
			shorterService);
		Link shortLink = shortenLinkService.shortLink(linkTo(url));
		System.out.println("Short link: " + shortLink.link());

		Optional<Link> fullLink = shortenLinkService.fullLink(shortLink);
		System.out.println("Full link: " + fullLink.get().link());

	}

}
