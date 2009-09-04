package br.com.adaptworks.scraper.element;

import java.util.Map;
import java.util.Map.Entry;

/**
 * @author jonasabreu
 * 
 */
final public class DefaultElementMatcher implements ElementMatcher {

    public boolean matches(final Element template, final Element html) {
        return typeMatches(template.getClass(), html.getClass()) && nameMatches(template.getName(), html.getName())
                && attributesMatches(template.getAttributes(), html.getAttributes());
    }

    private boolean typeMatches(final Class<?> template, final Class<?> html) {
        return template.equals(html);
    }

    private boolean nameMatches(final String template, final String html) {
        return template.equals(html);
    }

    private boolean attributesMatches(final Map<String, String> template, final Map<String, String> html) {
        if (template.size() > html.size()) {
            return false;
        }
        for (Entry<String, String> entry : template.entrySet()) {
            String htmlValue = html.get(entry.getKey());
            if (htmlValue == null) {
                return false;
            }
            if (!htmlValue.equals(entry.getValue())) {
                return false;
            }
        }
        return true;
    }

}
