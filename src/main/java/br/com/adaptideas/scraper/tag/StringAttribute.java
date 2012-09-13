package br.com.adaptideas.scraper.tag;

final public class StringAttribute extends Attribute {

	private final String value;

	public StringAttribute(final String value) {
		this.value = value;
	}

	@Override
	public boolean matches(final Attribute attribute) {
		return value.contains(attribute.value());
	}

	@Override
	public String value() {
		return value;
	}

	@Override
	public String toString() {
		return value;
	}
}
