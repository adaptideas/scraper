package br.com.adaptworks.scraper.element;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jonasabreu
 * 
 */
final public class ElementListMatcher {

    private final ElementMatcher matcher;

    public ElementListMatcher(final ElementMatcher matcher) {
        this.matcher = matcher;
    }

    public List<Integer> match(final List<Element> template, final List<Element> elements) {
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
        return list;
    }

}
