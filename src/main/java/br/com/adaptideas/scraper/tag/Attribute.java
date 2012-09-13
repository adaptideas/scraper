package br.com.adaptideas.scraper.tag;

import br.com.adaptideas.scraper.matcher.ContentCleaner;

public abstract class Attribute {

	public abstract boolean matches(Attribute value);

	public abstract String value();

	public static Attribute from(final String value) {
		String cleanValue = new ContentCleaner().clean(value);
		if (cleanValue.contains("${") || cleanValue.contains("...")) {
			return new PatternAttribute(cleanValue);
		}
		return new StringAttribute(cleanValue);
	}
}
