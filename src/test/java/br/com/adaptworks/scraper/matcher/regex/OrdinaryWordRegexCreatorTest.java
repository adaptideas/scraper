package br.com.adaptworks.scraper.matcher.regex;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

/**
 * @author jonasabreu
 * 
 */
final public class OrdinaryWordRegexCreatorTest {

    private RegexCreator creator;

    @Before
    public void setup() {
        creator = new OrdinaryWordRegexCreator();
    }

    @Test
    public void testThatAcceptsAnything() {
        Assert.assertTrue(creator.accepts(""));
        Assert.assertTrue(creator.accepts("..."));
        Assert.assertTrue(creator.accepts("${name}"));
        Assert.assertTrue(creator.accepts("simple text"));
    }

    @Test
    public void testThatCreatesQuotingRegex() {
        Assert.assertEquals("\\Qanything\\E", creator.regexFor("anything"));
    }

}
