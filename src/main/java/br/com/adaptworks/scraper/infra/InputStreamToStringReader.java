package br.com.adaptworks.scraper.infra;

import java.io.InputStream;
import java.util.Scanner;

/**
 * @author jonasabreu
 * 
 */
final public class InputStreamToStringReader {

    public String read(final InputStream inputStream) {
        if (inputStream == null) {
            throw new IllegalArgumentException("inputStream cannot be null");
        }
        return new Scanner(inputStream).useDelimiter("$$").next();
    }
}
