package br.com.adaptworks.scraper.tag;

import java.util.HashMap;
import java.util.Map;

import br.com.adaptworks.scraper.element.Element;

public class BangTagElement implements Element {

	public Map<String, String> getAttributes() {
		return new HashMap<String, String>();
	}

	public String getContent() {
		return "";
	}

	public String getName() {
		return "";
	}

}
