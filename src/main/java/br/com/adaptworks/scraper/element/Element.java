package br.com.adaptworks.scraper.element;

import java.util.Map;

/**
 * @author jonasabreu
 * 
 */
public interface Element {

    String getName();

    String getContent();

    Map<String, String> getAttributes();

}
