package br.com.adaptworks.scraper.tag;

import java.util.Map;

import org.apache.log4j.Logger;

/**
 * @author jonasabreu
 * 
 */
final public class Tag {

    private final String name;
    private final boolean isOpen;
    private final Map<String, String> attributes;

    private static final Logger log = Logger.getLogger(Tag.class);

    public Tag(final String name, final boolean isOpen, final Map<String, String> attributes) {
        this.name = name;
        this.isOpen = isOpen;
        this.attributes = attributes;
        log.trace("Creating tag " + toString());
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

    @Override
    public String toString() {
        return (isOpen ? "" : "/") + name + " with attributes " + attributes.entrySet();
    }

}
