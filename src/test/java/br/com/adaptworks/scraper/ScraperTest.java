package br.com.adaptworks.scraper;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

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

    @Test(expected = IllegalArgumentException.class)
    public void testThatThrowsExceptionIfHtmlIsNull() {
        new Scraper<Item>(new Template<Item>(getInputStream(), Item.class)).parse(null);
    }

    private InputStream getInputStream() {
        return new ByteArrayInputStream("".getBytes());
    }

}
