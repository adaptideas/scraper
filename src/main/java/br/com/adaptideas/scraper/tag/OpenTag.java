package br.com.adaptideas.scraper.tag;

import java.util.Map;

/**
 * @author jonasabreu
 * 
 */
final public class OpenTag implements Tag {

	private final Map<String, Attribute> attributes;
	private final String name;
	private final String content;

	public OpenTag(final String name, final String content, final Map<String, Attribute> attributes) {
		this.name = name;
		this.content = content == null ? "" : content;
		this.attributes = attributes;
	}

	public Attribute attribute(final String key) {
		return attributes.get(key);
	}

	public Map<String, Attribute> attributes() {
		return attributes;
	}

	public String content() {
		return content;
	}

	public String name() {
		return name;
	}

	public TagType type() {
		return TagType.OPEN;
	}

	@Override
	public String toString() {
		return name + " with attributes " + attributes.entrySet() + " and content=" + content;
	}
}
