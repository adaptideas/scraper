package br.com.adaptworks.scraper;

import org.junit.Test;

/**
 * @author jonasabreu
 * 
 */
final public class ScraperTest {

    @Test(expected = IllegalArgumentException.class)
    public void testThatThrowsExceptionIfTemplateIsNull() {
        new Scraper<Object>(null);
    }

}
