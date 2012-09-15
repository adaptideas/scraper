package br.com.adaptideas.scraper.cleaner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
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
final public class IrrelevantContentTagCleanerTest {

	@Test
	@SuppressWarnings("unchecked")
	public void testThatRemovesTagWithDifferentContent() {
		List<TemplateTag> list = new ArrayList<TemplateTag>();
		list.add(new TemplateTag(new OpenTag("b", "Número de Horas", Collections.EMPTY_MAP)));
		TagCleaner cleaner = new IrrelevantContentTagCleaner(list);
		Assert.assertTrue(cleaner.shouldClean(new OpenTag("b", "Carga Horária", Collections.EMPTY_MAP)));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void shouldNotRemoveTagWithComplexAttribute() {
		List<TemplateTag> list = new ArrayList<TemplateTag>();
		HashMap<String, Attribute> templateMap = new HashMap<String, Attribute>();
		templateMap.put("class", new StringAttribute("titulo"));
		list.add(new TemplateTag(new OpenTag("h1", "${test}", templateMap)));
		list.add(new TemplateTag(new CloseTag("/h1", "", Collections.EMPTY_MAP)));

		TagCleaner cleaner = new IrrelevantContentTagCleaner(list);

		HashMap<String, Attribute> map = new HashMap<String, Attribute>();
		map.put("class", new StringAttribute("titulo seguran-a-em-servidores-linux-norma-iso-27002-dist-ncia-466-"));

		Assert.assertFalse(cleaner.shouldClean(new OpenTag("h1",
				"Segurança em Servidores Linux: Norma ISO 27002 a distancia (466)", map)));
	}
}
