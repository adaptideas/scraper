package br.com.adaptideas.scraper.infra;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import br.com.adaptideas.scraper.exception.ScraperException;

/**
 * @author jonasabreu
 * 
 */
final public class InputStreamToStringReader {

	private final String charset;

	public InputStreamToStringReader(final String charset) {
		this.charset = charset;
	}

	public String read(final InputStream inputStream) {
		if (inputStream == null) {
			throw new IllegalArgumentException("inputStream cannot be null");
		}
		try {
			String encodedString = new Scanner(new InputStreamReader(inputStream, charset)).useDelimiter("$$").next();

			return new String(encodedString.getBytes("UTF-8"), "UTF-8").replaceAll("\\s+", " ").trim();
		} catch (UnsupportedEncodingException e) {
			throw new ScraperException("Encode not supported: " + charset, e);
		}
	}
}
