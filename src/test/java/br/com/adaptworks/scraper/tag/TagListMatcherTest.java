package br.com.adaptworks.scraper.tag;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import br.com.adaptworks.scraper.tag.CloseTag;
import br.com.adaptworks.scraper.tag.OpenTag;
import br.com.adaptworks.scraper.tag.Tag;
import br.com.adaptworks.scraper.tag.TagListMatcher;

/**
 * @author jonasabreu
 * 
 */
final public class TagListMatcherTest {

    private TagListMatcher matcher;
    private Tag openTag;
    private Tag closeTag;

    @Before
    public void setup() {
        matcher = new TagListMatcher(new DefaultTagMatcher());
        openTag = new OpenTag("td", "", new HashMap<String, String>());
        closeTag = new CloseTag("td", "", new HashMap<String, String>());
    }

    @Test
    public void testThatMatchesSameSequence() {
        List<Integer> matches = matcher.match(this.list(openTag, openTag, closeTag), this.list(openTag, openTag,
                closeTag));
        Assert.assertEquals(1, matches.size());
        Assert.assertEquals(new Integer(0), matches.get(0));
    }

    @Test
    public void testThatMatchesMoreThanOneSequence() {
        List<Integer> matches = matcher.match(this.list(openTag, openTag), this.list(openTag, openTag, openTag,
                openTag, closeTag));
        Assert.assertEquals(2, matches.size());
        Assert.assertEquals(new Integer(0), matches.get(0));
        Assert.assertEquals(new Integer(2), matches.get(1));
    }

    @Test
    public void testThatMatchesMoreThanOneSequenceSplit() {
        List<Integer> matches = matcher.match(this.list(openTag, openTag), this.list(openTag, openTag, closeTag,
                openTag, openTag));
        Assert.assertEquals(2, matches.size());
        Assert.assertEquals(new Integer(0), matches.get(0));
        Assert.assertEquals(new Integer(3), matches.get(1));
    }

    @Test
    public void testThatDoesNotMatchIfThereIsNoMatchingSequence() {
        List<Integer> matches = matcher.match(this.list(openTag, openTag), this.list(closeTag, openTag, closeTag,
                closeTag, openTag));
        Assert.assertEquals(0, matches.size());
    }

    private <T> List<T> list(final T... ts) {
        return Arrays.asList(ts);
    }

}
