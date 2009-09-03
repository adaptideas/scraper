package br.com.adaptworks.scraper;

/**
 * @author jonasabreu
 * 
 */
final public class CloseTagElement implements Element {

    private final String tag;

    public CloseTagElement(final String tag) {
        this.tag = tag.trim();
    }

    public String getName() {
        return tag;
    }

}
