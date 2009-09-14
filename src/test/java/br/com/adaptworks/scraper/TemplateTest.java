package br.com.adaptworks.scraper;

import java.io.InputStream;
import java.util.List;

import org.junit.Assert;
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

    @Test
    public void testThatRecoversData() {
        List<Item> match = new Template<Item>("<td>${test}</td>", Item.class).match(new Html("<td>123</td>"));
        Assert.assertEquals(1, match.size());
        Assert.assertEquals("123", match.get(0).test());
    }

}
