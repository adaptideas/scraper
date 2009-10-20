package br.com.adaptworks.scraper.tag;

import java.util.Map;

/**
 * @author jonasabreu
 * 
 */
public interface Tag {

    public String name();

    public TagType type();

    public String attribute(final String key);

    public Map<String, String> attributes();

}
