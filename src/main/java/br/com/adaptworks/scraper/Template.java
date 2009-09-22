package br.com.adaptworks.scraper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.vidageek.mirror.dsl.Mirror;

import org.apache.log4j.Logger;

import br.com.adaptworks.scraper.element.DefaultElementMatcher;
import br.com.adaptworks.scraper.element.Element;
import br.com.adaptworks.scraper.element.ElementListMatcher;
import br.com.adaptworks.scraper.element.ElementParser;
import br.com.adaptworks.scraper.infra.InputStreamToStringReader;

/**
 * @author jonasabreu
 * 
 */
final public class Template<T> {

    private final Class<T> type;
    private final List<Element> template;
    private final Pattern pattern = Pattern.compile("\\$\\{(.*?)\\}");

    private static final Logger log = Logger.getLogger(Template.class);

    public Template(final InputStream inputStream, final Class<T> type) {
        this(new InputStreamToStringReader().read(inputStream), type);
    }

    public Template(final String template, final Class<T> type) {
        if (template == null) {
            throw new IllegalArgumentException("template cannot be null");
        }
        if (type == null) {
            throw new IllegalArgumentException("type cannot be null");
        }

        log.debug("Creating template for type " + type.getName());

        this.template = new ElementParser().parse(template);
        this.type = type;
    }

    public List<T> match(final Html html) {
        log.debug("Matching html: " + html);
        List<Integer> indexes = new ElementListMatcher(new DefaultElementMatcher()).match(template, html.elements());
        List<Map<String, String>> data = recoverData(template, html.elements(), indexes);
        return convertDataToList(type, data);
    }

    private List<T> convertDataToList(final Class<T> type, final List<Map<String, String>> data) {
        List<T> list = new ArrayList<T>();
        for (Map<String, String> map : data) {
            T instance = new Mirror().on(type).invoke().constructor().withoutArgs();
            for (String field : map.keySet()) {
                new Mirror().on(instance).set().field(field).withValue(map.get(field));
            }
            list.add(instance);
        }
        return list;
    }

    private List<Map<String, String>> recoverData(final List<Element> template, final List<Element> html,
            final List<Integer> indexes) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();

        for (int i = 0; i < indexes.size(); i++) {
            Map<String, String> map = new HashMap<String, String>();
            for (int j = 0; j < template.size(); j++) {
                Matcher matcher = pattern.matcher(template.get(j).getContent());
                if (matcher.find()) {
                    map.put(matcher.group(1), html.get(indexes.get(i) + j).getContent());
                }
            }
            list.add(map);
        }

        return list;
    }
}
