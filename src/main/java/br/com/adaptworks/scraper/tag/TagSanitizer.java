package br.com.adaptworks.scraper.tag;

/**
 * @author jonasabreu
 * 
 */
final public class TagSanitizer {

	public String sanitize(final String declaration) {
		String clean = "";
		int i = 0;
		i = nextNonWhiteChar(declaration, i);
		clean += declaration.charAt(i++);// either / or first tag name char

		if (declaration.charAt(i - 1) == '/') {
			i = nextNonWhiteChar(declaration, i);
		}

		while ((i < declaration.length()) && !isWhiteChar(declaration.charAt(i))) {
			clean += declaration.charAt(i++);
		}

		clean += ' ';

		boolean attributeOpen = false;
		while (i < declaration.length()) {
			char c = declaration.charAt(i);
			if (attributeOpen || !isWhiteChar(c)) {
				if ((c == '"') || (c == '\'')) {
					attributeOpen = !attributeOpen;
					clean += '"';
					if (!attributeOpen) {
						clean += " ";
					}
				} else {
					clean += c;
				}
			}
			i++;
		}
		return clean.endsWith(" ") ? clean.substring(0, clean.length() - 1) : clean;
	}

	private int nextNonWhiteChar(final String declaration, int i) {
		while ((i < declaration.length()) && isWhiteChar(declaration.charAt(i))) {
			i++;
		}
		return i;
	}

	private boolean isWhiteChar(final char c) {
		return (c == '\n') || (c == '\t') || (c == '\r') || (c == ' ');
	}

}
