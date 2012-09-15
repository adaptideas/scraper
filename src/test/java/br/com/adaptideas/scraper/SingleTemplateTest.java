package br.com.adaptideas.scraper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Assert;
import org.junit.Test;

import br.com.adaptideas.scraper.converter.Converter;
import br.com.adaptideas.scraper.converter.DataConverter;
import br.com.adaptideas.scraper.exception.ScraperException;

/**
 * @author jonasabreu
 * 
 */
final public class SingleTemplateTest {

	@Test(expected = IllegalArgumentException.class)
	public void testThatThrowsExceptionIfInputStreamIsNull() {
		new SingleTemplate<Item>((InputStream) null, Item.class);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testThatThrowsExceptionIfTemplateIsNull() {
		new SingleTemplate<Item>((String) null, Item.class);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testThatThrowsExceptionIfTypeIsNull() {
		new SingleTemplate<Item>("", null);
	}

	@Test(expected = ScraperException.class)
	public void testThatThrowsExceptionFieldDoenstExists() {
		new SingleTemplate<Item>("<td>${fieldThatDoesntExist}</td>", Item.class).match(new Html("<td>123</td>"));
	}

	@Test
	public void testThatRecoversData() {
		Item match = new SingleTemplate<Item>("<td>${test}</td>", Item.class).match(new Html("<td>123</td>"));
		Assert.assertNotNull(match);
		Assert.assertEquals("123", match.test());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testThatUsesConvertersWhenSetting() {
		Mockery mockery = new Mockery();
		final Converter converter = mockery.mock(Converter.class);
		mockery.checking(new Expectations() {
			{
				oneOf(converter).accept(with(any(Class.class)));
				will(returnValue(true));

				oneOf(converter).convert(with("123"));
				will(returnValue("123"));
			}
		});

		List<Converter<?>> list = new ArrayList<Converter<?>>();
		list.add(converter);

		new SingleTemplate<Item>("<td>${test}</td>", Item.class, new DataConverter(list))
				.match(new Html("<td>123</td>"));

		mockery.assertIsSatisfied();
	}

	@Test
	public void testThatOnlySearchTagsOnTemplate2() {
		Item match = new SingleTemplate<Item>("<td>${test}</td>", Item.class).match(new Html("<td>123"));
		Assert.assertNull(match);
	}

	@Test
	public void testThatOnlySearchTagsOnTemplate() {
		Item match = new SingleTemplate<Item>("<td>${test}</td>", Item.class).match(new Html("<td><a>123</a></td>"));
		Assert.assertNotNull(match);
		Assert.assertEquals("123", match.test());
	}

	@Test
	public void testThatDoesNotRemoveImportantWhitespace() {
		Item match = new SingleTemplate<Item>("<td>${test}</td>", Item.class).match(new Html(
				"<td><p>more <b>some text </b>text</td>"));
		Assert.assertNotNull(match);
		Assert.assertEquals("more some text text", match.test());
	}

	@Test
	public void testThatOnlySearchTagsOnTemplate3() {
		Item match = new SingleTemplate<Item>("<td>${test}</td>", Item.class).match(new Html("<td><a>123</td>a"));
		Assert.assertNotNull(match);
		Assert.assertEquals("123", match.test());
	}

	@Test
	public void testThatOnlySearchTagsOnTemplateWithMultipleCaptureGroups() {
		Item match = new SingleTemplate<Item>("<td>${test} (${foo})</td>a", Item.class).match(new Html(
				"<td>123 (bar)</td>a"));
		Assert.assertNotNull(match);
		Assert.assertEquals("123", match.test());
		Assert.assertEquals("bar", match.foo());
	}

	@Test
	public void shouldMatchTagOnStrangeTags() {
		Item match = new SingleTemplate<Item>("<h1 class=\"titulo\">${test}</h1>", Item.class)
				.match(new Html(
						"<h1 class=\"titulo seguran-a-em-servidores-linux-norma-iso-27002-dist-ncia-466-\">Segurança em Servidores Linux: Norma ISO 27002 à distância (466)</h1>"));
		Assert.assertNotNull(match);
		Assert.assertEquals("Segurança em Servidores Linux: Norma ISO 27002 à distância (466)", match.test());
	}

	@Test
	public void testThatMatchesWithTextSpreadedAcrossTags() {
		Item match = new SingleTemplate<Item>("<span>A ${test} B...", Item.class).match(new Html(
				"<span> A<br>de<em>sc <p> B <td> asdf"));
		Assert.assertNotNull(match);
		Assert.assertEquals("de sc", match.test());
	}

	@Test
	public void testThatMatchesIfAttributeHasSharp() {
		Item match = new SingleTemplate<Item>("<span color=\"#ffffff\">${test}</span>", Item.class).match(new Html(
				"<span color=\"#ffffff\" asd=\"fgh\">abc</span>"));
		Assert.assertNotNull(match);
		Assert.assertEquals("abc", match.test());
	}

}
