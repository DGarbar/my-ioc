package shorter.service;

import java.util.Optional;
import shorter.model.Link;

public interface ShortenLinkService {

	Link shortLink(Link fullLink);

	Optional<Link> fullLink(Link shortLink);

}
