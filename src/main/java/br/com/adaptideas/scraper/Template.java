package br.com.adaptideas.scraper;

import java.util.List;

/**
 * @author jonasabreu
 * 
 */
public interface Template<T> {

	List<T> match(Html html);

}
