package br.com.adaptideas.scraper.tag;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.adaptideas.scraper.tag.TagSanitizer;

/**
 * @author jonasabreu
 * 
 */
final public class TagSanitizerTest {

	private TagSanitizer sanitizer;

	@Before
	public void setup() {
		sanitizer = new TagSanitizer();
	}

	@Test
	public void testThatKeepsGoodTags() {
		String tag = "td id=\"foo\" bar=\"foo\"";
		Assert.assertEquals(tag, sanitizer.sanitize(tag));
	}

	@Test
	public void testThatIgnoresWhiteChars() {
		String tag = "td id=\"foo\" bar=\"foo\"";
		Assert.assertEquals(tag, sanitizer.sanitize("\n\ttd \nid \n= \"foo\" \r bar=\"foo\"\n"));
	}

	@Test
	public void testThatKeepsWhiteCharsInsideAttributeValue() {
		String tag = "td id=\" foo\n \" bar=\"foo\"";
		Assert.assertEquals(tag, sanitizer.sanitize("td id=\" foo\n \" bar=\"foo\""));
	}

	@Test
	public void testThatKeepsWhiteCharsInsideAttributeValueAndRemovesThoseOutside() {
		String tag = "td id=\" foo\n \" bar=\"foo\"";
		Assert.assertEquals(tag, sanitizer.sanitize("td  \n id\t\r= \" foo\n \" \n\n\n\n\nbar =\t\"foo\"\t"));
	}

	@Test
	public void testThatChangesSingleQuoteToDoubleQuote() {
		String tag = "td id=\" foo\n \" bar=\"foo\"";
		Assert.assertEquals(tag, sanitizer.sanitize("td id=' foo\n ' bar='foo'"));
	}

	@Test
	public void testThatWorksOnTagsWithoutAttributes() {
		String tag = "td";
		Assert.assertEquals(tag, sanitizer.sanitize("td"));
	}

	@Test
	public void testThatWorksOnMessyTagsWithoutAttributes() {
		String tag = "td";
		Assert.assertEquals(tag, sanitizer.sanitize("   \t \n \rtd   \n"));
	}
}
