package br.com.adaptworks.scraper.tag;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * @author jonasabreu
 * 
 */
final public class TagListMatcher {

    private final TagMatcher matcher;

    private static final Logger log = Logger.getLogger(TagListMatcher.class);

    public TagListMatcher(final TagMatcher matcher) {
        this.matcher = matcher;
    }

    public List<Integer> match(final List<? extends Tag> template, final List<Tag> tags) {

        log.debug("Generating Indexes");
        log.trace("Template: " + template);

        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < tags.size(); i++) {
            int j = 0;
            while ((j < template.size()) && (i + j < tags.size()) && matcher.matches(template.get(j), tags.get(i + j))) {
                j++;
            }
            if (j == template.size()) {
                list.add(i);
                i += j - 1;
            }
        }
        log.trace("Indices Generated: " + list);
        return list;
    }

}
