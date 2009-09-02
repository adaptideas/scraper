package br.com.adaptworks.scraper;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import junit.framework.Assert;

import org.junit.Test;

/**
 * @author jonasabreu
 * 
 */
final public class TemplateTest {

    @Test
    public void testThatDoesNotMatchIfCantFindAnyHtmlElements() {
        Assert.assertFalse(new Template<Item>(getInputStream(), Item.class).matches(new Html(getInputStream())));
    }

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

    private InputStream getInputStream() {
        return new ByteArrayInputStream("abc".getBytes());
    }
}
