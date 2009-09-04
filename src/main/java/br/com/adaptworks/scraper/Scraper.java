package br.com.adaptworks.scraper;

import java.util.List;

/**
 * @author jonasabreu
 * 
 */
final public class Scraper<T> {

    private final Template<T> template;

    public Scraper(final Template<T> template) {
        if (template == null) {
            throw new IllegalArgumentException("template cannot be null.");
        }
        this.template = template;
    }

    public List<T> parse(final Html html) {
        if (html == null) {
            throw new IllegalArgumentException("html cannot be null");
        }

        return template.match(html);

    }

}
