package br.com.adaptideas.scraper.tag;

import java.util.regex.Pattern;

import br.com.adaptideas.scraper.matcher.PatternCreator;

final public class PatternAttribute extends Attribute {

	private final String value;
	private final Pattern pattern;

	public PatternAttribute(final String value) {
		this.value = value;
		System.out.println(value);
		pattern = new PatternCreator(false).create(value);
		System.out.println(pattern.pattern());
	}

	@Override
	public boolean matches(final Attribute value) {
		return pattern.matcher(value.value()).find();
	}

	@Override
	public String value() {
		return value;
	}

}
