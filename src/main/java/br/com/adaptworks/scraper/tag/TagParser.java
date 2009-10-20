package br.com.adaptworks.scraper.tag;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import br.com.adaptworks.scraper.cleaner.Cleaner;
import br.com.adaptworks.scraper.cleaner.ElementCleaner;
import br.com.adaptworks.scraper.cleaner.IrrelevantTagElementCleaner;

/**
 * @author jonasabreu
 * 
 */
final public class TagParser {

    private final Pattern pattern = Pattern.compile("(?s)<([^<>]*?)>([^<>]*)");
    private final Cleaner cleaner;

    private static final Logger log = Logger.getLogger(TagParser.class);

    public TagParser(final List<Tag> relevantElements) {
        List<ElementCleaner> cleaners = new ArrayList<ElementCleaner>();
        cleaners.add(new IrrelevantTagElementCleaner(relevantElements));
        cleaner = new Cleaner(cleaners);
    }

    public TagParser() {
        cleaner = new Cleaner(new ArrayList<ElementCleaner>());
    }

    public List<Tag> parse(final String template) {
        List<Tag> tags = new ArrayList<Tag>();
        Matcher matcher = pattern.matcher(template);

        while (matcher.find()) {
            String elementContent = null;

            if (matcher.group(2).length() != 0) {
                elementContent = matcher.group(2);
            }

            Tag tag = new TagReader().readTag(matcher.group(1), elementContent);
            tags.add(tag);

        }
        log.trace("Parsed html " + template + " and produced these tags: " + tags);

        return cleaner.clean(tags);
    }

}
