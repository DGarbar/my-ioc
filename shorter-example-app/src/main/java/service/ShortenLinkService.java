package service;

import java.util.Optional;
import model.Link;

public interface ShortenLinkService {

	Link shortLink(Link fullLink);

	Optional<Link> fullLink(Link shortLink);

}
