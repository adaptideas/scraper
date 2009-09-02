package br.com.adaptworks.scraper;

import java.io.InputStream;
import java.util.List;

import org.junit.Test;

/**
 * @author jonasabreu
 * 
 */
final public class InterfaceDevelopmentTest {

    @Test
    public void testScraperInterface() {
        @SuppressWarnings("unused")
        List<Item> list = new Scraper<Item>(new Template<Item>(getInputStream(), Item.class)).parse(new Html(
                getInputStream()));
    }

    private InputStream getInputStream() {
        return null;
    }

}
