package br.com.adaptideas.scraper.matcher;

import static java.util.Collections.EMPTY_MAP;
import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import br.com.adaptideas.scraper.tag.Attribute;
import br.com.adaptideas.scraper.tag.OpenTag;

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
		TemplateTag tag = new TemplateTag(new OpenTag("td", "${name} simple text", EMPTY_MAP));
		Assert.assertEquals("anything to just ignore",
							tag.match(new OpenTag("td", "anything to just ignore simple text", EMPTY_MAP)).get("name"));
	}

	@Test
	public void testThatRecoverCaptureGroupWithoutOtherContent() {
		TemplateTag tag = new TemplateTag(new OpenTag("td", "${name}", EMPTY_MAP));
		Assert.assertEquals("anything to just ignore simple text",
							tag.match(new OpenTag("td", "anything to just ignore simple text", EMPTY_MAP)).get("name"));
	}

	@Test
	public void testThatRecoverWithMultipleCaptureGroups() {
		TemplateTag tag = new TemplateTag(new OpenTag("td", "${name} simple text ${test}", Collections.EMPTY_MAP));
		Assert.assertEquals("anything to just ignore",
							tag.match(new OpenTag("td", "anything to just ignore simple text foo", EMPTY_MAP))
									.get("name"));
		Assert.assertEquals("foo", tag.match(new OpenTag("td", "anything to just ignore simple text foo", EMPTY_MAP))
				.get("test"));
	}

	@Test
	public void testThatRecoverDataFromAttributes() {
		final Map<String, Attribute> templateMap = new HashMap<String, Attribute>();
		templateMap.put("a", Attribute.from("${abc}"));
		templateMap.put("b", Attribute.from("${asdf}"));
		TemplateTag tag = new TemplateTag(new OpenTag("td", "asdf", templateMap));

		final Map<String, Attribute> map = new HashMap<String, Attribute>();
		map.put("a", Attribute.from("123"));
		map.put("b", Attribute.from("456"));
		map.put("c", Attribute.from("blablabla"));
		assertEquals("123", tag.match(new OpenTag("td", "", map)).get("abc"));
		assertEquals("456", tag.match(new OpenTag("td", "", map)).get("asdf"));
	}

	@Test
	public void testThatRecoverDataFromAttributesWithMultipleCaptureGroups() {
		final Map<String, Attribute> templateMap = new HashMap<String, Attribute>();
		templateMap.put("a", Attribute.from("${abc} - ${bcd}"));
		TemplateTag tag = new TemplateTag(new OpenTag("td", "asdf", templateMap));

		final Map<String, Attribute> map = new HashMap<String, Attribute>();
		map.put("a", Attribute.from("123 - 456"));
		assertEquals("123", tag.match(new OpenTag("td", "", map)).get("abc"));
		assertEquals("456", tag.match(new OpenTag("td", "", map)).get("bcd"));
	}
}
