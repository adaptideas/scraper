package br.com.adaptworks.scraper.tag;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jonasabreu
 * 
 */
final public class TagReader {

    public Tag readTag(final String declaration) {
        String tag = new TagSanitizer().sanitize(declaration);
        Map<String, String> attributes = recoverAttributes(tag);
        String tagName = recoverName(tag);
        boolean isOpen = recoverType(tag);

        return new Tag(tagName, isOpen, attributes);
    }

    private boolean recoverType(final String tag) {
        return !tag.startsWith("/");
    }

    private String recoverName(final String tag) {
        String tagName = tag.substring(0, tag.indexOf(" ") == -1 ? tag.length() : tag.indexOf(" "));

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

}
