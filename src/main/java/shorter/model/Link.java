package shorter.model;

import javax.persistence.*;


@Entity
public class Link {

    @Id
    @GeneratedValue
    private Long id;
    private String link;

    public Link() {
    }

    public Link(String link) {
        this.link = link;
    }

    public static Link linkTo(String link) {
        return new Link(link);
    }

    public static Link HTTPLinkTo(String path) {
        return new Link("http://" + path);
    }

    public String getPath() {
        return link.substring(link.indexOf("//") + 2);
    }

    public String link() {
        return link;
    }
}
