package br.com.adaptworks.scraper;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

/**
 * @author jonasabreu
 * 
 */
final public class ElementParserTest {

    private ElementParser parser;

    @Before
    public void setup() {
        parser = new ElementParser();
    }

    @Test
    public void testThatIfDoesNotHaveTagReturnsEmptyList() {
        Assert.assertEquals(0, parser.parse("").size());
        Assert.assertEquals(0, parser.parse("only text").size());
    }

    @Test
    public void testThatRecognizesOpenHtmlTag() {
        List<Element> elements = parser.parse("<td>");

        Assert.assertEquals(1, elements.size());
        Assert.assertEquals(OpenTagElement.class, elements.get(0).getClass());
        Assert.assertEquals("td", elements.get(0).getName());
    }

    @Test
    public void testThatRecognizesCloseHtmlTag() {
        List<Element> elements = parser.parse("</td>");

        Assert.assertEquals(1, elements.size());
        Assert.assertEquals(CloseTagElement.class, elements.get(0).getClass());
        Assert.assertEquals("td", elements.get(0).getName());
    }

    @Test
    public void testThatRecognizesTwoTags() {
        List<Element> elements = parser.parse("</td><td>");

        Assert.assertEquals(2, elements.size());
        Assert.assertEquals(CloseTagElement.class, elements.get(0).getClass());
        Assert.assertEquals(OpenTagElement.class, elements.get(1).getClass());
        Assert.assertEquals("td", elements.get(0).getName());
        Assert.assertEquals("td", elements.get(1).getName());
    }

    @Test
    public void testThatIgnoresWhiteChars() {
        List<Element> elements = parser.parse("\n\n </td>\n <td>");

        Assert.assertEquals(2, elements.size());
        Assert.assertEquals(CloseTagElement.class, elements.get(0).getClass());
        Assert.assertEquals(OpenTagElement.class, elements.get(1).getClass());
        Assert.assertEquals("td", elements.get(0).getName());
        Assert.assertEquals("td", elements.get(1).getName());
    }

    @Test
    public void testThatIgnoresWhiteCharsInsideTag() {
        List<Element> elements = parser.parse("</td\n> <td >");

        Assert.assertEquals(2, elements.size());
        Assert.assertEquals(CloseTagElement.class, elements.get(0).getClass());
        Assert.assertEquals(OpenTagElement.class, elements.get(1).getClass());
        Assert.assertEquals("td", elements.get(0).getName());
        Assert.assertEquals("td", elements.get(1).getName());
    }

    @Test
    public void testThatIgnoresMissingGreaterThanChar() {
        List<Element> elements = parser.parse("</td> > <td>");

        Assert.assertEquals(2, elements.size());
        Assert.assertEquals(CloseTagElement.class, elements.get(0).getClass());
        Assert.assertEquals(OpenTagElement.class, elements.get(1).getClass());
        Assert.assertEquals("td", elements.get(0).getName());
        Assert.assertEquals("td", elements.get(1).getName());

    }

    @Test
    public void testThatIgnoresMissingLesserThanChar() {
        List<Element> elements = parser.parse("</td> < <td>");

        Assert.assertEquals(2, elements.size());
        Assert.assertEquals(CloseTagElement.class, elements.get(0).getClass());
        Assert.assertEquals(OpenTagElement.class, elements.get(1).getClass());
        Assert.assertEquals("td", elements.get(0).getName());
        Assert.assertEquals("td", elements.get(1).getName());
    }

}
