package br.com.adaptworks.scraper.tag;

import br.com.adaptworks.scraper.matcher.TemplateTag;

/**
 * @author jonasabreu
 * 
 */
public interface TagMatcher {

	public boolean matches(final TemplateTag template, final Tag html);

}
