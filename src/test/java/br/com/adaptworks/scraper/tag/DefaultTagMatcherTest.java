package br.com.adaptworks.scraper.tag;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import br.com.adaptworks.scraper.tag.Tag;
import br.com.adaptworks.scraper.tag.TagMatcher;
import br.com.adaptworks.scraper.tag.TagType;

/**
 * @author jonasabreu
 * 
 */
final public class DefaultTagMatcherTest {

    private TagMatcher matcher;
    private final Map<String, String> emptyMap = new HashMap<String, String>();

    @Before
    public void setup() {
        matcher = new DefaultTagMatcher();
    }

    @Test
    public void testThatMatchesSameElement() {
        Assert.assertTrue(matcher.matches(new FakeTag("td", "", emptyMap), new FakeTag("td", "", emptyMap)));
    }

    @Test
    public void testThatIgnoresContent() {
        Assert
            .assertTrue(matcher.matches(new FakeTag("td", "sdkjsadkajsd", emptyMap), new FakeTag("td", "", emptyMap)));
        Assert
            .assertTrue(matcher.matches(new FakeTag("td", "", emptyMap), new FakeTag("td", "sdkjsadkajsd", emptyMap)));
    }

    @Test
    public void testThatDoesntMatchDifferentElementType() {
        Assert.assertFalse(matcher.matches(new FakeTag("td", "", emptyMap), new OtherFakeTag("td", "", emptyMap)));
    }

    @Test
    public void testThatDoenstMatchIfTagNameIsDifferent() {
        Assert.assertFalse(matcher.matches(new FakeTag("td", "", emptyMap), new FakeTag("td2", "", emptyMap)));
    }

    @Test
    public void testThatIgnoresAttributesNotSettedOnTemplateElement() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("foo", "bar");
        Assert.assertTrue(matcher.matches(new FakeTag("td", "", emptyMap), new FakeTag("td", "", map)));
    }

    @Test
    public void testThatOnlyMatchesIfAttributesSettedOnTemplateWereSettedOnHtml() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("foo", "bar");
        map.put("foo2", "bar2");
        Map<String, String> map2 = new HashMap<String, String>(map);
        map2.put("foo3", "bar3");
        Assert.assertTrue(matcher.matches(new FakeTag("td", "", map), new FakeTag("td", "", map2)));
    }

    @Test
    public void testThatDoesNotMatchIfTemplateAttributeIsNotFoundOnHtml() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("foo", "bar");
        map.put("foo2", "bar2");
        Map<String, String> map2 = new HashMap<String, String>(map);
        map2.put("foo3", "bar3");
        Assert.assertFalse(matcher.matches(new FakeTag("td", "", map2), new FakeTag("td", "", map)));
    }

    @Test
    public void testThatDoesNotMatchIfAttributeValueIsDifferent() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("foo", "bar");
        map.put("foo2", "bar2");
        Map<String, String> map2 = new HashMap<String, String>(map);
        map2.remove("foo2");
        map2.put("foo2", "bar");
        Assert.assertFalse(matcher.matches(new FakeTag("td", "", map2), new FakeTag("td", "", map)));
    }

    @Test
    public void testThatDoesNotMatchIfAttributeIsNotSettedOnHtml() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("foo", "bar");
        Map<String, String> map2 = new HashMap<String, String>();
        map2.put("foo2", "bar");
        Assert.assertFalse(matcher.matches(new FakeTag("td", "", map2), new FakeTag("td", "", map)));
    }

    private static class FakeTag implements Tag {
        private final Map<String, String> attributes;
        private final String content;
        private final String name;

        public FakeTag(final String name, final String content, final Map<String, String> attributes) {
            this.name = name;
            this.content = content;
            this.attributes = attributes;
        }

        public Map<String, String> attributes() {
            return attributes;
        }

        public String content() {
            return content;
        }

        public String name() {
            return name;
        }

        public String attribute(final String key) {
            return attributes.get(key);
        }

        public TagType type() {
            return TagType.OPEN;
        }
    }

    private static class OtherFakeTag implements Tag {
        private final Map<String, String> attributes;
        private final String content;
        private final String name;

        public OtherFakeTag(final String name, final String content, final Map<String, String> attributes) {
            this.name = name;
            this.content = content;
            this.attributes = attributes;
        }

        public Map<String, String> attributes() {
            return attributes;
        }

        public String content() {
            return content;
        }

        public String name() {
            return name;
        }

        public String attribute(final String key) {
            return attributes.get(key);
        }

        public TagType type() {
            return TagType.OPEN;
        }

    }

}
