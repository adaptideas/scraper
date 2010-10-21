package br.com.adaptideas.scraper.converter;

/**
 * @author jonasabreu
 * 
 */
public interface Converter<T> {

	boolean accept(Class<T> to);

	T convert(String value);

}
