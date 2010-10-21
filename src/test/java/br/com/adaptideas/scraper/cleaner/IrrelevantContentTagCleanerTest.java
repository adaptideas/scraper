package br.com.adaptideas.scraper.cleaner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import br.com.adaptideas.scraper.cleaner.IrrelevantContentTagCleaner;
import br.com.adaptideas.scraper.cleaner.TagCleaner;
import br.com.adaptideas.scraper.matcher.TemplateTag;
import br.com.adaptideas.scraper.tag.OpenTag;

/**
 * @author jonasabreu
 * 
 */
final public class IrrelevantContentTagCleanerTest {

	@SuppressWarnings("unchecked")
	@Test
	public void testThatRemovesTagWithDifferentContent() {
		List<TemplateTag> list = new ArrayList<TemplateTag>();
		list.add(new TemplateTag(new OpenTag("b", "Número de Horas", Collections.EMPTY_MAP)));
		TagCleaner cleaner = new IrrelevantContentTagCleaner(list);
		Assert.assertTrue(cleaner.shouldClean(new OpenTag("b", "Carga Horária", Collections.EMPTY_MAP)));
	}
}
