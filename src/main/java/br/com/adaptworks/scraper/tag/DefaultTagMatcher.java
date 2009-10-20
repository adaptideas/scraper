package br.com.adaptworks.scraper.tag;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;


/**
 * @author jonasabreu
 * 
 */
final public class DefaultTagMatcher implements TagMatcher {

    private static final Logger log = Logger.getLogger(DefaultTagMatcher.class);

    public boolean matches(final Tag template, final Tag html) {

        boolean result = typeMatches(template.getClass(), html.getClass()) && nameMatches(template.name(), html.name())
                && attributesMatches(template.attributes(), html.attributes());
        log.trace("Matching: " + template + " with " + html + " resulted " + result);
        return result;
    }

    private boolean typeMatches(final Class<? extends Tag> template, final Class<? extends Tag> html) {
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
