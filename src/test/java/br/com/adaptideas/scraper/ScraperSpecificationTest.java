package br.com.adaptideas.scraper;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author jonasabreu
 * 
 */
final public class ScraperSpecificationTest {

	@Test
	public void testThatSimpleTagWorksWithCaptureGroup() {
		Item item = new SingleTemplate<Item>("<td>\n${test}</td>", Item.class).match(new Html("<td>content</td>"));
		Assert.assertEquals("content", item.test());

		item = new SingleTemplate<Item>("<td>${test}\n</td>", Item.class).match(new Html("<td>content</td>"));
		Assert.assertEquals("content", item.test());
	}

	@Test
	public void testThatTagWithAttributesWorksWithCaptureGroup() {
		Item item = new SingleTemplate<Item>("<td class=\"test\">${test}\n</td>", Item.class).match(new Html(
				"<td>content</td>"));
		Assert.assertNull(item);

		item = new SingleTemplate<Item>("<td class=\"test\">\n${test}</td>", Item.class).match(new Html(
				"<td class=\"test\">content</td><td>should not capture</td>"));
		Assert.assertNotNull(item);
		Assert.assertEquals("content", item.test());

		item = new SingleTemplate<Item>("<td class=\"test\">${test}\n</td>", Item.class).match(new Html(
				"<td class=\"test\">content</td><td>should not capture</td>"));
		Assert.assertNotNull(item);
		Assert.assertEquals("content", item.test());
	}

	@Test
	public void testThatTagWithContentWorksWithCaptureGroup() {
		Item item = new SingleTemplate<Item>("<td>content</td>\n${test}", Item.class).match(new Html("<td></td>test"));
		Assert.assertNull(item);

		item = new SingleTemplate<Item>("<td>content</td>\n${test}</td>", Item.class).match(new Html(
				"<td>content</td><td>should capture</td>"));
		Assert.assertNotNull(item);
		Assert.assertEquals("should capture", item.test());

		item = new SingleTemplate<Item>("<td>content</td>${test}\n</td>", Item.class).match(new Html(
				"<td>content</td><td>should capture</td>"));
		Assert.assertNotNull(item);
		Assert.assertEquals("should capture", item.test());
	}

	@Test
	public void testThatTagWithContentWorksWithCaptureGroupAndContent() {
		Item item = new SingleTemplate<Item>("<td>content ${test}</td>", Item.class).match(new Html(
				"<td>cont capture</td>"));
		Assert.assertNull(item);

		item = new SingleTemplate<Item>("<td>content ${test}</td>", Item.class).match(new Html(
				"<td>content capture</td>"));
		Assert.assertNotNull(item);
		Assert.assertEquals("capture", item.test());

		item = new SingleTemplate<Item>("<td>${test} content</td>", Item.class).match(new Html(
				"<td>capture content</td>"));
		Assert.assertNotNull(item);
		Assert.assertEquals("capture", item.test());
	}

	@Test
	public void testThatTagWithContentWorksWithMultipleCaptureGroupsAndContent() {
		Item item = new SingleTemplate<Item>("<td>${foo} content ${test}</td>", Item.class).match(new Html(
				"<td>bar cont capture</td>"));
		Assert.assertNull(item);

		item = new SingleTemplate<Item>("<td>${foo} content ${test}</td>", Item.class).match(new Html(
				"<td>bar content capture</td>"));
		Assert.assertNotNull(item);
		Assert.assertEquals("capture", item.test());
		Assert.assertEquals("bar", item.foo());

		item = new SingleTemplate<Item>("<td>ignore ${foo} content ${test}</td>", Item.class).match(new Html(
				"<td>ignore bar content capture</td>"));
		Assert.assertNotNull(item);
		Assert.assertEquals("capture", item.test());
		Assert.assertEquals("bar", item.foo());

		item = new SingleTemplate<Item>("<td>ignore ${foo} content ${test} ignore</td>", Item.class).match(new Html(
				"<td>ignore bar content capture ignore</td>"));
		Assert.assertNotNull(item);
		Assert.assertEquals("capture", item.test());
		Assert.assertEquals("bar", item.foo());
	}

	@Test
	public void testThatTagWorksWithCaptureGroupsAndIgnoreAllMatcher() {
		Item item = new SingleTemplate<Item>("<td>${test} hour ...</td>", Item.class).match(new Html(
				"<td>capture hour just some text to ignore</td>"));
		Assert.assertNotNull(item);
		Assert.assertEquals("capture", item.test());

		item = new SingleTemplate<Item>("<td>before ${test} hour ...</td>", Item.class).match(new Html(
				"<td>before bar foo hour capture</td>"));
		Assert.assertNotNull(item);
		Assert.assertEquals("bar foo", item.test());

		item = new SingleTemplate<Item>("<td>... before ${test} hour ...</td>", Item.class).match(new Html(
				"<td>something just to be ignored before bar foo hour capture</td>"));
		Assert.assertNotNull(item);
		Assert.assertEquals("bar foo", item.test());
	}

	@Test
	public void testThatTagWorksWithMultipleCaptureGroupsAndIgnoreAllMatcher() {
		Item item = new SingleTemplate<Item>("<td>${foo} ignore ${test} hour ...</td>", Item.class).match(new Html(
				"<td>something ignore capture hour just some text to ignore</td>"));
		Assert.assertNotNull(item);
		Assert.assertEquals("capture", item.test());
		Assert.assertEquals("something", item.foo());

		item = new SingleTemplate<Item>("<td>before ${foo} before ${test} hour ...</td>", Item.class).match(new Html(
				"<td>before jarjar before bar foo hour capture</td>"));
		Assert.assertNotNull(item);
		Assert.assertEquals("bar foo", item.test());
		Assert.assertEquals("jarjar", item.foo());

		item = new SingleTemplate<Item>("<td>... before ${foo} before ${test} hour ...</td>", Item.class)
				.match(new Html("<td>more content to ignore before jarjar before bar foo hour capture</td>"));
		Assert.assertNotNull(item);
		Assert.assertEquals("bar foo", item.test());
		Assert.assertEquals("jarjar", item.foo());
	}

	@Test
	public void testThatRecognizesWithWhiteSpaces() {
		Item item = new SingleTemplate<Item>("<strong> Carga hor&aacute;ria - ${test}h</strong>", Item.class)
				.match(new Html("<strong> Carga hor&aacute;ria - 16h</strong>"));
		Assert.assertNotNull(item);
		Assert.assertEquals("16", item.test());
	}

	@Test
	public void testThatAttributeCaptureGroupsWork() {
		Item item = new SingleTemplate<Item>("<a href=\"${test}\">", Item.class).match(new Html(
				"<a href=\"www.google.com\">"));

		Assert.assertNotNull(item);
		Assert.assertEquals("www.google.com", item.test());
	}
}
