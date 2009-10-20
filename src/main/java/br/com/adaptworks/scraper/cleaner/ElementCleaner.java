package br.com.adaptworks.scraper.cleaner;

import br.com.adaptworks.scraper.tag.Tag;

/**
 * @author jonasabreu
 * 
 */
public interface ElementCleaner {

    boolean shouldClean(Tag element);

}
