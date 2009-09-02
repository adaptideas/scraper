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

    private InputStream getInputStream() {
        return new ByteArrayInputStream("".getBytes());
    }
}
