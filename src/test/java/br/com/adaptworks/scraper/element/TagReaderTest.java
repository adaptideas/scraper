package br.com.adaptworks.scraper.element;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import br.com.adaptworks.scraper.tag.DefaultTag;
import br.com.adaptworks.scraper.tag.TagReader;
import br.com.adaptworks.scraper.tag.TagType;

/**
 * @author jonasabreu
 * 
 */
final public class TagReaderTest {

	private TagReader reader;

	@Before
	public void setup() {
		this.reader = new TagReader();
	}

	@Test
	public void testThatFindsTagName() {
		DefaultTag tag = this.reader.readTag("td");
		Assert.assertEquals("td", tag.name());
		Assert.assertEquals(TagType.OPEN, tag.type());
	}

	@Test
	public void testThatRemovesSlash() {
		DefaultTag tag = this.reader.readTag("/td");
		Assert.assertEquals("td", tag.name());
		Assert.assertEquals(TagType.CLOSE, tag.type());
	}

	@Test
	public void testThatIgnoresBeginningWhiteChars() {
		DefaultTag tag = this.reader.readTag("\n \t /\n \rtd");
		Assert.assertEquals("td", tag.name());
		Assert.assertEquals(TagType.CLOSE, tag.type());
	}

	@Test
	public void testThatFindsAttributes() {
		DefaultTag tag = this.reader.readTag("td id=\"bla\"");
		Assert.assertEquals("td", tag.name());
		Assert.assertEquals(TagType.OPEN, tag.type());
		Assert.assertEquals("bla", tag.attribute("id"));
	}

	@Test
	public void testThatFindsTwoAttributes() {
		DefaultTag tag = this.reader.readTag("td id=\"bla\" foo='bar'");
		Assert.assertEquals("td", tag.name());
		Assert.assertEquals(TagType.OPEN, tag.type());
		Assert.assertEquals(2, tag.attributes().size());
		Assert.assertEquals("bla", tag.attribute("id"));
		Assert.assertEquals("bar", tag.attribute("foo"));
	}

	@Test
	public void testThatWorksForTagA() {
		DefaultTag tag = this.reader.readTag("a href=\"http://foo.bar\"");
		Assert.assertEquals("a", tag.name());
		Assert.assertEquals(TagType.OPEN, tag.type());
		Assert.assertEquals(1, tag.attributes().size());
		Assert.assertEquals("http://foo.bar", tag.attribute("href"));
	}

}
