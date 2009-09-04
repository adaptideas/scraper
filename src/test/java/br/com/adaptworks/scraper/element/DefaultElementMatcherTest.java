package br.com.adaptworks.scraper.element;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

/**
 * @author jonasabreu
 * 
 */
final public class DefaultElementMatcherTest {

    private ElementMatcher matcher;
    private final Map<String, String> emptyMap = new HashMap<String, String>();

    @Before
    public void setup() {
        matcher = new DefaultElementMatcher();
    }

    @Test
    public void testThatMatchesSameElement() {
        Assert.assertTrue(matcher.matches(new FakeElement("td", "", emptyMap), new FakeElement("td", "", emptyMap)));
    }

    @Test
    public void testThatIgnoresContent() {
        Assert.assertTrue(matcher.matches(new FakeElement("td", "sdkjsadkajsd", emptyMap), new FakeElement("td", "",
                emptyMap)));
        Assert.assertTrue(matcher.matches(new FakeElement("td", "", emptyMap), new FakeElement("td", "sdkjsadkajsd",
                emptyMap)));
    }

    @Test
    public void testThatDoesntMatchDifferentElementType() {
        Assert.assertFalse(matcher.matches(new FakeElement("td", "", emptyMap),
                new OtherFakeElement("td", "", emptyMap)));
    }

    @Test
    public void testThatDoenstMatchIfTagNameIsDifferent() {
        Assert.assertFalse(matcher.matches(new FakeElement("td", "", emptyMap), new FakeElement("td2", "", emptyMap)));
    }

    @Test
    public void testThatIgnoresAttributesNotSettedOnTemplateElement() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("foo", "bar");
        Assert.assertTrue(matcher.matches(new FakeElement("td", "", emptyMap), new FakeElement("td", "", map)));
    }

    @Test
    public void testThatOnlyMatchesIfAttributesSettedOnTemplateWereSettedOnHtml() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("foo", "bar");
        map.put("foo2", "bar2");
        Map<String, String> map2 = new HashMap<String, String>(map);
        map2.put("foo3", "bar3");
        Assert.assertTrue(matcher.matches(new FakeElement("td", "", map), new FakeElement("td", "", map2)));
    }

    @Test
    public void testThatDoesNotMatchIfTemplateAttributeIsNotFoundOnHtml() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("foo", "bar");
        map.put("foo2", "bar2");
        Map<String, String> map2 = new HashMap<String, String>(map);
        map2.put("foo3", "bar3");
        Assert.assertFalse(matcher.matches(new FakeElement("td", "", map2), new FakeElement("td", "", map)));
    }

    @Test
    public void testThatDoesNotMatchIfAttributeValueIsDifferent() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("foo", "bar");
        map.put("foo2", "bar2");
        Map<String, String> map2 = new HashMap<String, String>(map);
        map2.remove("foo2");
        map2.put("foo2", "bar");
        Assert.assertFalse(matcher.matches(new FakeElement("td", "", map2), new FakeElement("td", "", map)));
    }

    @Test
    public void testThatDoesNotMatchIfAttributeIsNotSettedOnHtml() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("foo", "bar");
        Map<String, String> map2 = new HashMap<String, String>();
        map2.put("foo2", "bar");
        Assert.assertFalse(matcher.matches(new FakeElement("td", "", map2), new FakeElement("td", "", map)));
    }

    private static class FakeElement implements Element {
        private final Map<String, String> attributes;
        private final String content;
        private final String name;

        public FakeElement(final String name, final String content, final Map<String, String> attributes) {
            this.name = name;
            this.content = content;
            this.attributes = attributes;
        }

        public Map<String, String> getAttributes() {
            return attributes;
        }

        public String getContent() {
            return content;
        }

        public String getName() {
            return name;
        }
    }

    private static class OtherFakeElement implements Element {
        private final Map<String, String> attributes;
        private final String content;
        private final String name;

        public OtherFakeElement(final String name, final String content, final Map<String, String> attributes) {
            this.name = name;
            this.content = content;
            this.attributes = attributes;
        }

        public Map<String, String> getAttributes() {
            return attributes;
        }

        public String getContent() {
            return content;
        }

        public String getName() {
            return name;
        }
    }

}
