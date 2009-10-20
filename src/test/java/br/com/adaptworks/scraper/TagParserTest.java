package br.com.adaptworks.scraper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import br.com.adaptworks.scraper.tag.BangTag;
import br.com.adaptworks.scraper.tag.CloseTag;
import br.com.adaptworks.scraper.tag.OpenTag;
import br.com.adaptworks.scraper.tag.Tag;
import br.com.adaptworks.scraper.tag.TagParser;

/**
 * @author jonasabreu
 * 
 */
final public class TagParserTest {

    private TagParser parser;

    @SuppressWarnings("unchecked")
    @Before
    public void setup() {
        List<Tag> tags = new ArrayList<Tag>();
        tags.add(new OpenTag("td", "", Collections.EMPTY_MAP));
        tags.add(new OpenTag("tr", "", Collections.EMPTY_MAP));
        tags.add(new OpenTag("foo", "", Collections.EMPTY_MAP));
        tags.add(new OpenTag("bar", "", Collections.EMPTY_MAP));
        tags.add(new OpenTag("a", "", Collections.EMPTY_MAP));
        tags.add(new OpenTag("hr", "", Collections.EMPTY_MAP));
        tags.add(new CloseTag("td", "", Collections.EMPTY_MAP));
        tags.add(new CloseTag("tr", "", Collections.EMPTY_MAP));
        tags.add(new CloseTag("foo", "", Collections.EMPTY_MAP));
        tags.add(new CloseTag("bar", "", Collections.EMPTY_MAP));
        tags.add(new CloseTag("a", "", Collections.EMPTY_MAP));
        parser = new TagParser(tags);
    }

    @Test
    public void testThatIfDoesNotHaveTagReturnsEmptyList() {
        Assert.assertEquals(0, parser.parse("").size());
        Assert.assertEquals(0, parser.parse("only text").size());
    }

    @Test
    public void testThatRecognizesOpenHtmlTag() {
        List<Tag> elements = parser.parse("<td>");

        Assert.assertEquals(1, elements.size());
        Assert.assertEquals(OpenTag.class, elements.get(0).getClass());
        Assert.assertEquals("td", elements.get(0).name());
    }

    @Test
    public void testThatRecognizesCloseHtmlTag() {
        List<Tag> elements = parser.parse("</td>");

        Assert.assertEquals(1, elements.size());
        Assert.assertEquals(CloseTag.class, elements.get(0).getClass());
        Assert.assertEquals("td", elements.get(0).name());
    }

    @Test
    public void testThatRecognizesTwoTags() {
        List<Tag> elements = parser.parse("</td><td>");

        Assert.assertEquals(2, elements.size());
        Assert.assertEquals(CloseTag.class, elements.get(0).getClass());
        Assert.assertEquals(OpenTag.class, elements.get(1).getClass());
        Assert.assertEquals("td", elements.get(0).name());
        Assert.assertEquals("td", elements.get(1).name());
    }

    @Test
    public void testThatIgnoresWhiteChars() {
        List<Tag> elements = parser.parse("\n\n </td>\n <td>");

        Assert.assertEquals(2, elements.size());
        Assert.assertEquals(CloseTag.class, elements.get(0).getClass());
        Assert.assertEquals(OpenTag.class, elements.get(1).getClass());
        Assert.assertEquals("td", elements.get(0).name());
        Assert.assertEquals("td", elements.get(1).name());
    }

    @Test
    public void testThatIgnoresWhiteCharsInsideTag() {
        List<Tag> elements = parser.parse("</td\n> <td >");

        Assert.assertEquals(2, elements.size());
        Assert.assertEquals(CloseTag.class, elements.get(0).getClass());
        Assert.assertEquals(OpenTag.class, elements.get(1).getClass());
        Assert.assertEquals("td", elements.get(0).name());
        Assert.assertEquals("td", elements.get(1).name());
    }

    @Test
    public void testThatIgnoresMissingGreaterThanChar() {
        List<Tag> elements = parser.parse("</td> > <td>");

        Assert.assertEquals(2, elements.size());
        Assert.assertEquals(CloseTag.class, elements.get(0).getClass());
        Assert.assertEquals(OpenTag.class, elements.get(1).getClass());
        Assert.assertEquals("td", elements.get(0).name());
        Assert.assertEquals("td", elements.get(1).name());

    }

    @Test
    public void testThatIgnoresMissingLesserThanChar() {
        List<Tag> elements = parser.parse("</td> < <td>");

        Assert.assertEquals(2, elements.size());
        Assert.assertEquals(CloseTag.class, elements.get(0).getClass());
        Assert.assertEquals(OpenTag.class, elements.get(1).getClass());
        Assert.assertEquals("td", elements.get(0).name());
        Assert.assertEquals("td", elements.get(1).name());
    }

    @Test
    public void testThatRecognizesAnyOpenTag() {
        List<Tag> elements = parser.parse("<foo>");

        Assert.assertEquals(1, elements.size());
        Assert.assertEquals(OpenTag.class, elements.get(0).getClass());
        Assert.assertEquals("foo", elements.get(0).name());
    }

    @Test
    public void testThatRecognizesAnyCloseTag() {
        List<Tag> elements = parser.parse("</bar>");

        Assert.assertEquals(1, elements.size());
        Assert.assertEquals(CloseTag.class, elements.get(0).getClass());
        Assert.assertEquals("bar", elements.get(0).name());
    }

