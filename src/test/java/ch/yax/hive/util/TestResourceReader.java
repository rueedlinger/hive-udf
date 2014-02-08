package ch.yax.hive.util;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class TestResourceReader {

	private String resource = "/ch.places.txt";

	@Test
	public void testInitRead() {
		new ResourceReader(resource);
	}

	@Test
	public void testRead() {
		ResourceReader reader = new ResourceReader(resource);
		List<String> entries = reader.getEntries();
		Assert.assertNotNull(entries);

		System.out.println(entries);

		Assert.assertEquals(5, entries.size());

		Assert.assertArrayEquals(entries.toArray(), new String[] { "Basel",
				"Bern", "Zürich", "Genf", "Lausanne" });

	}

}
