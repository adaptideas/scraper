package br.com.adaptworks.scraper.cleaner;

import java.util.List;

import br.com.adaptworks.scraper.tag.Tag;

/**
 * @author jonasabreu
 * 
 */
final public class IrrelevantTagCleaner implements TagCleaner {

    private final List<Tag> relevantTags;

    public IrrelevantTagCleaner(final List<Tag> relevantTags) {
        this.relevantTags = relevantTags;
    }

    public boolean shouldClean(final Tag element) {
        for (Tag tag : relevantTags) {
            if (tag.name().equals(element.name()) && tag.type().equals(element.type())) {
                return false;
            }
        }
        return true;
    }

}
