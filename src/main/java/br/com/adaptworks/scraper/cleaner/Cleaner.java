package br.com.adaptworks.scraper.cleaner;

import java.util.ArrayList;
import java.util.List;

import br.com.adaptworks.scraper.tag.Tag;

/**
 * @author jonasabreu
 * 
 */
final public class Cleaner {

    private final List<TagCleaner> cleaners;

    public Cleaner(final List<TagCleaner> cleaners) {
        this.cleaners = cleaners;
    }

    public List<Tag> clean(final List<Tag> tags) {
        List<Tag> result = new ArrayList<Tag>();
        for (Tag tag : tags) {
            boolean mustClean = false;

            for (TagCleaner cleaner : cleaners) {
                if (cleaner.shouldClean(tag)) {
                    mustClean = true;
                }
            }

            if (!mustClean) {
                result.add(tag);
            } else {
                if (result.size() != 0) {
                    Tag last = result.remove(result.size() - 1);
                    result.add(last.type().createTag(last.name(), last.content() + tag.content(), last.attributes()));
                }
            }
        }
        return result;
    }

}
