package br.com.adaptworks.scraper.matcher;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import br.com.adaptworks.scraper.tag.Tag;
import br.com.adaptworks.scraper.tag.TagType;

/**
 * @author jonasabreu
 * 
 */
final public class TemplateTag implements Tag {

    private final Tag tag;

    private static Logger log = Logger.getLogger(TemplateTag.class);

    public TemplateTag(final Tag tag) {
        this.tag = tag;
    }

    public Map<String, String> match(final String content) {
        Map<String, String> map = new HashMap<String, String>();
        String regex = "(?s)(?i)\\Q" + tag.content().replaceAll("(\\$\\{.*?\\})", "\\\\E(.+?)\\\\Q") + "\\E$";
        Matcher allGroupsFromHtml = Pattern.compile(regex).matcher(content);
        if (allGroupsFromHtml.find()) {
            Matcher matcher = Pattern.compile("\\$\\{(.*?)\\}").matcher(tag.content());
            int j = 1;
            while (matcher.find()) {
                log.trace("recovering [" + allGroupsFromHtml.group(j) + "] on " + matcher.group(1));
                map.put(matcher.group(1), allGroupsFromHtml.group(j));
                j++;
            }
        }
        return map;
    }

    // Delegate Methods
    public String attribute(final String key) {
        return tag.attribute(key);
    }

    public Map<String, String> attributes() {
        return tag.attributes();
    }

    public String content() {
        return tag.content();
    }

    public String name() {
        return tag.name();
    }

    public TagType type() {
        return tag.type();
    }

    @Override
    public String toString() {
        return tag.toString();
    }

}
