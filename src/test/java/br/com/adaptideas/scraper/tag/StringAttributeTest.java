package br.com.adaptideas.scraper.tag;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

final public class StringAttributeTest {

	@Test
	public void shouldMatchIfContentIsEquals() {
		assertTrue(new StringAttribute("content").matches(new StringAttribute("content")));
	}

	@Test
	public void shouldMatchIfContentIsContainedIntoOtherContent() {
		assertTrue(new StringAttribute("asdfcontentasdf").matches(new StringAttribute("content")));
	}

	@Test
	public void shouldNotMatchIfContentIsNotEquals() {
		assertFalse(new StringAttribute("different").matches(new StringAttribute("content")));
	}

}
