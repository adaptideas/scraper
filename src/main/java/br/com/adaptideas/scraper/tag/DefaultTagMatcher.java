package br.com.adaptideas.scraper.tag;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import br.com.adaptideas.scraper.matcher.TemplateTag;

/**
 * @author jonasabreu
 * 
 */
final public class DefaultTagMatcher implements TagMatcher {

	private static final Logger log = Logger.getLogger(DefaultTagMatcher.class);

	public boolean matches(final TemplateTag template, final Tag html) {

		boolean result = typeMatches(template.type(), html.type()) && nameMatches(template.name(), html.name())
				&& attributesMatches(template.attributes(), html.attributes())
				&& contentMatches(template, html.content());
		log.trace("Matching: " + template + " with " + html + " resulted " + result);
		return result;
	}

	private boolean contentMatches(final TemplateTag template, final String content) {
		if (template.content().trim().length() == 0) {
			return true;
		}
		return template.matches(content);
	}

	private boolean typeMatches(final TagType tagType, final TagType tagType2) {
		return tagType.equals(tagType2);
	}

	private boolean nameMatches(final String template, final String html) {
		return template.equalsIgnoreCase(html);
	}

	private boolean attributesMatches(final Map<String, Attribute> templateAttributes,
			final Map<String, Attribute> htmlAttributes) {
		if (templateAttributes.size() > htmlAttributes.size()) {
			return false;
		}
		for (Entry<String, Attribute> entry : templateAttributes.entrySet()) {
			Attribute htmlValue = htmlAttributes.get(entry.getKey());
			if (htmlValue == null) {
				return false;
			}
			if (!entry.getValue().matches(htmlValue)) {
				return false;
			}
		}
		return true;
	}

}
