package ch.yax.hive.udf.util;

import java.util.List;

import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.junit.Assert;
import org.junit.Test;

import ch.yax.hive.udf.util.data.MemoryContent;

public class TestMemoryDictionary {

	private String resourceFull = "/ch.places.txt";
	private String resourceSmall = "/ch.places.small.txt";

	@Test
	public void testGetContent_Full() throws HiveException {
		MemoryContent reader = new MemoryContent(resourceFull);
		List<String> content = reader.getEntries();

		Assert.assertNotNull(content);

		Assert.assertEquals(4096, content.size());

	}

	@Test
	public void testGetContent_Small() throws HiveException {
		MemoryContent reader = new MemoryContent(resourceSmall);
		List<String> content = reader.getEntries();

		Assert.assertNotNull(content);

		Assert.assertEquals(2363, content.size());

	}

}
