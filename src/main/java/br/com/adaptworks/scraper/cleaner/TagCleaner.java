package br.com.adaptworks.scraper.cleaner;

import br.com.adaptworks.scraper.tag.Tag;

/**
 * @author jonasabreu
 * 
 */
public interface TagCleaner {

	boolean shouldClean(Tag element);

}
