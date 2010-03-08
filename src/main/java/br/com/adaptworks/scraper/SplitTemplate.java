package br.com.adaptworks.scraper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import net.vidageek.mirror.dsl.Mirror;
import net.vidageek.mirror.list.dsl.Matcher;
import net.vidageek.mirror.list.dsl.MirrorList;
import br.com.adaptworks.scraper.converter.DataConverter;

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

	public List<T> match(final Html html) {
		List<T> found = new ArrayList<T>();
		int nullsFound = Integer.MAX_VALUE;

		for (Template<T> template : templates) {
			List<T> match = template.match(html);
			int nulls = countNulls(match);
			if (nulls < nullsFound) {
				nullsFound = nulls;
				found = match;
			}
		}
		return found;
	}

	private int countNulls(final List<T> match) {
		int nullCounter = 0;
		final Mirror mirror = new Mirror();
		if (match.size() == 0) {
			return Integer.MAX_VALUE;
		}

		final MirrorList<Field> fields = mirror.on(match.get(0).getClass()).reflectAll().fields();

		for (final T type : match) {
			nullCounter += fields.matching(new Matcher<Field>() {
				public boolean accepts(final Field element) {
					return mirror.on(type).get().field(element) == null;
				}
			}).size();
		}

		return nullCounter;

	}
}
