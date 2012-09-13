package br.com.adaptideas.scraper.tag;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

final public class PatternAttributeTest {

	@Test
	public void shouldMatchIfContentIsEquals() {
		assertTrue(new PatternAttribute("content").matches(new PatternAttribute("content")));
	}

	@Test
	public void shouldMatchIfContentIsContainedIntoOtherContent() {
		assertTrue(new PatternAttribute("content").matches(new PatternAttribute("asdfcontentasdf")));
	}

	@Test
	public void shouldNotMatchIfContentIsNotEquals() {
		assertFalse(new PatternAttribute("different").matches(new PatternAttribute("content")));
	}

	@Test
	public void shouldMatchIfContentCanBeCaptured() {
		assertTrue(new PatternAttribute("${capture}").matches(new PatternAttribute("different")));
	}

	@Test
	public void shouldMatchIfThereIsPartialContent() {
		assertTrue(new PatternAttribute("diff...").matches(new PatternAttribute("different")));
	}
}
