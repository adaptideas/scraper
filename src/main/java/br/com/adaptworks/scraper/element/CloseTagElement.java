package br.com.adaptworks.scraper.element;

import java.util.Map;

import br.com.adaptworks.scraper.tag.DefaultTag;

/**
 * @author jonasabreu
 * 
 */
final public class CloseTagElement implements Element {

    private final DefaultTag tag;
    private final String tagContent;

    public CloseTagElement(final DefaultTag tag, final String tagContent) {
        this.tagContent = tagContent == null ? "" : tagContent;
        this.tag = tag;
    }

    public String getName() {
        return tag.type() + tag.name();
    }

    public String getContent() {
        return tagContent;
    }

    public Map<String, String> getAttributes() {
        return tag.attributes();
    }

    @Override
    public String toString() {
        return tag.toString();
    }

}
