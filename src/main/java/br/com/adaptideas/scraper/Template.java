package br.com.adaptideas.scraper;


/**
 * @author jonasabreu
 * 
 */
public interface Template<T> {

	T match(Html html);

}
