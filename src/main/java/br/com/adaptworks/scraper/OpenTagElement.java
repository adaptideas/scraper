package br.com.adaptworks.scraper;

/**
 * @author jonasabreu
 * 
 */
final public class OpenTagElement implements Element {

    private final String tag;

    public OpenTagElement(final String tag) {
        this.tag = tag.trim();
    }

    public String getName() {
        return tag;
    }

}
