package br.com.adaptworks.scraper;

import org.junit.Assert;
import org.junit.Test;

import br.com.adaptworks.scraper.element.OpenTagElement;
import br.com.adaptworks.scraper.tag.TagReader;

/**
 * @author jonasabreu
 * 
 */
final public class OpenTagElementTest {

    @Test
    public void testTrimsTagName() {
        Assert.assertEquals("td", new OpenTagElement(new TagReader().readTag("\n\ttd  ", null), null).getName());
    }

    @Test
    public void testThatReturnsEmptyStringIfCreatedWithNullContent() {
        Assert.assertEquals("", new OpenTagElement(new TagReader().readTag("td", null), null).getContent());
    }

    @Test
    public void testThatToStringReturnsSomethingGoodToRead() {
        Assert.assertEquals(" td with attributes [class=bla]", new OpenTagElement(new TagReader().readTag(
                "td class=\"bla\"", null), null).toString());
    }

}
