package br.com.adaptideas.scraper.matcher;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.vidageek.mirror.dsl.Mirror;
import net.vidageek.mirror.list.dsl.MirrorList;

import org.apache.log4j.Logger;

import br.com.adaptideas.scraper.converter.DataConverter;
import br.com.adaptideas.scraper.exception.ScraperException;
import br.com.adaptideas.scraper.infra.Tuple2;
import br.com.adaptideas.scraper.tag.DefaultTagMatcher;
import br.com.adaptideas.scraper.tag.Tag;
import br.com.adaptideas.scraper.tag.TagListMatcher;

/**
 * @author jonasabreu
 * 
 */
final public class TemplateMatcher {

	private final List<TemplateTag> template;
	private final boolean found;
	private final Integer offset;
	private final List<Tag> htmlTags;

	private static Logger log = Logger.getLogger(TemplateMatcher.class);
	private final DataConverter converter;

	public TemplateMatcher(final List<TemplateTag> template, final List<Tag> htmlTags, final DataConverter converter,
			final int receivedOffset) {
		this.template = template;
		this.htmlTags = htmlTags;
		this.converter = converter;
		offset = new TagListMatcher(new DefaultTagMatcher()).match(template, htmlTags, receivedOffset);
		found = offset != -1;
	}

	public boolean find() {
		return found;
	}

	public <T> Tuple2<T, Integer> recoverData(final Class<T> type) {
		T instance = new Mirror().on(type).invoke().constructor().withoutArgs();

		for (int i = 0; i < template.size(); i++) {

			set(instance, template.get(i), htmlTags.get(offset + i));
		}
		return new Tuple2<T, Integer>(instance, offset + template.size());
	}

	private void set(final Object instance, final TemplateTag templateTag, final Tag htmlTag) {
		Map<String, String> map = templateTag.match(htmlTag);

		for (Entry<String, String> entry : map.entrySet()) {

			log.trace("setting [" + entry.getValue() + "] on " + entry.getKey());
			if (!setUsingSetter(instance, entry)) {
				setUsingField(instance, entry);
			}
		}

	}

	private boolean setUsingSetter(final Object instance, final Entry<String, String> entry) {
		MirrorList<Method> setters = new Mirror().on((Class<?>) instance.getClass()).reflectAll().setters();
		for (Method setter : setters) {
			if (setter.getName().equalsIgnoreCase("set" + entry.getKey())) {
				new Mirror().on(instance).invoke().method(setter)
						.withArgs(converter.convert(entry.getValue(), setter.getParameterTypes()[0]));
				return true;
			}
		}
		return false;
	}

	private void setUsingField(final Object instance, final Entry<String, String> entry) {
		Field field = new Mirror().on((Class<?>) instance.getClass()).reflect().field(entry.getKey());
		if (field == null) {
			throw new ScraperException("Could not find field for " + entry.getKey() + " on class "
					+ ((Class<?>) instance.getClass()).getName());
		}
		new Mirror().on(instance).set().field(field).withValue(converter.convert(entry.getValue(), field.getType()));
	}
}
