package br.com.adaptideas.scraper.tag;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.adaptideas.scraper.matcher.TemplateTag;

/**
 * @author jonasabreu
 * 
 */
final public class TagListMatcherTest {

	private TagListMatcher matcher;
	private TemplateTag openTag;
	private TemplateTag closeTag;

	@Before
	public void setup() {
		matcher = new TagListMatcher(new DefaultTagMatcher());
		openTag = new TemplateTag(new OpenTag("td", "", new HashMap<String, Attribute>()));
		closeTag = new TemplateTag(new CloseTag("td", "", new HashMap<String, Attribute>()));
	}

	@Test
	public void testThatMatchesSameSequence() {
		Integer match = matcher.match(this.list(openTag, openTag, closeTag), this.list(openTag, openTag, closeTag), 0);
		Assert.assertEquals(new Integer(0), match);
	}

	@Test
	public void testThatMatchesMoreThanOneSequence() {
		Integer match = matcher.match(	this.list(openTag, openTag),
										this.list(openTag, openTag, openTag, openTag, closeTag), 0);
		Assert.assertEquals(new Integer(0), match);
	}

	@Test
	public void testThatDoesNotMatchIfThereIsNoMatchingSequence() {
		Assert.assertEquals(new Integer(-1),
							matcher.match(	this.list(openTag, openTag),
											this.list(closeTag, openTag, closeTag, closeTag, openTag), 0));
	}

	private <T extends Tag> List<T> list(final T... ts) {
		return Arrays.asList(ts);
	}

}
