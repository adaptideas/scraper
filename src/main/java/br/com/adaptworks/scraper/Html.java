package br.com.adaptworks.scraper;

import java.io.InputStream;
import java.util.List;

import br.com.adaptworks.scraper.element.Element;
import br.com.adaptworks.scraper.element.ElementParser;
import br.com.adaptworks.scraper.infra.InputStreamToStringReader;

/**
 * @author jonasabreu
 * 
 */
final public class Html {

    private final String html;

    public Html(final InputStream inputStream) {
        this(new InputStreamToStringReader().read(inputStream));
    }

    public Html(final String html) {
        this.html = html;
    }

    public List<Element> elements() {
        return new ElementParser().parse(html);
    }

}
