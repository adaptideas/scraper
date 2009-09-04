package br.com.adaptworks.scraper;

import org.junit.Assert;
import org.junit.Test;

import br.com.adaptworks.scraper.element.CloseTagElement;
import br.com.adaptworks.scraper.tag.TagReader;

/**
 * @author jonasabreu
 * 
 */
final public class CloseTagElementTest {
    @Test
    public void testTrimsTagName() {
        Assert.assertEquals("td", new CloseTagElement(new TagReader().readTag("\n\ttd  "), null).getName());
    }
}