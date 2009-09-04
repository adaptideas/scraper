package br.com.adaptworks.scraper.tag;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

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
}
