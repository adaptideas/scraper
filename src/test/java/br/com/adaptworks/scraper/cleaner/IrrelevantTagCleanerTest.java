package br.com.adaptworks.scraper.cleaner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.adaptworks.scraper.tag.CloseTag;
import br.com.adaptworks.scraper.tag.OpenTag;
import br.com.adaptworks.scraper.tag.Tag;

/**
 * @author jonasabreu
 * 
 */
final public class IrrelevantTagCleanerTest {

	private TagCleaner cleaner;
	private HashMap<String, String> attributes;

	@Before
	public void setup() {
		List<Tag> list = new ArrayList<Tag>();
		attributes = new HashMap<String, String>();
		attributes.put("class", "title");

		list.add(new OpenTag("tr", "", attributes));
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
		HashMap<String, String> attributes = new HashMap<String, String>();
		attributes.put("class", "title foo");
		Assert.assertFalse(cleaner.shouldClean(new OpenTag("tr", "", attributes)));
	}

	@Test
	public void testThatRemovesTagWithoutAttributes() {
		HashMap<String, String> attributes = new HashMap<String, String>();
		attributes.put("class", "ti tle");
		Assert.assertTrue(cleaner.shouldClean(new OpenTag("tr", "", attributes)));
	}

	@Test
	public void testThatDoesNotRemovesTagWithSimilarContent() {
		List<Tag> list = new ArrayList<Tag>();
		list.add(new OpenTag("b", "Carga Horária", attributes));
		TagCleaner cleaner = new IrrelevantTagCleaner(list);
		Assert.assertFalse(cleaner.shouldClean(new OpenTag("b", "Carga Horária", attributes)));
	}

	@Test
	public void testThatRemovesTagWithDifferentContent() {
		List<Tag> list = new ArrayList<Tag>();
		list.add(new OpenTag("b", "Número de Horas", attributes));
		TagCleaner cleaner = new IrrelevantTagCleaner(list);
		Assert.assertTrue(cleaner.shouldClean(new OpenTag("b", "Carga Horária", attributes)));
	}
}
