package br.com.adaptideas.scraper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import net.vidageek.mirror.dsl.Mirror;
import net.vidageek.mirror.list.dsl.Matcher;
import net.vidageek.mirror.list.dsl.MirrorList;
import br.com.adaptideas.scraper.converter.DataConverter;

/**
 * @author jonasabreu
 * 
 */
final public class SplitTemplate<T> implements Template<T> {

	private List<Template<T>> templates;

	public SplitTemplate(final String templateStrings, final Class<T> type) {
		this(templateStrings, type, new DataConverter());
	}

	public SplitTemplate(final String templateStrings, final Class<T> type, final DataConverter converter) {
		List<Template<T>> list = new ArrayList<Template<T>>();
		for (String templateString : templateStrings.split(Pattern.quote("<split>"))) {
			list.add(new SingleTemplate<T>(templateString, type, converter));
		}
		templates = list;
	}

	public T match(final Html html) {
		T found = null;
		int nullsFound = Integer.MAX_VALUE;

		for (Template<T> template : templates) {
			T match = template.match(html);
			int nulls = countNulls(match);
			if (nulls < nullsFound) {
				nullsFound = nulls;
				found = match;
			}
		}
		return found;
	}

	private int countNulls(final T match) {
		if (match == null) {
			return Integer.MAX_VALUE;
		}

		final Mirror mirror = new Mirror();

		final MirrorList<Field> fields = mirror.on(match.getClass()).reflectAll().fields();

		return fields.matching(new Matcher<Field>() {
			public boolean accepts(final Field element) {
				return mirror.on(match).get().field(element) == null;
			}
		}).size();

	}
}
