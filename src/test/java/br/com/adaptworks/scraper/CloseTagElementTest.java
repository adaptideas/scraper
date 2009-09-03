package br.com.adaptworks.scraper;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author jonasabreu
 * 
 */
final public class CloseTagElementTest {
    @Test
    public void testTrimsTagName() {
        Assert.assertEquals("td", new CloseTagElement("\n\ttd  ").getName());
    }
}
