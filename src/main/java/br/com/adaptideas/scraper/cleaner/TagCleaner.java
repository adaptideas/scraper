package br.com.adaptideas.scraper.cleaner;

import br.com.adaptideas.scraper.tag.Tag;

/**
 * @author jonasabreu
 * 
 */
public interface TagCleaner {

	boolean shouldClean(Tag element);

}
