package br.com.adaptworks.scraper.element;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jonasabreu
 * 
 */
final public class TagReader {

    public Tag readTag(final String declaration) {
        String tag = sanitize(declaration);
        System.out.println(tag);
        Map<String, String> attributes = recoverAttributes(tag);
        String tagName = recoverName(tag);
        boolean isOpen = recoverType(tag);

        return new Tag(tagName, isOpen, attributes);
    }

    private boolean recoverType(final String tag) {
        return !tag.startsWith("/");
    }

    private String recoverName(final String tag) {
        String tagName = tag.substring(0, tag.indexOf(" "));

        return tagName.startsWith("/") ? tagName.substring(1) : tagName;
    }

    private Map<String, String> recoverAttributes(final String declaration) {
        String[] split = declaration.split(" ");
        Map<String, String> map = new HashMap<String, String>();
        for (int i = 1; i < split.length; i++) {
            String[] attribute = split[i].split("=\"|\"");
            map.put(attribute[0], attribute[1]);
        }
        return map;
    }

    private String sanitize(final String declaration) {
        String clean = "";
        int i = 0;
        i = nextNonWhiteChar(declaration, i);
        clean += declaration.charAt(i++);// either / or first tag name char

        i = nextNonWhiteChar(declaration, i);
        while ((i < declaration.length()) && !isWhiteChar(declaration.charAt(i))) {
            clean += declaration.charAt(i++);
        }
        System.out.println(clean);

        clean += ' ';

        boolean attributeOpen = false;
        while (i < declaration.length()) {
            char c = declaration.charAt(i);
            if (attributeOpen || !isWhiteChar(c)) {
                if ((c == '"') || (c == '\'')) {
                    attributeOpen = !attributeOpen;
                    clean += '"';
                    if (!attributeOpen) {
                        clean += " ";
                    }
                } else {
                    clean += c;
                }
            }
            i++;
        }
        return clean;
    }

    private int nextNonWhiteChar(final String declaration, int i) {
        while ((i < declaration.length()) && isWhiteChar(declaration.charAt(i))) {
            i++;
        }
        return i;
    }

    private boolean isWhiteChar(final char c) {
        return (c == '\n') || (c == '\t') || (c == '\r') || (c == ' ');
    }

}
