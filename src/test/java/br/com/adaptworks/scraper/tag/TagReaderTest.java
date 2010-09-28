package br.com.adaptworks.scraper.tag;

import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;

import br.com.adaptworks.scraper.tag.CloseTag;
import br.com.adaptworks.scraper.tag.OpenTag;
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
        Tag tag = reader.readTag("td", null);
        Assert.assertEquals("td", tag.name());
        Assert.assertEquals(OpenTag.class, tag.getClass());
    }

    @Test
    public void testThatRemovesSlash() {
        Tag tag = reader.readTag("/td", null);
        Assert.assertEquals("td", tag.name());
        Assert.assertEquals(CloseTag.class, tag.getClass());
    }

    @Test
    public void testThatIgnoresBeginningWhiteChars() {
        Tag tag = reader.readTag("\n \t /\n \rtd", null);
        Assert.assertEquals("td", tag.name());
        Assert.assertEquals(CloseTag.class, tag.getClass());
    }

    @Test
    public void testThatFindsAttributes() {
        Tag tag = reader.readTag("td id=\"bla\"", null);
        Assert.assertEquals("td", tag.name());
        Assert.assertEquals(OpenTag.class, tag.getClass());
        Assert.assertEquals("bla", tag.attribute("id"));
    }

    @Test
    public void testThatFindsTwoAttributes() {
        Tag tag = reader.readTag("td id=\"bla\" foo='bar'", null);
        Assert.assertEquals("td", tag.name());
        Assert.assertEquals(OpenTag.class, tag.getClass());
        Assert.assertEquals(2, tag.attributes().size());
        Assert.assertEquals("bla", tag.attribute("id"));
        Assert.assertEquals("bar", tag.attribute("foo"));
    }

    @Test
    public void testThatWorksForTagA() {
        Tag tag = reader.readTag("a href=\"http://foo.bar\"", null);
        Assert.assertEquals("a", tag.name());
        Assert.assertEquals(OpenTag.class, tag.getClass());
        Assert.assertEquals(1, tag.attributes().size());
        Assert.assertEquals("http://foo.bar", tag.attribute("href"));
    }

}
