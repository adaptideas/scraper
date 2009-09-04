package br.com.adaptworks.scraper.element;

import java.util.Map;

import br.com.adaptworks.scraper.tag.Tag;

/**
 * @author jonasabreu
 * 
 */
final public class CloseTagElement implements Element {

    private final Tag tag;
    private final String tagContent;

    public CloseTagElement(final Tag tag, final String tagContent) {
        this.tagContent = tagContent;
        this.tag = tag;
    }

    public String getName() {
        return tag.name();
    }

    public String getContent() {
        return tagContent;
    }

    public Map<String, String> getAttributes() {
        return tag.attributes();
    }

}
