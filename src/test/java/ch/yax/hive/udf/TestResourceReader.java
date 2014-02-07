package ch.yax.hive.udf;

import java.io.IOException;
import java.util.List;

import org.junit.*;

import ch.yax.hive.util.ResourceReader;

public class TestResourceReader {

	private String resource = "/ch.places.txt";

	@Test
	public void testInitRead() throws IOException {
		new ResourceReader(resource);
	}

	@Test
	public void testRead() throws IOException {
		ResourceReader reader = new ResourceReader(resource);
		List<String> entries = reader.getEntries();
		Assert.assertNotNull(entries);
		Assert.assertEquals(5, entries.size());
		Assert.assertArrayEquals(entries.toArray(), new String[] { "basel",
				"bern", "zürich", "genf", "lausanne" });

	}

}
