package br.com.adaptideas.scraper;

import java.io.InputStream;
import java.util.List;

import br.com.adaptideas.scraper.infra.InputStreamToStringReader;
import br.com.adaptideas.scraper.matcher.TemplateTag;
import br.com.adaptideas.scraper.tag.Tag;
import br.com.adaptideas.scraper.tag.TagParser;

/**
 * @author jonasabreu
 * 
 */
final public class Html {

	private final String html;

	public Html(final InputStream inputStream, final String charset) {
		this(new InputStreamToStringReader(charset).read(inputStream));
	}

	public Html(final String html) {
		this.html = html;
	}

	public List<Tag> tags(final List<TemplateTag> relevantTags) {
		return new TagParser(relevantTags).parse(html);
	}

	@Override
	public String toString() {
		return html;
	}

}
