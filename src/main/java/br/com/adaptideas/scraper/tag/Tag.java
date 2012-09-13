package br.com.adaptideas.scraper.tag;

import java.util.Map;

/**
 * @author jonasabreu
 * 
 */
public interface Tag {

	public String name();

	public TagType type();

	public String content();

	public Attribute attribute(final String attributeName);

	public Map<String, Attribute> attributes();

}
