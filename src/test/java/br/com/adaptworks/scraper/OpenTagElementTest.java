package br.com.adaptworks.scraper;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author jonasabreu
 * 
 */
final public class OpenTagElementTest {

    @Test
    public void testTrimsTagName() {
        Assert.assertEquals("td", new OpenTagElement("\n\ttd  ").getName());
    }
}
