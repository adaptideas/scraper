package br.com.adaptworks.scraper.tag;


/**
 * @author jonasabreu
 * 
 */
public interface TagMatcher {

    public boolean matches(final Tag template, final Tag html);

}
