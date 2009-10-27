package br.com.adaptworks.scraper.matcher.regex;

/**
 * @author jonasabreu
 * 
 */
final public class EllipsisRegexCreator implements RegexCreator {

    public boolean accepts(final String token) {
        return token.contains("...");
    }

    public String regexFor(final String token) {
        return "\\Q" + token.replaceAll("\\.\\.\\.", "\\\\E.*?\\\\Q") + "\\E";
    }

}
