package br.com.adaptworks.scraper;

import java.util.List;

/**
 * @author jonasabreu
 * 
 */
final public class Scraper<T> {

    public Scraper(final Template<T> template) {
        if (template == null) {
            throw new IllegalArgumentException("template cannot be null.");
        }
    }

    public List<T> parse(final Html html) {
        return null;
        // TODO Auto-generated method stub

    }

}
