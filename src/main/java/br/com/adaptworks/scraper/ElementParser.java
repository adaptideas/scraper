package br.com.adaptworks.scraper;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author jonasabreu
 * 
 */
final public class ElementParser {

    private final Pattern pattern;

    public ElementParser() {
        pattern = Pattern.compile("(?s)<([^><]*?)>");
    }

    public List<Element> parse(final String template) {
        ArrayList<Element> elements = new ArrayList<Element>();
        Matcher matcher = pattern.matcher(template);
        while (matcher.find()) {
            String tagName = matcher.group(1);
            if (!tagName.startsWith("/")) {
                elements.add(new OpenTagElement(tagName));
            } else {
                elements.add(new CloseTagElement(tagName.substring(1)));
            }
        }

        return elements;
    }

}
