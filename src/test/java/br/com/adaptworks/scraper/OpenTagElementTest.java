package br.com.adaptworks.scraper;

import org.junit.Assert;
import org.junit.Test;

import br.com.adaptworks.scraper.element.OpenTagElement;

/**
 * @author jonasabreu
 * 
 */
final public class OpenTagElementTest {

    @Test
    public void testTrimsTagName() {
        Assert.assertEquals("td", new OpenTagElement("\n\ttd  ", null).getName());
    }
}
