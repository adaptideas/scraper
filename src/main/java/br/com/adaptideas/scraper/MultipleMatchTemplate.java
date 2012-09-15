package br.com.adaptideas.scraper;

import java.util.ArrayList;
import java.util.List;

import br.com.adaptideas.scraper.infra.Tuple2;

final public class MultipleMatchTemplate<T> implements Template<List<T>> {

	private final SplitTemplate<T> template;

	public MultipleMatchTemplate(final String template, final Class<T> type) {
		this.template = new SplitTemplate<T>(template, type);
	}

	public List<T> match(final Html html) {
		final List<T> list = new ArrayList<T>();
		Tuple2<T, Integer> match = template.match(html, 0);
		while (match != null) {
			list.add(match._1);
			match = template.match(html, match._2);
		}
		return list;
	}

}
