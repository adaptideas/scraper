package br.com.adaptworks.scraper.matcher.regex;

import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;

/**
 * @author jonasabreu
 * 
 */
final public class CaptureGroupsRegexCreatorTest {
	private RegexCreator creator;

	@Before
	public void setup() {
		creator = new CaptureGroupsRegexCreator();
	}

	@Test
	public void testThatAcceptsAnything() {
		Assert.assertFalse(creator.accepts(""));
		Assert.assertFalse(creator.accepts("..."));
		Assert.assertTrue(creator.accepts("${name}"));
		Assert.assertTrue(creator.accepts("(${name})"));
		Assert.assertFalse(creator.accepts("simple text"));
	}

	@Test
	public void testThatCreatesCaptureGroupRegex() {
		Assert.assertEquals("\\Q\\E(.*?)\\Q\\E", creator.regexFor("${name}"));
	}

	@Test
	public void testThatCreatesCaptureGroupRegexWithinCharacters() {
		Assert.assertEquals("\\Q(\\E(.*?)\\Q)\\E", creator.regexFor("(${foo})"));
	}
}
