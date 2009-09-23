package br.com.adaptworks.scraper.tag;

import java.util.Map;

import org.apache.log4j.Logger;

/**
 * @author jonasabreu
 * 
 */
final public class Tag {

	private final String name;
	private final TagType tagType;
	private final Map<String, String> attributes;

	private static final Logger log = Logger.getLogger(Tag.class);

	public Tag(final String name, final TagType tagType, final Map<String, String> attributes) {
		this.name = name;
		this.tagType = tagType;
		this.attributes = attributes;
		log.trace("Creating tag " + this.toString());
	}

	public String name() {
		return this.name;
	}

	public TagType type() {
		return this.tagType;
	}

	public String attribute(final String key) {
		return this.attributes.get(key);
	}

	public Map<String, String> attributes() {
		return this.attributes;
	}

	@Override
	public String toString() {
		return this.tagType + this.name + " with attributes " + this.attributes.entrySet();
	}

}
