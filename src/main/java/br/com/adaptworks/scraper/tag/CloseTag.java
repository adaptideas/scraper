package br.com.adaptworks.scraper.tag;

import java.util.Map;

/**
 * @author jonasabreu
 * 
 */
final public class CloseTag implements Tag {

    private final Map<String, String> attributes;
    private final String name;
    private final String content;

    public CloseTag(final String name, final String content, final Map<String, String> attributes) {
        this.name = name;
        this.content = content == null ? "" : content;
        this.attributes = attributes;
    }

    public String attribute(final String key) {
        return attributes.get(key);
    }

    public Map<String, String> attributes() {
        return attributes;
    }

    public String content() {
        return content;
    }

    public String name() {
        return name;
    }

    public TagType type() {
        return TagType.CLOSE;
    }

    @Override
    public String toString() {
        return "/" + name + " with attributes " + attributes.entrySet() + " and content=" + content;
    }
}
