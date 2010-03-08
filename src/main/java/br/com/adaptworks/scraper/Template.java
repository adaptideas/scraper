package br.com.adaptworks.scraper;

import java.util.List;

/**
 * @author jonasabreu
 * 
 */
public interface Template<T> {

	List<T> match(Html html);

}
