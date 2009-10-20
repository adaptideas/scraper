package br.com.adaptworks.scraper;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import br.com.adaptworks.scraper.element.Element;
import br.com.adaptworks.scraper.element.OpenTagElement;

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

	@Test
	public void testThatIgnoresTags() {
		Html html = new Html("<a><tr><td>blablabla</tr></a>");
		List<Element> elements = html.elements("tr");

		Assert.assertEquals(1, elements.size());
		Assert.assertEquals(OpenTagElement.class, elements.get(0).getClass());
		Assert.assertEquals("tr", elements.get(0).getName());

	}

	@Theory
	public void testLatin1ToUTF8Conversion(final String charsetName) throws UnsupportedEncodingException {
		byte[] latin1 = new String("<a><tr>áéíóú</tr></a>").getBytes(charsetName);
		Html html = new Html(new ByteArrayInputStream(latin1), charsetName);
		List<Element> elements = html.elements("tr");

		Assert.assertEquals(1, elements.size());
		Assert.assertEquals(OpenTagElement.class, elements.get(0).getClass());
		Assert.assertEquals("tr", elements.get(0).getName());
		Assert.assertEquals("áéíóú", elements.get(0).getContent());
	}

}
