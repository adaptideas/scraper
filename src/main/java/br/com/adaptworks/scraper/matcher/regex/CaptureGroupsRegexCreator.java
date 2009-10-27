package br.com.adaptworks.scraper.matcher.regex;

import java.util.regex.Pattern;

/**
 * @author jonasabreu
 * 
 */
final public class CaptureGroupsRegexCreator implements RegexCreator {

    public boolean accepts(final String token) {
        return Pattern.compile("\\$\\{.*?\\}").matcher(token).find();
    }

    public String regexFor(final String token) {
        return "\\Q" + token.replaceAll("\\$\\{.*?\\}", "\\\\E(.*?)\\\\Q") + "\\E";
    }

}
