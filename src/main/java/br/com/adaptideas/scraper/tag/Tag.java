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

	public String attribute(final String key);

	public Map<String, String> attributes();

}
