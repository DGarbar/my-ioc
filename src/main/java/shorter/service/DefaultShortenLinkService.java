package shorter.service;

import static shorter.model.Link.HTTPLinkTo;

import java.util.Optional;
import shorter.model.Link;
import shorter.repo.ShortLinksRepo;

public class DefaultShortenLinkService implements ShortenLinkService {

	private final ShortLinksRepo shortLinksRepo;
	private final ShorterService shorterService;

	public DefaultShortenLinkService(ShortLinksRepo shortLinksRepo,
		ShorterService shorterService) {
		this.shortLinksRepo = shortLinksRepo;
		this.shorterService = shorterService;
	}

	@Override
	public Link shortLink(Link fullLink) {
		String fullPath = fullLink.getPath();
		String shortPath = shorterService.shorten(fullPath);
		shortLinksRepo.put(shortPath, fullPath);
		return HTTPLinkTo(shortPath);
	}

	@Override
	public Optional<Link> fullLink(Link shortLink) {
		String shortPath = shortLink.getPath();
		Optional<String> fullPath = shortLinksRepo.get(shortPath);
		return fullPath.map(Link::HTTPLinkTo);
	}
}
