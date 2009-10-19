package br.com.adaptworks.scraper;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import br.com.adaptworks.scraper.element.Element;
import br.com.adaptworks.scraper.element.OpenTagElement;

/**
 * @author jonasabreu
 * 
 */
final public class HtmlTest {

    @Test
    public void testThatIgnoresTags() {
        Html html = new Html("<a><tr><td>blablabla</tr></a>");
        List<Element> elements = html.elements("tr");

        Assert.assertEquals(1, elements.size());
        Assert.assertEquals(OpenTagElement.class, elements.get(0).getClass());
        Assert.assertEquals("tr", elements.get(0).getName());

    }
}
