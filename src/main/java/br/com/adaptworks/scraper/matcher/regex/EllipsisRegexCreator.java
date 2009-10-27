package br.com.adaptworks.scraper.matcher.regex;

/**
 * @author jonasabreu
 * 
 */
final public class EllipsisRegexCreator implements RegexCreator {

    public boolean accepts(final String token) {
        return "...".equals(token);
    }

    public String regexFor(final String token) {
        return ".*?";
    }

}
