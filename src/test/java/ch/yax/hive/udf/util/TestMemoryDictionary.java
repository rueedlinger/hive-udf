package ch.yax.hive.udf.util;

import org.junit.Assert;
import org.junit.Test;

public class TestMemoryDictionary {

	private String resourceFull = "/ch.places.txt";
	private String resourceSmall = "/ch.places.small.txt";

	@Test
	public void testGetContent_Full() {
		MemoryDictionary reader = new MemoryDictionary(resourceFull);
		String content = reader.getContent();

		Assert.assertNotNull(content);

		Assert.assertEquals(44066, content.length());
		Assert.assertNotNull(reader.getInputStream());

	}

	@Test
	public void testGetContent_Small() {
		MemoryDictionary reader = new MemoryDictionary(resourceSmall);
		String content = reader.getContent();

		Assert.assertNotNull(content);

		Assert.assertEquals(24707, content.length());
		Assert.assertNotNull(reader.getInputStream());

	}

}
