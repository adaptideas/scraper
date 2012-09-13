package br.com.adaptideas.scraper.matcher;

import java.util.ArrayList;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import br.com.adaptideas.scraper.matcher.regex.CaptureGroupsRegexCreator;
import br.com.adaptideas.scraper.matcher.regex.EllipsisRegexCreator;
import br.com.adaptideas.scraper.matcher.regex.OrdinaryWordRegexCreator;
import br.com.adaptideas.scraper.matcher.regex.RegexCreator;

final public class PatternCreator {

	private static Logger log = Logger.getLogger(PatternCreator.class);

	private final ArrayList<RegexCreator> creators;

	private final boolean forceMatch;

	public PatternCreator() {
		this(true);
	}

	public PatternCreator(final boolean forceMatch) {
		this.forceMatch = forceMatch;
		creators = new ArrayList<RegexCreator>();
		creators.add(new EllipsisRegexCreator());
		creators.add(new CaptureGroupsRegexCreator());
		creators.add(new OrdinaryWordRegexCreator());
	}

	public Pattern create(final String content) {
		String pattern = "(?i)(?s)" + (forceMatch ? "^" : "");
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
		String regex = pattern.trim() + (forceMatch ? "$" : "");
		log.trace("created regex [" + regex + "]");

		return Pattern.compile(regex);

	}

}
