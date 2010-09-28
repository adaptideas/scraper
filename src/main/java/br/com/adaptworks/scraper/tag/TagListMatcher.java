package br.com.adaptworks.scraper.tag;

import java.util.List;

import org.apache.log4j.Logger;

import br.com.adaptworks.scraper.matcher.TemplateTag;

/**
 * @author jonasabreu
 * 
 */
final public class TagListMatcher {

	private final TagMatcher matcher;

	private static final Logger log = Logger.getLogger(TagListMatcher.class);

	public TagListMatcher(final TagMatcher matcher) {
		this.matcher = matcher;
	}

	public Integer match(final List<TemplateTag> template, final List<? extends Tag> tags) {

		for (int i = 0; i < tags.size(); i++) {
			int j = 0;
			while ((j < template.size()) && (i + j < tags.size()) && matcher.matches(template.get(j), tags.get(i + j))) {
				j++;
			}
			if (j == template.size()) {
				log.trace("Match found: " + i);
				return i;
			}
		}
		log.trace("Could not find match: -1");
		return -1;
	}

}
