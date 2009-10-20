package br.com.adaptworks.scraper.cleaner;

import java.util.ArrayList;
import java.util.List;

import br.com.adaptworks.scraper.tag.Tag;

/**
 * @author jonasabreu
 * 
 */
final public class Cleaner {

    private final List<ElementCleaner> cleaners;

    public Cleaner(final List<ElementCleaner> cleaners) {
        this.cleaners = cleaners;
    }

    public List<Tag> clean(final List<Tag> elements) {
        List<Tag> result = new ArrayList<Tag>();
        for (Tag element : elements) {
            boolean mustClean = false;

            for (ElementCleaner cleaner : cleaners) {
                if (cleaner.shouldClean(element)) {
                    mustClean = true;
                }
            }

            if (!mustClean) {
                result.add(element);
            }
        }
        return result;
    }

}
