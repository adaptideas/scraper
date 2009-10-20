package br.com.adaptworks.scraper.tag;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * @author jonasabreu
 * 
 */
final public class TagParser {

    private final Pattern pattern = Pattern.compile("(?s)<([^<>]*?)>([^<>]*)");
    private final String relevantTags;
    private final boolean shouldClean;

    private static final Logger log = Logger.getLogger(TagParser.class);

    public TagParser(final String relevantTags) {
        this.relevantTags = relevantTags;
        shouldClean = true;
    }

    public TagParser() {
        shouldClean = false;
        relevantTags = null;
    }

    public List<Tag> parse(final String template) {
        String cleanTemplate = template;
        if (shouldClean) {
            cleanTemplate = cleanTemplate(relevantTags, template);
        }
        log.trace("Relevant tags: " + relevantTags);
        log.trace("Clean template: " + cleanTemplate);
        List<Tag> tags = new ArrayList<Tag>();
        Matcher matcher = pattern.matcher(cleanTemplate);
        while (matcher.find()) {
            String elementContent = null;

            if (matcher.group(2).length() != 0) {
                elementContent = matcher.group(2);
            }

            Tag tag = new TagReader().readTag(matcher.group(1), elementContent);
            tags.add(tag);

        }

        log.trace("Parsed html " + template + " and produced these tags: " + tags);

        return tags;
    }

    private String cleanTemplate(final String relevantTags, final String template) {
        String regex = "(?s)(?i)<(?!(?i:" + relevantTags + ")\\b)[^<>]+>";
        return template.replaceAll(regex, "");
    }

}
