package br.com.adaptworks.scraper.element;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.adaptworks.scraper.tag.Tag;
import br.com.adaptworks.scraper.tag.TagReader;

/**
 * @author jonasabreu
 * 
 */
final public class ElementParser {

    private final Pattern pattern;

    public ElementParser() {
        pattern = Pattern.compile("(?s)<([^<>]*?)>([^<>]*)");
    }

    public List<Element> parse(final String template) {
        ArrayList<Element> elements = new ArrayList<Element>();
        Matcher matcher = pattern.matcher(template);
        while (matcher.find()) {
            Tag tag = new TagReader().readTag(matcher.group(1));
            String elementContent = null;
            if (matcher.group(2).length() != 0) {
                elementContent = matcher.group(2);
            }
            if (tag.isOpen()) {
                elements.add(new OpenTagElement(tag, elementContent));
            } else {
                elements.add(new CloseTagElement(tag, elementContent));
            }
        }

        return elements;
    }

}
