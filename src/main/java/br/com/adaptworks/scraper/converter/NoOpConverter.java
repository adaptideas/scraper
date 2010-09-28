package br.com.adaptworks.scraper.converter;

/**
 * @author jonasabreu
 * 
 */
final public class NoOpConverter implements Converter<Object> {

	@SuppressWarnings("unchecked")
	public boolean accept(final Class from) {
		return true;
	}

	public Object convert(final String value) {
		return value;
	}

}
