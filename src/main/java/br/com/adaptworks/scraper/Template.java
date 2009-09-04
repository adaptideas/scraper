package br.com.adaptworks.scraper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import br.com.adaptworks.scraper.element.DefaultElementMatcher;
import br.com.adaptworks.scraper.element.Element;
import br.com.adaptworks.scraper.element.ElementListMatcher;
import br.com.adaptworks.scraper.element.ElementParser;
import br.com.adaptworks.scraper.infra.InputStreamToStringReader;

/**
 * @author jonasabreu
 * 
 */
final public class Template<T> {

    private final Class<T> type;
    private final List<Element> template;

    public Template(final InputStream inputStream, final Class<T> type) {
        this(new InputStreamToStringReader().read(inputStream), type);
    }

    public Template(final String template, final Class<T> type) {
        if (template == null) {
            throw new IllegalArgumentException("template cannot be null");
        }
        if (type == null) {
            throw new IllegalArgumentException("type cannot be null");
        }
        this.template = new ElementParser().parse(template);
        this.type = type;
    }

    public List<T> match(final Html html) {
        List<Integer> indexes = new ElementListMatcher(new DefaultElementMatcher()).match(template, html.elements());
        return new ArrayList<T>();
    }

}
