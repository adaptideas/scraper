package br.com.adaptideas.scraper.exception;

/**
 * @author jonasabreu
 * 
 */
final public class ScraperException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ScraperException(final String message) {
		super(message);
	}

	public ScraperException(final String message, final Throwable e) {
		super(message, e);
	}
}
