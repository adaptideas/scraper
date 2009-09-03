package br.com.adaptworks.scraper.element;

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
        pattern = Pattern.compile("(?s)<([^<>]*?)>([^<>]*)");
    }

    public List<Element> parse(final String template) {
        ArrayList<Element> elements = new ArrayList<Element>();
        Matcher matcher = pattern.matcher(template);
        while (matcher.find()) {
            String tagName = removeWhiteChar(matcher.group(1));
            String tagContent = null;
            if (matcher.group(2).length() != 0) {
                tagContent = matcher.group(2);
            }
            if (!tagName.startsWith("/")) {
                elements.add(new OpenTagElement(tagName, tagContent));
            } else {
                elements.add(new CloseTagElement(tagName.substring(1), tagContent));
            }
        }

        return elements;
    }

    private String removeWhiteChar(final String string) {
        String res = "";
        for (int i = 0; i < string.length(); i++) {
            if (!isWhiteChar(string.charAt(i))) {
                res += string.charAt(i);
            }
        }
        return res;
    }

    private boolean isWhiteChar(final char c) {
        return (c == '\n') || (c == '\t') || (c == '\r') || (c == ' ');
    }

}
