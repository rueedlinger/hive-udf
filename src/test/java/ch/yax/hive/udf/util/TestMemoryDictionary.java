package ch.yax.hive.udf.util;

import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.junit.Assert;
import org.junit.Test;

public class TestMemoryDictionary {

	private String resourceFull = "/ch.places.txt";
	private String resourceSmall = "/ch.places.small.txt";

	@Test
	public void testGetContent_Full() throws HiveException {
		MemoryContent reader = new MemoryContent(resourceFull);
		String content = reader.getContent();

		Assert.assertNotNull(content);

		Assert.assertEquals(44066, content.length());
		Assert.assertNotNull(reader.getInputStream());

	}

	@Test
	public void testGetContent_Small() throws HiveException {
		MemoryContent reader = new MemoryContent(resourceSmall);
		String content = reader.getContent();

		Assert.assertNotNull(content);

		Assert.assertEquals(24707, content.length());
		Assert.assertNotNull(reader.getInputStream());

	}

}
