package br.com.adaptworks.scraper;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

/**
 * @author jonasabreu
 * 
 */
final public class ScraperSpecificationTest {

    @Test
    public void testThatSimpleTagWorksWithCaptureGroup() {
        List<Item> items = new SingleTemplate<Item>("<td>\n${test}</td>", Item.class).match(new Html("<td>content</td>"));
        Assert.assertEquals(1, items.size());
        Assert.assertEquals("content", items.get(0).test());

        items = new SingleTemplate<Item>("<td>${test}\n</td>", Item.class).match(new Html("<td>content</td>"));
        Assert.assertEquals(1, items.size());
        Assert.assertEquals("content", items.get(0).test());
    }

    @Test
    public void testThatTagWithAttributesWorksWithCaptureGroup() {
        List<Item> items = new SingleTemplate<Item>("<td class=\"test\">${test}\n</td>", Item.class).match(new Html(
                "<td>content</td>"));
        Assert.assertEquals(0, items.size());

        items = new SingleTemplate<Item>("<td class=\"test\">\n${test}</td>", Item.class).match(new Html(
                "<td class=\"test\">content</td><td>should not capture</td>"));
        Assert.assertEquals(1, items.size());
        Assert.assertEquals("content", items.get(0).test());

        items = new SingleTemplate<Item>("<td class=\"test\">${test}\n</td>", Item.class).match(new Html(
                "<td class=\"test\">content</td><td>should not capture</td>"));
        Assert.assertEquals(1, items.size());
        Assert.assertEquals("content", items.get(0).test());
    }

    @Test
    public void testThatTagWithContentWorksWithCaptureGroup() {
        List<Item> items = new SingleTemplate<Item>("<td>content</td>\n${test}", Item.class).match(new Html("<td></td>test"));
        Assert.assertEquals(0, items.size());

        items = new SingleTemplate<Item>("<td>content</td>\n${test}</td>", Item.class).match(new Html(
                "<td>content</td><td>should capture</td>"));
        Assert.assertEquals(1, items.size());
        Assert.assertEquals("should capture", items.get(0).test());

        items = new SingleTemplate<Item>("<td>content</td>${test}\n</td>", Item.class).match(new Html(
                "<td>content</td><td>should capture</td>"));
        Assert.assertEquals(1, items.size());
        Assert.assertEquals("should capture", items.get(0).test());
    }

    @Test
    public void testThatTagWithContentWorksWithCaptureGroupAndContent() {
        List<Item> items = new SingleTemplate<Item>("<td>content ${test}</td>", Item.class).match(new Html(
                "<td>cont capture</td>"));
        Assert.assertEquals(0, items.size());

        items = new SingleTemplate<Item>("<td>content ${test}</td>", Item.class).match(new Html("<td>content capture</td>"));
        Assert.assertEquals(1, items.size());
        Assert.assertEquals("capture", items.get(0).test());

        items = new SingleTemplate<Item>("<td>${test} content</td>", Item.class).match(new Html("<td>capture content</td>"));
        Assert.assertEquals(1, items.size());
        Assert.assertEquals("capture", items.get(0).test());
    }

    @Test
    public void testThatTagWithContentWorksWithMultipleCaptureGroupsAndContent() {
        List<Item> items = new SingleTemplate<Item>("<td>${foo} content ${test}</td>", Item.class).match(new Html(
                "<td>bar cont capture</td>"));
        Assert.assertEquals(0, items.size());

        items = new SingleTemplate<Item>("<td>${foo} content ${test}</td>", Item.class).match(new Html(
                "<td>bar content capture</td>"));
        Assert.assertEquals(1, items.size());
        Assert.assertEquals("capture", items.get(0).test());
        Assert.assertEquals("bar", items.get(0).foo());

        items = new SingleTemplate<Item>("<td>ignore ${foo} content ${test}</td>", Item.class).match(new Html(
                "<td>ignore bar content capture</td>"));
        Assert.assertEquals(1, items.size());
        Assert.assertEquals("capture", items.get(0).test());
        Assert.assertEquals("bar", items.get(0).foo());

        items = new SingleTemplate<Item>("<td>ignore ${foo} content ${test} ignore</td>", Item.class).match(new Html(
                "<td>ignore bar content capture ignore</td>"));
        Assert.assertEquals(1, items.size());
        Assert.assertEquals("capture", items.get(0).test());
        Assert.assertEquals("bar", items.get(0).foo());
    }

    @Test
    public void testThatTagWorksWithCaptureGroupsAndIgnoreAllMatcher() {
        List<Item> items = new SingleTemplate<Item>("<td>${test} hour ...</td>", Item.class).match(new Html(
                "<td>capture hour just some text to ignore</td>"));
        Assert.assertEquals(1, items.size());
        Assert.assertEquals("capture", items.get(0).test());

        items = new SingleTemplate<Item>("<td>before ${test} hour ...</td>", Item.class).match(new Html(
                "<td>before bar foo hour capture</td>"));
        Assert.assertEquals(1, items.size());
        Assert.assertEquals("bar foo", items.get(0).test());

        items = new SingleTemplate<Item>("<td>... before ${test} hour ...</td>", Item.class).match(new Html(
                "<td>something just to be ignored before bar foo hour capture</td>"));
        Assert.assertEquals(1, items.size());
        Assert.assertEquals("bar foo", items.get(0).test());
    }

    @Test
    public void testThatTagWorksWithMultipleCaptureGroupsAndIgnoreAllMatcher() {
        List<Item> items = new SingleTemplate<Item>("<td>${foo} ignore ${test} hour ...</td>", Item.class).match(new Html(
                "<td>something ignore capture hour just some text to ignore</td>"));
        Assert.assertEquals(1, items.size());
        Assert.assertEquals("capture", items.get(0).test());
        Assert.assertEquals("something", items.get(0).foo());

        items = new SingleTemplate<Item>("<td>before ${foo} before ${test} hour ...</td>", Item.class).match(new Html(
                "<td>before jarjar before bar foo hour capture</td>"));
        Assert.assertEquals(1, items.size());
        Assert.assertEquals("bar foo", items.get(0).test());
        Assert.assertEquals("jarjar", items.get(0).foo());

        items = new SingleTemplate<Item>("<td>... before ${foo} before ${test} hour ...</td>", Item.class).match(new Html(
                "<td>more content to ignore before jarjar before bar foo hour capture</td>"));
        Assert.assertEquals(1, items.size());
        Assert.assertEquals("bar foo", items.get(0).test());
        Assert.assertEquals("jarjar", items.get(0).foo());
    }
}
