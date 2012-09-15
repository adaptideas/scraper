package br.com.adaptideas.scraper.converter;

/**
 * @author jonasabreu
 * 
 */
final public class NoOpConverter implements Converter<Object> {

	public boolean accept(final Class<Object> from) {
		return true;
	}

	public Object convert(final String value) {
		return value;
	}

}
