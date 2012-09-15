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
final public class IrrelevantTagCleaner implements TagCleaner {

	private final List<TemplateTag> relevantTags;

	public IrrelevantTagCleaner(final List<TemplateTag> relevantTags) {
		this.relevantTags = relevantTags;
	}

	public boolean shouldClean(final Tag element) {
		for (TemplateTag tag : relevantTags) {
			if (tag.name().equalsIgnoreCase(element.name()) && tag.type().equals(element.type())
					&& attributesMatches(tag, element)) {
				return false;
			}
		}
		return true;
	}

	private boolean attributesMatches(final Tag templateTag, final Tag element) {
		for (Entry<String, Attribute> entry : templateTag.attributes().entrySet()) {
			if (!elementContainsAttribute(element, entry)
					|| !templateMatchesElement(element, entry)) {
				return false;
			}
		}
		return true;
	}

	private boolean templateMatchesElement(final Tag element, Entry<String, Attribute> entry) {
		return entry.getValue().matches(element.attributes().get(entry.getKey()));
	}

	private boolean elementContainsAttribute(final Tag element, Entry<String, Attribute> entry) {
		return element.attributes().containsKey(entry.getKey());
	}

}
