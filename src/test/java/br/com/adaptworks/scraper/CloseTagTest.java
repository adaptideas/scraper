package br.com.adaptworks.scraper;

import org.junit.Assert;
import org.junit.Test;

import br.com.adaptworks.scraper.tag.CloseTag;
import br.com.adaptworks.scraper.tag.TagReader;

/**
 * @author jonasabreu
 * 
 */
final public class CloseTagTest {
	@Test
	public void testTrimsTagName() {
		Assert.assertEquals("td", new TagReader().readTag("\n\t/td  ", null).name());
		Assert.assertEquals(CloseTag.class, new TagReader().readTag("\n\t/td  ", null).getClass());
	}

	@Test
	public void testThatReturnsEmptyStringIfCreatedWithNullContent() {
		Assert.assertEquals(CloseTag.class, new TagReader().readTag("/td", null).getClass());
		Assert.assertEquals("", new TagReader().readTag("/td", null).content());
	}

	@Test
	public void testThatToStringReturnsSomethingGoodToRead() {
		Assert.assertEquals("/td with attributes [class=bla] and content=content", new TagReader()
				.readTag("/td class=\"bla\"", "content").toString());
	}
}
