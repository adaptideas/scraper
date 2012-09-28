package br.com.adaptideas.scraper.tag;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.adaptideas.scraper.matcher.PatternCreator;

final public class PatternAttribute extends Attribute {

	private final String value;
	private final Pattern pattern;
	private final Pattern extractionPattern;

	public PatternAttribute(final String value) {
		this.value = value;
		pattern = new PatternCreator(false).create(value);
		extractionPattern = new PatternCreator().create(value);
	}

	@Override
	public boolean matches(final Attribute value) {
		return pattern.matcher(value.value()).find();
	}

	@Override
	public String value() {
		return value;
	}

	@Override
	public String toString() {
		return "[" + pattern.pattern() + "]";
	}

	@Override
	public boolean canExtract() {
		return true;
	}

	@Override
	public String[] extract(final Attribute attribute) {
		final Matcher matcher = extractionPattern.matcher(attribute.value());
		matcher.find();
		String[] result = new String[matcher.groupCount() + 1];
		for (int i = 0; i <= matcher.groupCount(); i++) {
			result[i] = matcher.group(i);
		}
		return result;
	}

}