    @Test
    public void testThatFindsContent() {
        List<Tag> elements = parser.parse("</bar> content ");
        Assert.assertEquals(1, elements.size());
        Assert.assertEquals(CloseTag.class, elements.get(0).getClass());
        Assert.assertEquals("bar", elements.get(0).name());
        Assert.assertEquals(" content ", elements.get(0).content());
    }

    @Test
    public void testThatKeepsContentOnFirstTag() {
        List<Tag> elements = parser.parse("</bar> content <foo> foo content </foo>");

        Assert.assertEquals(3, elements.size());
        Assert.assertEquals(CloseTag.class, elements.get(0).getClass());
        Assert.assertEquals(OpenTag.class, elements.get(1).getClass());
        Assert.assertEquals(CloseTag.class, elements.get(2).getClass());
        Assert.assertEquals("bar", elements.get(0).name());
        Assert.assertEquals("foo", elements.get(1).name());
        Assert.assertEquals("foo", elements.get(2).name());
        Assert.assertEquals(" content ", elements.get(0).content());
        Assert.assertEquals(" foo content ", elements.get(1).content());

    }

    @Test
    public void testThatIgnoresWhiteCharOnTagName() {
        List<Tag> elements = parser.parse("</bar > content <foo> foo content </foo>");

        Assert.assertEquals(3, elements.size());
        Assert.assertEquals(CloseTag.class, elements.get(0).getClass());
        Assert.assertEquals(OpenTag.class, elements.get(1).getClass());
        Assert.assertEquals(CloseTag.class, elements.get(2).getClass());
        Assert.assertEquals("bar", elements.get(0).name());
        Assert.assertEquals("foo", elements.get(1).name());
        Assert.assertEquals("foo", elements.get(2).name());
        Assert.assertEquals(" content ", elements.get(0).content());
        Assert.assertEquals(" foo content ", elements.get(1).content());
    }

    @Test
    public void testThatRecognizesAtributes() {
        List<Tag> elements = parser.parse("</bar id=\"bla\"> content <foo attr='pong'> foo content </foo>");

        Assert.assertEquals(3, elements.size());
        Assert.assertEquals(CloseTag.class, elements.get(0).getClass());
        Assert.assertEquals(OpenTag.class, elements.get(1).getClass());
        Assert.assertEquals(CloseTag.class, elements.get(2).getClass());

        Assert.assertEquals("bar", elements.get(0).name());
        Assert.assertEquals("foo", elements.get(1).name());
        Assert.assertEquals("foo", elements.get(2).name());

        Assert.assertEquals(" content ", elements.get(0).content());
        Assert.assertEquals(" foo content ", elements.get(1).content());

        Assert.assertEquals("bla", elements.get(0).attributes().get("id"));
        Assert.assertEquals("pong", elements.get(1).attributes().get("attr"));
    }

    @Test
    public void testThatAcceptsWhiteSpaceOnAttributeValue() {
        List<Tag> elements = parser.parse("<a href=\"http://blog.caelum.com.br \" a=\"b\">Comunidade</a>");

        Assert.assertEquals(2, elements.size());

        Assert.assertEquals(OpenTag.class, elements.get(0).getClass());
        Assert.assertEquals(CloseTag.class, elements.get(1).getClass());

        Assert.assertEquals("a", elements.get(0).name());
        Assert.assertEquals("a", elements.get(1).name());

        Assert.assertEquals("http://blog.caelum.com.br ", elements.get(0).attributes().get("href"));
        Assert.assertEquals("b", elements.get(0).attributes().get("a"));

        Assert.assertEquals("Comunidade", elements.get(0).content());
    }

    @Test
    public void testThatAcceptsTheTagA() {
        List<Tag> elements = parser.parse("<a href=\"http://bla\">");

        Assert.assertEquals(1, elements.size());
        Assert.assertEquals(OpenTag.class, elements.get(0).getClass());
        Assert.assertEquals("a", elements.get(0).name());
        Assert.assertEquals("http://bla", elements.get(0).attributes().get("href"));

    }

    @Test
    public void testThatReadsComments() {
        List<Tag> elements = new TagParser().parse("<!-- -->");

        Assert.assertEquals(1, elements.size());
        Assert.assertEquals(BangTag.class, elements.get(0).getClass());

    }

    @Test
    public void testThatAcceptsSelfClosingTag() {
        List<Tag> elements = parser.parse("<hr />");

        Assert.assertEquals(1, elements.size());
        Assert.assertEquals(OpenTag.class, elements.get(0).getClass());
        Assert.assertEquals("hr", elements.get(0).name());
    }

    @Test
    public void testThatAcceptsSelfClosingTagWithAttributes() {
        List<Tag> elements = parser.parse("<hr a=\"b\"/>");

        Assert.assertEquals(1, elements.size());
        Assert.assertEquals(OpenTag.class, elements.get(0).getClass());
        Assert.assertEquals("hr", elements.get(0).name());
        Assert.assertEquals("b", elements.get(0).attributes().get("a"));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testThatReadsOnlyRelevantTags() {
        List<Tag> relevanteElements = new ArrayList<Tag>();
        relevanteElements.add(new OpenTag("tr", "", Collections.EMPTY_MAP));
        List<Tag> elements = new TagParser(relevanteElements).parse("<a> <b> <c> <tr> </tr> <td> <ty>");

        Assert.assertEquals(1, elements.size());
        Assert.assertEquals(OpenTag.class, elements.get(0).getClass());
        Assert.assertEquals("tr", elements.get(0).name());

    }
}
