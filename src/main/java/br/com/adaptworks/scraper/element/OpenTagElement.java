package br.com.adaptworks.scraper.element;

/**
 * @author jonasabreu
 * 
 */
final public class OpenTagElement implements Element {

    private final String tag;
    private final String tagContent;

    public OpenTagElement(final String tag, final String tagContent) {
        this.tagContent = tagContent;
        this.tag = tag.trim();
    }

    public String getName() {
        return tag;
    }

    public String getContent() {
        return tagContent;
    }

}
