package br.com.adaptworks.scraper.matcher;

import java.util.Collections;

import junit.framework.Assert;

import org.junit.Test;

import br.com.adaptworks.scraper.tag.OpenTag;

/**
 * @author jonasabreu
 * 
 */
@SuppressWarnings("unchecked")
final public class TemplateTagTest {

    @Test
    public void testThatMatchesOrdinaryWords() {
        TemplateTag tag = new TemplateTag(new OpenTag("td", "simple text", Collections.EMPTY_MAP));
        Assert.assertTrue(tag.matches("simple text"));
    }

    @Test
    public void testThatMatchesWithEllipsis() {
        TemplateTag tag = new TemplateTag(new OpenTag("td", "... simple text", Collections.EMPTY_MAP));
        Assert.assertTrue(tag.matches("anything to just ignore simple text"));
    }

    @Test
    public void testThatMatchesWithCaptureGroups() {
        TemplateTag tag = new TemplateTag(new OpenTag("td", "${name} simple text", Collections.EMPTY_MAP));
        Assert.assertTrue(tag.matches("anything to just ignore simple text"));
    }

    @Test
    public void testThatMatchesWithCaptureGroupWithoutOtherContent() {
        TemplateTag tag = new TemplateTag(new OpenTag("td", "${name}", Collections.EMPTY_MAP));
        Assert.assertTrue(tag.matches("anything to just ignore simple text"));
    }

    @Test
    public void testThatMatchesWithCaptureGroupWithWhiteSpaceWithoutOtherContent() {
        TemplateTag tag = new TemplateTag(new OpenTag("td", " ${name}", Collections.EMPTY_MAP));
        Assert.assertTrue(tag.matches("anything to just ignore simple text"));
    }

    @Test
    public void testThatMatchesWithBizarreContent() {
        TemplateTag tag = new TemplateTag(new OpenTag("td", "... anything ${name} simple ${other} text ...",
                Collections.EMPTY_MAP));
        Assert.assertTrue(tag.matches("a anything to just ignore simple and normal text after"));
    }

    @Test
    public void testThatMatcherIsCaseInsensitive() {
        TemplateTag tag = new TemplateTag(new OpenTag("td", "... ANYthing ${name} simple ${other} text ...",
                Collections.EMPTY_MAP));
        Assert.assertTrue(tag.matches("a anything to JUST ignore simple and normal text after"));
    }

    @Test
    public void testThatRecoverCaptureGroup() {
        TemplateTag tag = new TemplateTag(new OpenTag("td", "${name} simple text", Collections.EMPTY_MAP));
        Assert.assertEquals("anything to just ignore", tag.match("anything to just ignore simple text").get("name"));
    }

    @Test
    public void testThatRecoverCaptureGroupWithoutOtherContent() {
        TemplateTag tag = new TemplateTag(new OpenTag("td", "${name}", Collections.EMPTY_MAP));
        Assert.assertEquals("anything to just ignore simple text", tag
            .match("anything to just ignore simple text")
            .get("name"));
    }

    @Test
    public void testThatRecoverWithMultipleCaptureGroups() {
        TemplateTag tag = new TemplateTag(new OpenTag("td", "${name} simple text ${test}", Collections.EMPTY_MAP));
        Assert
            .assertEquals("anything to just ignore", tag.match("anything to just ignore simple text foo").get("name"));
        Assert.assertEquals("foo", tag.match("anything to just ignore simple text foo").get("test"));
    }

}
