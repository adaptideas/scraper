package br.com.adaptworks.scraper.cleaner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import br.com.adaptworks.scraper.tag.CloseTag;
import br.com.adaptworks.scraper.tag.OpenTag;
import br.com.adaptworks.scraper.tag.Tag;

/**
 * @author jonasabreu
 * 
 */
@SuppressWarnings("unchecked")
final public class IrrelevantTagCleanerTest {

    private TagCleaner cleaner;

    @Before
    public void setup() {
        List<Tag> list = new ArrayList<Tag>();
        list.add(new OpenTag("tr", "", Collections.EMPTY_MAP));
        cleaner = new IrrelevantTagCleaner(list);
    }

    @Test
    public void testThatRemovesTagNotPresentOnRelevantTags() {
        Assert.assertTrue(cleaner.shouldClean(new OpenTag("bla", "", Collections.EMPTY_MAP)));
    }

    @Test
    public void testThatRemovesTagOfDifferentType() {
        Assert.assertTrue(cleaner.shouldClean(new CloseTag("bla", "", Collections.EMPTY_MAP)));
    }

    @Test
    public void testThatDoesNotRemovesTagPresentOnRelevantTags() {
        Assert.assertFalse(cleaner.shouldClean(new OpenTag("tr", "", Collections.EMPTY_MAP)));
    }
}
