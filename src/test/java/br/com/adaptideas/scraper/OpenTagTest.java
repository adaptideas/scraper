package br.com.adaptideas.scraper;

import org.junit.Assert;
import org.junit.Test;

import br.com.adaptideas.scraper.tag.TagReader;

/**
 * @author jonasabreu
 * 
 */
final public class OpenTagTest {

	@Test
	public void testTrimsTagName() {
		Assert.assertEquals("td", new TagReader().readTag("\n\ttd  ", null).name());
	}

	@Test
	public void testThatReturnsEmptyStringIfCreatedWithNullContent() {
		Assert.assertEquals("", new TagReader().readTag("td", null).content());
	}

	@Test
	public void testThatToStringReturnsSomethingGoodToRead() {
		Assert.assertEquals("td with attributes [class=bla] and content=content", new TagReader()
				.readTag("td class=\"bla\"", "content").toString());
	}

}
