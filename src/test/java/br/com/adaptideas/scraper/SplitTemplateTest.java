package br.com.adaptideas.scraper;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author jonasabreu
 * 
 */
final public class SplitTemplateTest {

	@Test
	public void testThatWorksAsSingleTemplate() {
		Item match = new SplitTemplate<Item>("<td>${foo}</td>", Item.class).match(new Html("<td>test</td>"));

		Assert.assertNotNull(match);
		Assert.assertEquals("test", match.foo());
	}

	@Test
	public void testThatIfFirstTemplateCantFindTheSecondFinds() {
		Item match = new SplitTemplate<Item>("<tr>${foo}</tr><split><td>${foo}</td>", Item.class).match(new Html(
				"<td>test</td>"));

		Assert.assertNotNull(match);
		Assert.assertEquals("test", match.foo());
	}

	@Test
	public void testThatSecondTemplateDoesNotOverrideFirst() {
		Item match = new SplitTemplate<Item>("<tr>${foo}</tr><split><td>${foo}</td>", Item.class).match(new Html(
				"<tr>test</tr><td>foo</td>"));

		Assert.assertNotNull(match);
		Assert.assertEquals("test", match.foo());
	}
}
