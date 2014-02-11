package ch.yax.hive.udf.util;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class TestResourceReader {

	private String resourceFull = "/ch.places.txt";
	private String resourceSmall = "/ch.places.small.txt";

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

		Assert.assertEquals(2363, entries.size());

		Assert.assertEquals("Aeugst am Albis", entries.get(0));
		Assert.assertEquals("Affoltern am Albis", entries.get(1));
		Assert.assertEquals("Bonstetten", entries.get(2));
		Assert.assertEquals("Hausen am Albis", entries.get(3));
		Assert.assertEquals("Hedingen", entries.get(4));

	}
}
