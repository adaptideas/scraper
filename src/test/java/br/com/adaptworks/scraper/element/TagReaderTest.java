package br.com.adaptworks.scraper.element;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import br.com.adaptworks.scraper.tag.Tag;
import br.com.adaptworks.scraper.tag.TagReader;

/**
 * @author jonasabreu
 * 
 */
final public class TagReaderTest {

    private TagReader reader;

    @Before
    public void setup() {
        reader = new TagReader();
    }

    @Test
    public void testThatFindsTagName() {
        Tag tag = reader.readTag("td");
        Assert.assertEquals("td", tag.name());
        Assert.assertTrue(tag.isOpen());
    }

    @Test
    public void testThatRemovesSlash() {
        Tag tag = reader.readTag("/td");
        Assert.assertEquals("td", tag.name());
        Assert.assertFalse(tag.isOpen());
    }

    @Test
    public void testThatIgnoresBeginningWhiteChars() {
        Tag tag = reader.readTag("\n \t /\n \rtd");
        Assert.assertEquals("td", tag.name());
        Assert.assertFalse(tag.isOpen());
    }

    @Test
    public void testThatFindsAttributes() {
        Tag tag = reader.readTag("td id=\"bla\"");
        Assert.assertEquals("td", tag.name());
        Assert.assertTrue(tag.isOpen());
        Assert.assertEquals("bla", tag.attribute("id"));
    }

    @Test
    public void testThatFindsTwoAttributes() {
        Tag tag = reader.readTag("td id=\"bla\" foo='bar'");
        Assert.assertEquals("td", tag.name());
        Assert.assertTrue(tag.isOpen());
        Assert.assertEquals(2, tag.attributes().size());
        Assert.assertEquals("bla", tag.attribute("id"));
        Assert.assertEquals("bar", tag.attribute("foo"));
    }
}
