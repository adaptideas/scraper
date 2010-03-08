package br.com.adaptworks.scraper;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

/**
 * @author jonasabreu
 * 
 */
final public class SplitTemplateTest {

	@Test
	public void testThatWorksAsSingleTemplate() {
		List<Item> match = new SplitTemplate<Item>("<td>${foo}</td>", Item.class).match(new Html("<td>test</td>"));

		Assert.assertEquals(1, match.size());
		Assert.assertEquals("test", match.get(0).foo());
	}

	@Test
	public void testThatIfFirstTemplateCantFindTheSecondFinds() {
		List<Item> match = new SplitTemplate<Item>("<tr>${foo}</tr><split><td>${foo}</td>", Item.class).match(new Html(
				"<td>test</td>"));

		Assert.assertEquals(1, match.size());
		Assert.assertEquals("test", match.get(0).foo());
	}

	@Test
	public void testThatSecondTemplateDoesNotOverrideFirst() {
		List<Item> match = new SplitTemplate<Item>("<tr>${foo}</tr><split><td>${foo}</td>", Item.class).match(new Html(
				"<tr>test</tr><td>foo</td>"));

		Assert.assertEquals(1, match.size());
		Assert.assertEquals("test", match.get(0).foo());
	}
}
