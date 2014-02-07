package ch.yax.hive.udf;

import org.apache.hadoop.io.Text;
import org.junit.Assert;
import org.junit.Test;

public class TestFindPlace {

	@Test
	public void testFindPlaceWithAllNull() {
		try {
			FindPlace finder = new FindPlace();
			finder.evaluate(null, null);
			Assert.fail("Exception not thorwn");

		} catch (RuntimeException ex) {

		}
	}

	@Test
	public void testFindPlaceWithFirstValueNull() {
		try {
			FindPlace finder = new FindPlace();
			finder.evaluate(null, new Text("dd"));
			Assert.fail("Exception not thorwn");

		} catch (RuntimeException ex) {

		}
	}

	@Test
	public void testFindPlaceWithCountryNotSupported() {
		try {
			FindPlace finder = new FindPlace();
			finder.evaluate(new Text("IT"), new Text("text"));
			Assert.fail("Exception not thorwn");

		} catch (RuntimeException ex) {

		}
	}

	@Test
	public void testFindPlaceWithCountrySupported() {

		FindPlace finder = new FindPlace();
		Text found = finder.evaluate(new Text("CH"), new Text("text"));
		Assert.assertEquals("UNKNOWN", found.toString());

	}

	@Test
	public void testFindPlaceWithCountrySupportedAndNullValue() {

		FindPlace finder = new FindPlace();
		Text found = finder.evaluate(new Text("CH"), null);
		Assert.assertEquals("UNKNOWN", found.toString());
	}

	@Test
	public void testFindPlaceWithCountryLowerCaseSupportedAndNullValue() {

		FindPlace finder = new FindPlace();
		Text found = finder.evaluate(new Text("CH".toLowerCase()), null);
		Assert.assertEquals("UNKNOWN", found.toString());
	}

	@Test
	public void testFindPlaceWithCountryLowerCaseSupportedAndEmptyText() {

		FindPlace finder = new FindPlace();
		Text found = finder
				.evaluate(new Text("CH".toLowerCase()), new Text(""));
		Assert.assertEquals("UNKNOWN", found.toString());
	}

	@Test
	public void testFindPlaceWithCountryWithPlace() {

		FindPlace finder = new FindPlace();
		Text found = finder.evaluate(new Text("CH".toLowerCase()), new Text(
				"i like Bern city"));
		Assert.assertEquals("Bern", found.toString());
	}
}
