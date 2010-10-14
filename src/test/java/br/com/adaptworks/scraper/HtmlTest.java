package br.com.adaptworks.scraper;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import br.com.adaptworks.scraper.matcher.TemplateTag;
import br.com.adaptworks.scraper.tag.OpenTag;
import br.com.adaptworks.scraper.tag.Tag;

/**
 * @author jonasabreu
 * 
 */
@RunWith(Theories.class)
final public class HtmlTest {

	@DataPoint
	public final static String latin1 = "ISO-8859-1";

	@DataPoint
	public final static String utf16 = "UTF-16";

	@DataPoint
	public final static String utf8 = "UTF-8";

	@DataPoint
	public final static String latin2 = "ISO-8859-2";

	@DataPoint
	public final static String windows1252 = "windows-1252";

	@SuppressWarnings("unchecked")
	@Test
	public void testThatIgnoresTags() {
		Html html = new Html("<a><tr><td>blablabla</tr></a>");
		List<TemplateTag> relevantTags = new ArrayList<TemplateTag>();
		relevantTags.add(new TemplateTag(new OpenTag("tr", "", Collections.EMPTY_MAP)));
		List<Tag> tags = html.tags(relevantTags);

		Assert.assertEquals(1, tags.size());
		Assert.assertEquals(OpenTag.class, tags.get(0).getClass());
		Assert.assertEquals("tr", tags.get(0).name());

	}

	@SuppressWarnings("unchecked")
	@Theory
	public void testCharsetConvertion(final String charsetName) throws UnsupportedEncodingException {
		byte[] latin1 = new String("<a><tr>áéíóú</tr></a>").getBytes(charsetName);
		Html html = new Html(new ByteArrayInputStream(latin1), charsetName);
		List<TemplateTag> relevantElements = new ArrayList<TemplateTag>();
		relevantElements.add(new TemplateTag(new OpenTag("tr", "", Collections.EMPTY_MAP)));
		List<Tag> elements = html.tags(relevantElements);

		Assert.assertEquals(1, elements.size());
		Assert.assertEquals(OpenTag.class, elements.get(0).getClass());
		Assert.assertEquals("tr", elements.get(0).name());
		Assert.assertEquals("áéíóú", elements.get(0).content());
	}

}
