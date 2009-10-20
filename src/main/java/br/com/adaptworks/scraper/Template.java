package br.com.adaptworks.scraper;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.vidageek.mirror.dsl.Mirror;

import org.apache.log4j.Logger;

import br.com.adaptworks.scraper.converter.Converter;
import br.com.adaptworks.scraper.converter.NoOpConverter;
import br.com.adaptworks.scraper.exception.ScraperException;
import br.com.adaptworks.scraper.infra.InputStreamToStringReader;
import br.com.adaptworks.scraper.tag.DefaultTagMatcher;
import br.com.adaptworks.scraper.tag.Tag;
import br.com.adaptworks.scraper.tag.TagListMatcher;
import br.com.adaptworks.scraper.tag.TagParser;

/**
 * @author jonasabreu
 * 
 */
@SuppressWarnings("unchecked")
final public class Template<T> {

    private final Class<T> type;
    private final List<Tag> template;
    private final Pattern pattern = Pattern.compile("\\$\\{(.*?)\\}");

    private static final String UTF8 = "UTF-8";
    private static final Logger log = Logger.getLogger(Template.class);
    private final List<Converter> converters;

    public Template(final InputStream inputStream, final Class<T> type) {
        this(new InputStreamToStringReader(UTF8).read(inputStream), type, new ArrayList<Converter>());
    }

    public Template(final InputStream inputStream, final Class<T> type, final List<Converter> converters) {
        this(new InputStreamToStringReader(UTF8).read(inputStream), type, converters);
    }

    public Template(final String template, final Class<T> type) {
        this(template, type, new ArrayList<Converter>());
    }

    public Template(final String template, final Class<T> type, final List<Converter> converters) {
        if (template == null) {
            throw new IllegalArgumentException("template cannot be null");
        }
        if (type == null) {
            throw new IllegalArgumentException("type cannot be null");
        }

        log.debug("Creating template for type " + type.getName());

        this.converters = converters;
        converters.add(new NoOpConverter());
        this.template = new TagParser().parse(template);

        this.type = type;
    }

    public List<T> match(final Html html) {
        log.debug("Matching html: " + html);
        List<Tag> htmlElements = html.elements(getAllTags(template));
        List<Integer> indexes = new TagListMatcher(new DefaultTagMatcher()).match(template, htmlElements);
        System.out.println(indexes);
        List<Map<String, String>> data = recoverData(template, htmlElements, indexes);
        return convertDataToList(type, data);
    }

    private String getAllTags(final List<Tag> template) {
        String res = "";
        for (Tag tag : template) {
            res += tag.type().toString().trim() + tag.name() + "|";
        }
        return res.substring(0, res.length() - 1);
    }

    private List<T> convertDataToList(final Class<T> type, final List<Map<String, String>> data) {
        List<T> list = new ArrayList<T>();
        for (Map<String, String> map : data) {
            T instance = new Mirror().on(type).invoke().constructor().withoutArgs();
            for (String field : map.keySet()) {
                String value = map.get(field);
                Converter converter = getConverterFor(field);
                new Mirror().on(instance).set().field(field).withValue(converter.convert(value));
            }
            list.add(instance);
        }
        return list;
    }

    private Converter<?> getConverterFor(final String fieldName) {
        Field field = new Mirror().on(type).reflect().field(fieldName);
        if (field == null) {
            throw new ScraperException("Could not find field for " + fieldName + " on class " + type.getName());
        }

        for (Converter converter : converters) {
            if (converter.accept(field.getType())) {
                return converter;
            }
        }
        return new NoOpConverter();
    }

    private List<Map<String, String>> recoverData(final List<Tag> template, final List<Tag> html,
            final List<Integer> indexes) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();

        for (int i = 0; i < indexes.size(); i++) {
            Map<String, String> map = new HashMap<String, String>();
            for (int j = 0; j < template.size(); j++) {
                Matcher matcher = pattern.matcher(template.get(j).content());
                if (matcher.find()) {
                    map.put(matcher.group(1), html.get(indexes.get(i) + j).content());
                }
            }
            list.add(map);
        }

        return list;
    }
}
