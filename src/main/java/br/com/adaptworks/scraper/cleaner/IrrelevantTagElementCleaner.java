package br.com.adaptworks.scraper.cleaner;

import java.util.List;

import br.com.adaptworks.scraper.tag.Tag;

/**
 * @author jonasabreu
 * 
 */
final public class IrrelevantTagElementCleaner implements ElementCleaner {

    public IrrelevantTagElementCleaner(final List<Tag> relevantElements) {
    }

    public boolean shouldClean(final Tag element) {
        return false;
    }

}
