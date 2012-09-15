package br.com.adaptideas.scraper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import net.vidageek.mirror.dsl.Mirror;
import net.vidageek.mirror.list.dsl.Matcher;
import net.vidageek.mirror.list.dsl.MirrorList;
import br.com.adaptideas.scraper.converter.DataConverter;
import br.com.adaptideas.scraper.infra.Tuple2;

/**
 * @author jonasabreu
 * 
 */
final public class SplitTemplate<T> implements Template<T> {

	private List<SingleTemplate<T>> templates;

	public SplitTemplate(final String templateStrings, final Class<T> type) {
		this(templateStrings, type, new DataConverter());
	}

	public SplitTemplate(final String templateStrings, final Class<T> type, final DataConverter converter) {
		List<SingleTemplate<T>> list = new ArrayList<SingleTemplate<T>>();
		for (String templateString : templateStrings.split(Pattern.quote("<split>"))) {
			list.add(new SingleTemplate<T>(templateString, type, converter));
		}
		templates = list;
	}

	public T match(final Html html) {
		Tuple2<T, Integer> match = match(html, 0);
		if (match == null) {
			return null;
		}
		return match._1;
	}

	Tuple2<T, Integer> match(final Html html, final Integer offset) {
		Tuple2<T, Integer> found = null;
		int nullsFound = Integer.MAX_VALUE;

		for (SingleTemplate<T> template : templates) {
			Tuple2<T, Integer> match = template.match(html, offset);
			if (match == null) {
				continue;
			}
			int nulls = countNulls(match._1);
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
