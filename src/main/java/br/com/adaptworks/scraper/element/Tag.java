package br.com.adaptworks.scraper.element;

import java.util.Map;

/**
 * @author jonasabreu
 * 
 */
final public class Tag {

    private final String name;
    private final boolean isOpen;
    private final Map<String, String> attributes;

    public Tag(final String name, final boolean isOpen, final Map<String, String> attributes) {
        this.name = name;
        this.isOpen = isOpen;
        this.attributes = attributes;
    }

    public String name() {
        return name;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public String attribute(final String key) {
        return attributes.get(key);
    }

    public Map<String, String> attributes() {
        return attributes;
    }

}
