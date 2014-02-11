package ch.yax.hive.udf.util;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class TestResourceReader {

	private String resourceFull = "/ch.places.txt";
	private String resourceSmall = "/test.places.txt";

	@Test
	public void testInitRead() {

	}

	@Test
	public void testRead() {
		ResourceReader reader = new ResourceReader(resourceFull);
		List<String> entries = reader.getEntries();
		Assert.assertNotNull(entries);
		Assert.assertEquals(4096, entries.size());

	}

	@Test
	public void testReadContent() {
		ResourceReader reader = new ResourceReader(resourceSmall);
		List<String> entries = reader.getEntries();
		Assert.assertNotNull(entries);

		Assert.assertEquals(5, entries.size());

		Assert.assertEquals("Basel", entries.get(0));
		Assert.assertEquals("Bern", entries.get(1));
		Assert.assertEquals("Zürich", entries.get(2));
		Assert.assertEquals("Genf", entries.get(3));
		Assert.assertEquals("Lausanne", entries.get(4));

	}
}
