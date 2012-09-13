package br.com.adaptideas.scraper.tag;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author jonasabreu
 * 
 */
final public class TagReaderTest {

	private TagReader reader;

	@Before
	public void setup() {
		reader = new TagReader();
	}

	@Test
	public void testThatFindsTagName() {
		Tag tag = reader.readTag("td", null);
		Assert.assertEquals("td", tag.name());
		Assert.assertEquals(OpenTag.class, tag.getClass());
	}

	@Test
	public void testThatRemovesSlash() {
		Tag tag = reader.readTag("/td", null);
		Assert.assertEquals("td", tag.name());
		Assert.assertEquals(CloseTag.class, tag.getClass());
	}

	@Test
	public void testThatIgnoresBeginningWhiteChars() {
		Tag tag = reader.readTag("\n \t /\n \rtd", null);
		Assert.assertEquals("td", tag.name());
		Assert.assertEquals(CloseTag.class, tag.getClass());
	}

	@Test
	public void testThatFindsAttributes() {
		Tag tag = reader.readTag("td id=\"bla\"", null);
		Assert.assertEquals("td", tag.name());
		Assert.assertEquals(OpenTag.class, tag.getClass());
		Assert.assertEquals("bla", tag.attribute("id").value());
	}

	@Test
	public void testThatFindsTwoAttributes() {
		Tag tag = reader.readTag("td id=\"bla\" foo='bar'", null);
		Assert.assertEquals("td", tag.name());
		Assert.assertEquals(OpenTag.class, tag.getClass());
		Assert.assertEquals(2, tag.attributes().size());
		Assert.assertEquals("bla", tag.attribute("id").value());
		Assert.assertEquals("bar", tag.attribute("foo").value());
	}

	@Test
	public void testThatWorksForTagA() {
		Tag tag = reader.readTag("a href=\"http://foo.bar\"", null);
		Assert.assertEquals("a", tag.name());
		Assert.assertEquals(OpenTag.class, tag.getClass());
		Assert.assertEquals(1, tag.attributes().size());
		Assert.assertEquals("http://foo.bar", tag.attribute("href").value());
	}

}
