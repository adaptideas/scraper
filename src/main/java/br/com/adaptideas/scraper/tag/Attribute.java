package br.com.adaptideas.scraper.tag;

public abstract class Attribute {

	public abstract boolean matches(Attribute value);

	public abstract String value();

	public static Attribute from(final String value) {
		return new StringAttribute(value);
	}

}
