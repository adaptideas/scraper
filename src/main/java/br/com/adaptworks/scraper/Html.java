package br.com.adaptworks.scraper;

import java.io.InputStream;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.adaptworks.scraper.element.Element;
import br.com.adaptworks.scraper.element.ElementParser;
import br.com.adaptworks.scraper.infra.InputStreamToStringReader;

/**
 * @author jonasabreu
 * 
 */
final public class Html {

    private final String html;

    private static final Logger log = Logger.getLogger(Html.class);

    public Html(final InputStream inputStream) {
        this(new InputStreamToStringReader().read(inputStream));
    }

    public Html(final String html) {
        log.debug("Creating html");
        this.html = html;
    }

    public List<Element> elements(final String relevantTags) {
        return new ElementParser(relevantTags).parse(html);
    }

}
