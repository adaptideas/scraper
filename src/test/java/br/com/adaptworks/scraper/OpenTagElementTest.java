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
        Assert.assertEquals("td", new OpenTagElement(new TagReader().readTag("\n\ttd  "), null).getName());
    }
}
