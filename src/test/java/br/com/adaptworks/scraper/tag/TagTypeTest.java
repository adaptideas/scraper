package br.com.adaptworks.scraper.tag;

import org.junit.Assert;
import org.junit.Test;

public class TagTypeTest {

	@Test
	public void testThatItReturnsBangIfTheCharacterIsExclamationMark() {
		TagType type = TagType.fromValue('!');

		Assert.assertEquals(TagType.BANG, type);
	}

	@Test
	public void testThatItReturnsCloseIfTheCharacterIsSlash() {
		TagType type = TagType.fromValue('/');

		Assert.assertEquals(TagType.CLOSE, type);
	}

	@Test
	public void testThatItReturnsOpenIfTheCharacterIsNeitherSlashNorBang() {
		TagType type = TagType.fromValue('a');

		Assert.assertEquals(TagType.OPEN, type);
	}

}
