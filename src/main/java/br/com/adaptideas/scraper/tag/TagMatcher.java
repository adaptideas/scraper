package br.com.adaptideas.scraper.tag;

import br.com.adaptideas.scraper.matcher.TemplateTag;

/**
 * @author jonasabreu
 * 
 */
public interface TagMatcher {

	public boolean matches(final TemplateTag template, final Tag html);

}
