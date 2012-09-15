package br.com.adaptideas.scraper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

final public class MultipleMatchTemplateTest {

	@Test
	public void shouldReturnAnEmptyListIfNoItemIsFound() {
		List<Item> list = new MultipleMatchTemplate<Item>("", Item.class).match(new Html(""));

		assertNotNull(list);
		assertEquals(0, list.size());
	}

	@Test
	public void shouldFindSingleItem() {
		List<Item> match = new MultipleMatchTemplate<Item>("<td>${foo}</td>", Item.class).match(new Html(
				"<td>test</td>"));

		assertNotNull(match);
		assertEquals(1, match.size());
		assertEquals("test", match.get(0).foo());
	}

	@Test
	public void shouldFindSingleItemWithSplit() {
		List<Item> match = new MultipleMatchTemplate<Item>("<td>${foo}</td><split><tr>${foo}</tr>", Item.class)
				.match(new Html("<tr>test</tr>"));

		assertNotNull(match);
		assertEquals(1, match.size());
		assertEquals("test", match.get(0).foo());
	}

	@Test
	public void shouldFindMultipleItems() {
		List<Item> match = new MultipleMatchTemplate<Item>("<td>${foo}</td>", Item.class).match(new Html(
				"<td>test</td> anything <td>test2</td>"));

		assertNotNull(match);
		assertEquals(2, match.size());
		assertEquals("test", match.get(0).foo());
		assertEquals("test2", match.get(1).foo());
	}
}
