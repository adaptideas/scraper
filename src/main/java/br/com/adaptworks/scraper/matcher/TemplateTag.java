package br.com.adaptworks.scraper.matcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import br.com.adaptworks.scraper.matcher.regex.CaptureGroupsRegexCreator;
import br.com.adaptworks.scraper.matcher.regex.EllipsisRegexCreator;
import br.com.adaptworks.scraper.matcher.regex.OrdinaryWordRegexCreator;
import br.com.adaptworks.scraper.matcher.regex.RegexCreator;
import br.com.adaptworks.scraper.tag.Tag;
import br.com.adaptworks.scraper.tag.TagType;

/**
 * @author jonasabreu
 * 
 */
final public class TemplateTag implements Tag {

	private final Tag tag;

	private final List<RegexCreator> creators;

	private static Logger log = Logger.getLogger(TemplateTag.class);

	private final Pattern tagPattern;
	private final Pattern captureGroupPattern = Pattern.compile("\\$\\{\\s*(.*?)\\s*\\}");

	private final String templateContent;

	public TemplateTag(final Tag tag) {
		this.tag = tag;
		creators = new ArrayList<RegexCreator>();
		creators.add(new EllipsisRegexCreator());
		creators.add(new CaptureGroupsRegexCreator());
		creators.add(new OrdinaryWordRegexCreator());

		templateContent = clearContent(tag.content());
		tagPattern = createPattern(templateContent);
	}

	private String clearContent(final String content) {
		return captureGroupPattern.matcher(content).replaceAll("\\${$1}");
	}

	private Pattern createPattern(final String content) {
		String pattern = "(?i)(?s)^";
		for (String token : content.split(" ")) {
			if (token.length() != 0) {
				for (RegexCreator creator : creators) {
					if (creator.accepts(token)) {
						pattern += creator.regexFor(token) + " ";
						break;
					}
				}
			}
		}
		String regex = pattern.trim() + "$";
		log.trace("created regex [" + regex + "]");

		return Pattern.compile(regex);

	}

	public Map<String, String> match(final String content) {
		Map<String, String> map = new HashMap<String, String>();
		Matcher contentMatcher = tagPattern.matcher(content);
		if (contentMatcher.find()) {
			Matcher templateMatcher = captureGroupPattern.matcher(templateContent);
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
	public String attribute(final String key) {
		return tag.attribute(key);
	}

	public Map<String, String> attributes() {
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
