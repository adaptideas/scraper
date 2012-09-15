package br.com.adaptideas.scraper.tag;

final public class StringAttribute extends Attribute {

	private final String value;

	public StringAttribute(final String value) {
		this.value = value;
	}

	@Override
	public boolean matches(final Attribute attribute) {
		return attribute.value().contains(value);
	}

	@Override
	public String value() {
		return value;
	}

	@Override
	public String toString() {
		return value;
	}

	@Override
	public boolean canExtract() {
		return false;
	}

	@Override
	public String[] extract(final Attribute attribute) {
		return null;
	}
}
