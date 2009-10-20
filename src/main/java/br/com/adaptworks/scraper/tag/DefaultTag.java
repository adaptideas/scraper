package br.com.adaptworks.scraper.tag;

import java.util.Map;

import org.apache.log4j.Logger;

/**
 * @author jonasabreu
 * 
 */
final public class DefaultTag implements Tag {

    private final String name;
    private final TagType tagType;
    private final Map<String, String> attributes;

    private static final Logger log = Logger.getLogger(DefaultTag.class);

    public DefaultTag(final String name, final TagType tagType, final Map<String, String> attributes) {
        this.name = name;
        this.tagType = tagType;
        this.attributes = attributes;
        log.trace("Creating tag " + toString());
    }

    public String name() {
        return name;
    }

    public TagType type() {
        return tagType;
    }

    public String attribute(final String key) {
        return attributes.get(key);
    }

    public Map<String, String> attributes() {
        return attributes;
    }

    @Override
    public String toString() {
        return tagType + name + " with attributes " + attributes.entrySet();
    }

}
