package br.com.adaptworks.scraper.cleaner;

import java.util.List;
import java.util.Map.Entry;

import br.com.adaptworks.scraper.tag.Tag;

/**
 * @author jonasabreu
 * 
 */
final public class IrrelevantTagCleaner implements TagCleaner {

	private final List<Tag> relevantTags;

	public IrrelevantTagCleaner(final List<Tag> relevantTags) {
		this.relevantTags = relevantTags;
	}

	public boolean shouldClean(final Tag element) {
		for (Tag tag : relevantTags) {
			if (tag.name().equals(element.name()) && tag.type().equals(element.type())
					&& attributesMatches(tag, element) && contentMatches(tag, element)) {
				return false;
			}
		}
		return true;
	}

	private boolean attributesMatches(final Tag tag, final Tag element) {
		for (Entry<String, String> entry : tag.attributes().entrySet()) {
			if (!element.attributes().containsKey(entry.getKey())
					|| !element.attributes().get(entry.getKey()).contains(entry.getValue())) {
				return false;
			}
		}
		return true;
	}

	private boolean contentMatches(final Tag tag, final Tag element) {
		if ("".equals(tag.content().trim())) {
			return true;
		}
		return "".equals(tag.content().trim()) ? true : tag.content().equals(element.content());
	}
}
