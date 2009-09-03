package br.com.adaptworks.scraper;

import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

/**
 * @author jonasabreu
 * 
 */
final public class Template<T> {

    private final Class<T> type;
    private final List<Element> template;

    public Template(final InputStream inputStream, final Class<T> type) {
        this(readInputStream(inputStream), type);
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

    public boolean matches(final Html html) {
        return false;
    }

    private static String readInputStream(final InputStream inputStream) {
        if (inputStream == null) {
            throw new IllegalArgumentException("inputStream cannot be null");
        }
        return new Scanner(inputStream).useDelimiter("$$").next();
    }
}
