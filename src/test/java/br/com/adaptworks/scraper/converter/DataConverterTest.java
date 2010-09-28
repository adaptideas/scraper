package br.com.adaptworks.scraper.converter;

import org.junit.Assert;

import org.junit.Test;

/**
 * @author jonasabreu
 * 
 */
final public class DataConverterTest {

	@Test
	public void testThatDoesNotConvertValuesIfNotNeeded() {
		DataConverter converter = new DataConverter();

		Assert.assertEquals("123", converter.convert("123", String.class));
	}

}
