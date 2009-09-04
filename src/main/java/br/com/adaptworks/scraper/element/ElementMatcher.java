package br.com.adaptworks.scraper.element;

/**
 * @author jonasabreu
 * 
 */
public interface ElementMatcher {

    public boolean matches(final Element template, final Element html);

}
