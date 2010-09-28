package br.com.adaptworks.scraper.matcher.regex;

import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;

/**
 * @author jonasabreu
 * 
 */
final public class EllipsisRegexCreatorTest {

    private RegexCreator creator;

    @Before
    public void setup() {
        creator = new EllipsisRegexCreator();
    }

    @Test
    public void testThatAcceptsAnything() {
        Assert.assertFalse(creator.accepts(""));
        Assert.assertTrue(creator.accepts("..."));
        Assert.assertTrue(creator.accepts("...text"));
        Assert.assertTrue(creator.accepts("text..."));
        Assert.assertFalse(creator.accepts("${name}"));
        Assert.assertFalse(creator.accepts("simple text"));
    }

    @Test
    public void testThatCreatesEllipsisRegex() {
        Assert.assertEquals("\\Q\\E.*?\\Q\\E", creator.regexFor("..."));
    }

    @Test
    public void testThatCreatesEllipsisRegexWithText() {
        Assert.assertEquals("\\Q\\E.*?\\Qtext\\E", creator.regexFor("...text"));
    }

}
