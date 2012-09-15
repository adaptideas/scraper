package br.com.adaptideas.scraper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.adaptideas.scraper.converter.DataConverter;
import br.com.adaptideas.scraper.infra.InputStreamToStringReader;
import br.com.adaptideas.scraper.infra.Tuple2;
import br.com.adaptideas.scraper.matcher.TemplateMatcher;
import br.com.adaptideas.scraper.matcher.TemplateTag;
import br.com.adaptideas.scraper.tag.Tag;
import br.com.adaptideas.scraper.tag.TagParser;

/**
 * @author jonasabreu
 * 
 */
final public class SingleTemplate<T> implements Template<T> {

	private final Class<T> type;
	private final List<TemplateTag> template;
	private DataConverter converter;
	private static final String UTF8 = "UTF-8";
	private static final Logger log = Logger.getLogger(SingleTemplate.class);

	public SingleTemplate(final InputStream template, final Class<T> type) {
		this(new InputStreamToStringReader(UTF8).read(template), type, new DataConverter());
	}

	public SingleTemplate(final InputStream template, final Class<T> type, final DataConverter dataConverter) {
		this(new InputStreamToStringReader(UTF8).read(template), type, dataConverter);
	}

	public SingleTemplate(final String template, final Class<T> type) {
		this(template, type, new DataConverter());
	}

	public SingleTemplate(final String template, final Class<T> type, final DataConverter dataConverter) {
		if (template == null) {
			throw new IllegalArgumentException("template cannot be null");
		}
		if (type == null) {
			throw new IllegalArgumentException("type cannot be null");
		}

		log.debug("Creating template for type " + type.getName());

		converter = dataConverter;
		this.template = new ArrayList<TemplateTag>();
		for (Tag tag : new TagParser().parse(template.replaceAll("\\s+", " ").trim())) {
			this.template.add(new TemplateTag(tag));
		}
		this.type = type;
	}

	public T match(final Html html) {
		final Tuple2<T, Integer> match = match(html, 0);
		if (match == null) {
			return null;
		}
		return match._1;
	}

	Tuple2<T, Integer> match(final Html html, final Integer offset) {
		List<Tag> htmlTags = html.tags(template);
		log.trace("Matching html: " + htmlTags);

		TemplateMatcher matcher = new TemplateMatcher(template, htmlTags, converter, offset);

		if (matcher.find()) {
			return matcher.recoverData(type);
		}
		return null;
	}
}
