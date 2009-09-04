package br.com.adaptworks.scraper;

import java.io.InputStream;

import org.junit.Test;

/**
 * @author jonasabreu
 * 
 */
final public class TemplateTest {

    @Test(expected = IllegalArgumentException.class)
    public void testThatThrowsExceptionIfInputStreamIsNull() {
        new Template<Item>((InputStream) null, Item.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThatThrowsExceptionIfTemplateIsNull() {
        new Template<Item>((String) null, Item.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThatThrowsExceptionIfTypeIsNull() {
        new Template<Item>("", null);
    }

}
