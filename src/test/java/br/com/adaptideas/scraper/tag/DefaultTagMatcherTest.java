package br.com.adaptideas.scraper.tag;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.adaptideas.scraper.matcher.TemplateTag;

/**
 * @author jonasabreu
 * 
 */
final public class DefaultTagMatcherTest {

	private TagMatcher matcher;
	private final HashMap<String, Attribute> emptyMap = new HashMap<String, Attribute>();

	@Before
	public void setup() {
		matcher = new DefaultTagMatcher();
	}

	@Test
	public void testThatMatchesSameElement() {
		Assert.assertTrue(matcher.matches(new TemplateTag(new FakeTag("td", "", emptyMap)), new FakeTag("td", "",
				emptyMap)));
	}

	@Test
	public void testThatMatchingIsCaseInsensitive() {
		Assert.assertTrue(matcher.matches(new TemplateTag(new FakeTag("Td", "", emptyMap)), new FakeTag("tD", "",
				emptyMap)));
	}

	@Test
	public void testThatDoesntMatchDifferentElementType() {
		Assert.assertFalse(matcher.matches(new TemplateTag(new FakeTag("td", "", emptyMap)), new OtherFakeTag("td", "",
				emptyMap)));
	}

	@Test
	public void testThatDoenstMatchIfTagNameIsDifferent() {
		Assert.assertFalse(matcher.matches(new TemplateTag(new FakeTag("td", "", emptyMap)), new FakeTag("td2", "",
				emptyMap)));
	}

	@Test
	public void testThatIgnoresAttributesNotSettedOnTemplateElement() {
		Map<String, Attribute> map = new HashMap<String, Attribute>();
		map.put("foo", new StringAttribute("bar"));
		Assert.assertTrue(matcher.matches(new TemplateTag(new FakeTag("td", "", emptyMap)), new FakeTag("td", "", map)));
	}

	@Test
	public void testThatOnlyMatchesIfAttributesSettedOnTemplateWereSettedOnHtml() {
		HashMap<String, Attribute> map = new HashMap<String, Attribute>();
		map.put("foo", new StringAttribute("bar"));
		map.put("foo2", new StringAttribute("bar2"));
		HashMap<String, Attribute> map2 = new HashMap<String, Attribute>(map);
		map2.put("foo3", new StringAttribute("bar3"));
		Assert.assertTrue(matcher.matches(new TemplateTag(new FakeTag("td", "", map)), new FakeTag("td", "", map2)));
	}

	@Test
	public void testThatDoesNotMatchIfTemplateAttributeIsNotFoundOnHtml() {
		HashMap<String, Attribute> map = new HashMap<String, Attribute>();
		map.put("foo", new StringAttribute("bar"));
		map.put("foo2", new StringAttribute("bar2"));
		HashMap<String, Attribute> map2 = new HashMap<String, Attribute>(map);
		map2.put("foo3", new StringAttribute("bar3"));
		Assert.assertFalse(matcher.matches(new TemplateTag(new FakeTag("td", "", map2)), new FakeTag("td", "", map)));
	}

	@Test
	public void testThatDoesNotMatchIfAttributeValueIsDifferent() {
		HashMap<String, Attribute> contentMap = new HashMap<String, Attribute>();
		contentMap.put("foo", new StringAttribute("bar"));
		contentMap.put("foo2", new StringAttribute("bar"));
		HashMap<String, Attribute> templateMap = new HashMap<String, Attribute>(contentMap);
		templateMap.remove("foo2");
		templateMap.put("foo2", new StringAttribute("bar2"));
		Assert.assertFalse(matcher.matches(new TemplateTag(new FakeTag("td", "", templateMap)), new FakeTag("td", "",
				contentMap)));
	}

	@Test
	public void testThatDoesNotMatchIfAttributeIsNotSettedOnHtml() {
		HashMap<String, Attribute> map = new HashMap<String, Attribute>();
		map.put("foo", new StringAttribute("bar"));
		HashMap<String, Attribute> map2 = new HashMap<String, Attribute>();
		map2.put("foo2", new StringAttribute("bar"));
		Assert.assertFalse(matcher.matches(new TemplateTag(new FakeTag("td", "", map2)), new FakeTag("td", "", map)));
	}

	@Test
	public void testThatMatchesIfAttributeContentIsContainedInHtmlAttribute() {
		HashMap<String, Attribute> templateMap = new HashMap<String, Attribute>();
		templateMap.put("class", new StringAttribute("titulo"));

		HashMap<String, Attribute> contentMap = new HashMap<String, Attribute>();
		contentMap.put("class", new StringAttribute("titulo foo"));

		Assert.assertTrue(matcher.matches(new TemplateTag(new FakeTag("td", "", templateMap)), new FakeTag("td", "",
				contentMap)));
	}

	private static class FakeTag implements Tag {
		private final Map<String, Attribute> attributes;
		private final String content;
		private final String name;

		public FakeTag(final String name, final String content, final Map<String, Attribute> attributes) {
			this.name = name;
			this.content = content;
			this.attributes = attributes;
		}

		public Map<String, Attribute> attributes() {
			return attributes;
		}

		public String content() {
			return content;
		}

		public String name() {
			return name;
		}

		public Attribute attribute(final String key) {
			return attributes.get(key);
		}

		public TagType type() {
			return TagType.OPEN;
		}
	}

	private static class OtherFakeTag implements Tag {
		private final Map<String, Attribute> attributes;
		private final String content;
		private final String name;

		public OtherFakeTag(final String name, final String content, final Map<String, Attribute> attributes) {
			this.name = name;
			this.content = content;
			this.attributes = attributes;
		}

		public Map<String, Attribute> attributes() {
			return attributes;
		}

		public String content() {
			return content;
		}

		public String name() {
			return name;
		}

		public Attribute attribute(final String key) {
			return attributes.get(key);
		}

		public TagType type() {
			return TagType.CLOSE;
		}

	}

}
