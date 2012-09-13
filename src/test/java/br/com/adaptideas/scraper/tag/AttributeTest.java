package br.com.adaptideas.scraper.tag;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

final public class AttributeTest {

	@Test
	public void shouldReturnStringAttribute() {
		Attribute attribute = Attribute.from("simple value");

		assertEquals(StringAttribute.class, attribute.getClass());
		assertEquals("simple value", attribute.value());
	}

	@Test
	public void shouldReturnPatternAttribute() {
		Attribute attribute = Attribute.from("${capture} value");

		assertEquals(PatternAttribute.class, attribute.getClass());
		assertEquals("${capture} value", attribute.value());
		assertTrue(attribute.matches(new StringAttribute("anything value")));
	}

}
