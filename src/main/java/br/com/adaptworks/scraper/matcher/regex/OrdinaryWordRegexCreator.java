package br.com.adaptworks.scraper.matcher.regex;

import java.util.regex.Pattern;

/**
 * @author jonasabreu
 * 
 */
final public class OrdinaryWordRegexCreator implements RegexCreator {

    public boolean accepts(final String token) {
        return true;
    }

    public String regexFor(final String token) {
        return Pattern.quote(token);
    }

}
