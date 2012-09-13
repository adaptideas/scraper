package br.com.adaptideas.scraper.matcher;

import java.util.regex.Pattern;

final public class ContentCleaner {

	public final Pattern captureGroupPattern = Pattern.compile("\\$\\{\\s*(.*?)\\s*\\}");

	public String clean(final String content) {
		return captureGroupPattern.matcher(content).replaceAll("\\${$1}");
	}

}
