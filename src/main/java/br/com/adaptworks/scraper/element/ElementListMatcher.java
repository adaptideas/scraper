package br.com.adaptworks.scraper.element;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * @author jonasabreu
 * 
 */
final public class ElementListMatcher {

    private final ElementMatcher matcher;

    private static final Logger log = Logger.getLogger(ElementListMatcher.class);

    public ElementListMatcher(final ElementMatcher matcher) {
        this.matcher = matcher;
    }

    public List<Integer> match(final List<Element> template, final List<Element> elements) {

        log.debug("Generating Indexes");
        log.trace("Template: " + template);

        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < elements.size(); i++) {
            int j = 0;
            while ((j < template.size()) && (i + j < elements.size())
                    && matcher.matches(template.get(j), elements.get(i + j))) {
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
