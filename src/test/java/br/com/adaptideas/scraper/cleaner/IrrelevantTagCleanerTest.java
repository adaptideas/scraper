package br.com.adaptideas.scraper.cleaner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.adaptideas.scraper.matcher.TemplateTag;
import br.com.adaptideas.scraper.tag.Attribute;
import br.com.adaptideas.scraper.tag.CloseTag;
import br.com.adaptideas.scraper.tag.OpenTag;
import br.com.adaptideas.scraper.tag.StringAttribute;

/**
 * @author jonasabreu
 * 
 */
final public class IrrelevantTagCleanerTest {

	private TagCleaner cleaner;
	private HashMap<String, Attribute> attributes;

	@Before
	public void setup() {
		List<TemplateTag> list = new ArrayList<TemplateTag>();
		attributes = new HashMap<String, Attribute>();
		attributes.put("class", new StringAttribute("title"));

		list.add(new TemplateTag(new OpenTag("tr", "", attributes)));
		cleaner = new IrrelevantTagCleaner(list);
	}

	@Test
	public void testThatRemovesTagNotPresentOnRelevantTags() {
		Assert.assertTrue(cleaner.shouldClean(new OpenTag("bla", "", attributes)));
	}

	@Test
	public void testThatRemovesTagOfDifferentType() {
		Assert.assertTrue(cleaner.shouldClean(new CloseTag("bla", "", attributes)));
	}

	@Test
	public void testThatDoesNotRemovesTagPresentOnRelevantTags() {
		Assert.assertFalse(cleaner.shouldClean(new OpenTag("tr", "", attributes)));
	}

	@Test
	public void testThatDoesNotRemovesTagWithAttributes() {
		HashMap<String, Attribute> attributes = new HashMap<String, Attribute>();
		attributes.put("class", new StringAttribute("title foo"));
		Assert.assertFalse(cleaner.shouldClean(new OpenTag("tr", "", attributes)));
	}

	@Test
	public void testThatRemovesTagWithoutAttributes() {
		HashMap<String, Attribute> attributes = new HashMap<String, Attribute>();
		attributes.put("class", new StringAttribute("ti tle"));
		Assert.assertTrue(cleaner.shouldClean(new OpenTag("tr", "", attributes)));
	}

	@Test
	public void testThatDoesNotRemovesTagWithSimilarContent() {
		List<TemplateTag> list = new ArrayList<TemplateTag>();
		list.add(new TemplateTag(new OpenTag("b", "Carga Horária", attributes)));
		TagCleaner cleaner = new IrrelevantTagCleaner(list);
		Assert.assertFalse(cleaner.shouldClean(new OpenTag("b", "Carga Horária", attributes)));
	}

	@Test
	public void testThatDoesNotRemoveTagsWithMultipleCaptureGroups() {
		List<TemplateTag> list = new ArrayList<TemplateTag>();
		list.add(new TemplateTag(new OpenTag("b", "${test} (${foo})a", attributes)));
		TagCleaner cleaner = new IrrelevantTagCleaner(list);
		Assert.assertFalse(cleaner.shouldClean(new OpenTag("b", "123 (bar)a", attributes)));
	}
}
