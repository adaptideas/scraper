package br.com.adaptworks.scraper.tag;

public enum TagType {
	OPEN(' '), CLOSE('/'), BANG('!');

	private final char character;

	private TagType(final char character) {
		this.character = character;

	}

	public static TagType fromValue(final char character) {
		for (TagType type : values()) {
			if (type.character == character) {
				return type;
			}
		}
		return TagType.OPEN;
	}

	@Override
	public String toString() {
		return "" + this.character;
	}
}
