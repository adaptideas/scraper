package br.com.adaptworks.scraper;

import java.io.InputStream;

/**
 * @author jonasabreu
 * 
 */
final public class Template<T> {

    public Template(final InputStream inputStream, final Class<T> type) {
    }

    public boolean matches(final Html html) {
        return false;
    }

}
