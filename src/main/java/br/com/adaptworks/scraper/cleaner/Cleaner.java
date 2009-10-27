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
        List<Tag> result = tags;
        for (TagCleaner cleaner : cleaners) {
            List<Tag> temp = new ArrayList<Tag>();
            for (Tag tag : result) {

                if (!cleaner.shouldClean(tag)) {
                    temp.add(tag);
                } else {
                    if (temp.size() != 0) {
                        Tag last = temp.remove(temp.size() - 1);
                        temp.add(last.type().createTag(last.name(), last.content() + tag.content(), last.attributes()));
                    }
                }
            }
            result = temp;
        }
        return result;
    }

}
