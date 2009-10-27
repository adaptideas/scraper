package br.com.adaptworks.scraper.infra;

import java.io.ByteArrayInputStream;

import junit.framework.Assert;

import org.junit.Test;

/**
 * @author jonasabreu
 * 
 */
final public class InputStreamToStringReaderTest {

    @Test
    public void testThatRemovesWhiteChars() {
        String string = new InputStreamToStringReader("UTF-8").read(new ByteArrayInputStream(
                "\ntext \n\t \r other    text\n\r".getBytes()));
        Assert.assertEquals("text other text", string);
    }

}
