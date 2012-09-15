package br.com.adaptideas.scraper.matcher;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import br.com.adaptideas.scraper.tag.Attribute;
import br.com.adaptideas.scraper.tag.Tag;
import br.com.adaptideas.scraper.tag.TagType;

/**
 * @author jonasabreu
 * 
 */
final public class TemplateTag implements Tag {

	private final Tag tag;

	private static Logger log = Logger.getLogger(TemplateTag.class);

	private final Pattern tagPattern;

	private final String templateContent;

	public TemplateTag(final Tag tag) {
		this.tag = tag;

		templateContent = new ContentCleaner().clean(tag.content());
		tagPattern = new PatternCreator().create(templateContent);
	}

	public Map<String, String> match(final Tag htmlTag) {
		Map<String, String> map = extractMatchesFromContent(htmlTag.content());
		map.putAll(extractMatchesFromAttribute(htmlTag.attributes()));
		return map;
	}

	private Map<String, String> extractMatchesFromAttribute(final Map<String, Attribute> attributes) {
		final Map<String, String> map = new HashMap<String, String>();
		for (Entry<String, Attribute> templateEntry : attributes().entrySet()) {
			Attribute tplAttribute = templateEntry.getValue();
			Matcher templateMatcher = new ContentCleaner().captureGroupPattern.matcher(tplAttribute.value());

			if (tplAttribute.canExtract()) {
				final String[] matches = tplAttribute.extract(attributes.get(templateEntry.getKey()));

				int i = 1;
				while (templateMatcher.find()) {
					map.put(templateMatcher.group(1), matches[i]);
					i++;
				}
			}
		}
		return map;
	}

	private Map<String, String> extractMatchesFromContent(final String content) {
		Map<String, String> map = new HashMap<String, String>();
		Matcher contentMatcher = tagPattern.matcher(content);
		if (contentMatcher.find()) {
			Matcher templateMatcher = new ContentCleaner().captureGroupPattern.matcher(templateContent);
			int i = 1;
			while (templateMatcher.find()) {
				log.trace("putting " + contentMatcher.group(i) + " on " + templateMatcher.group(1));
				map.put(templateMatcher.group(1), contentMatcher.group(i));
				i++;
			}
		}
		return map;
	}

	public boolean matches(final String content) {
		return tagPattern.matcher(content).matches();
	}

	public String content() {
		return templateContent;
	}

	// Delegate Methods
	public Attribute attribute(final String key) {
		return tag.attribute(key);
	}

	public Map<String, Attribute> attributes() {
		return tag.attributes();
	}

	public String name() {
		return tag.name();
	}

	public TagType type() {
		return tag.type();
	}

	@Override
	public String toString() {
		return tag.toString();
	}

}
