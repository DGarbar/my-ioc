package ioc.Util.data;

import ioc.Util.data.exception.PersistenceXmlParserException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;

public class PersistenceXmlParser {

    public static String getPersistenceUnitName() {
        try {
            URI uri = PersistenceXmlParser.class.getClassLoader()
                .getResource("META-INF/persistence.xml").toURI();
            Path persistenceXml = Path.of(uri);
            return Files.lines(persistenceXml)
                .filter(s -> s.contains("persistence-unit"))
                .findFirst()
                .map(PersistenceXmlParser::getNapeOfPersistenceUnit)
                .orElseThrow(IllegalArgumentException::new);
        } catch (Exception e) {
            throw new PersistenceXmlParserException(e);
        }
    }

    private static String getNapeOfPersistenceUnit(String xml) {
        int nameIndex = xml.indexOf("name");
        int first = xml.indexOf("\"", nameIndex)+1;
        int last = xml.indexOf("\"", first);
        return xml.substring(first,last);
    }

}
