package br.com.adaptideas.scraper.cleaner;

import java.util.List;
import java.util.Map.Entry;

import br.com.adaptideas.scraper.matcher.TemplateTag;
import br.com.adaptideas.scraper.tag.Attribute;
import br.com.adaptideas.scraper.tag.Tag;

/**
 * @author jonasabreu
 * 
 */
final public class IrrelevantContentTagCleaner implements TagCleaner {

	private final List<TemplateTag> relevantTags;

	public IrrelevantContentTagCleaner(final List<TemplateTag> relevantTags) {
		this.relevantTags = relevantTags;
	}

	public boolean shouldClean(final Tag element) {
		for (TemplateTag tag : relevantTags) {
			if (tag.name().equalsIgnoreCase(element.name()) && tag.type().equals(element.type())
					&& attributesMatches(tag, element) && contentMatches(tag, element)) {
				return false;
			}
		}
		return true;
	}

	private boolean attributesMatches(final Tag tag, final Tag element) {
		for (Entry<String, Attribute> entry : tag.attributes().entrySet()) {
			if (!element.attributes().containsKey(entry.getKey())
					|| !entry.getValue().matches(element.attributes().get(entry.getKey()))) {
				return false;
			}
		}
		return true;
	}

	private boolean contentMatches(final TemplateTag tag, final Tag element) {
		if (tag.content().trim().length() == 0) {
			return true;
		}
		return tag.matches(element.content());
	}

}
