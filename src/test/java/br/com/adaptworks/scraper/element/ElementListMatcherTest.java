package br.com.adaptworks.scraper.element;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import br.com.adaptworks.scraper.tag.Tag;
import br.com.adaptworks.scraper.tag.TagType;

/**
 * @author jonasabreu
 * 
 */
final public class ElementListMatcherTest {

	private ElementListMatcher matcher;
	private Element openTag;
	private Element closeTag;

	@Before
	public void setup() {
		this.matcher = new ElementListMatcher(new DefaultElementMatcher());
		this.openTag = new OpenTagElement(new Tag("td", TagType.OPEN, new HashMap<String, String>()), "");
		this.closeTag = new CloseTagElement(new Tag("td", TagType.CLOSE, new HashMap<String, String>()), "");
	}

	@Test
	public void testThatMatchesSameSequence() {
		List<Integer> matches = this.matcher.match(this.list(this.openTag, this.openTag, this.closeTag), this.list(
				this.openTag, this.openTag, this.closeTag));
		Assert.assertEquals(1, matches.size());
		Assert.assertEquals(new Integer(0), matches.get(0));
	}

	@Test
	public void testThatMatchesMoreThanOneSequence() {
		List<Integer> matches = this.matcher.match(this.list(this.openTag, this.openTag), this.list(this.openTag,
				this.openTag, this.openTag, this.openTag, this.closeTag));
		Assert.assertEquals(2, matches.size());
		Assert.assertEquals(new Integer(0), matches.get(0));
		Assert.assertEquals(new Integer(2), matches.get(1));
	}

	@Test
	public void testThatMatchesMoreThanOneSequenceSplit() {
		List<Integer> matches = this.matcher.match(this.list(this.openTag, this.openTag), this.list(this.openTag,
				this.openTag, this.closeTag, this.openTag, this.openTag));
		Assert.assertEquals(2, matches.size());
		Assert.assertEquals(new Integer(0), matches.get(0));
		Assert.assertEquals(new Integer(3), matches.get(1));
	}

	@Test
	public void testThatDoesNotMatchIfThereIsNoMatchingSequence() {
		List<Integer> matches = this.matcher.match(this.list(this.openTag, this.openTag), this.list(this.closeTag,
				this.openTag, this.closeTag, this.closeTag, this.openTag));
		Assert.assertEquals(0, matches.size());
	}

	private <T> List<T> list(final T... ts) {
		return Arrays.asList(ts);
	}

}
