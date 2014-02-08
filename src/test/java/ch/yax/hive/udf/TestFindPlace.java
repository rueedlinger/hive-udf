package ch.yax.hive.udf;

import org.apache.hadoop.io.Text;
import org.junit.Assert;
import org.junit.Test;

public class TestFindPlace {

	private FindPlace finder = new FindPlace();

	@Test
	public void testFindPlaceWithAllNull() {
		try {

			finder.evaluate(null, null);
			Assert.fail("Exception not thorwn");

		} catch (RuntimeException ex) {

		}
	}

	@Test
	public void testFindPlaceWithFirstValueNull() {
		try {

			finder.evaluate(null, new Text("dd"));
			Assert.fail("Exception not thorwn");

		} catch (RuntimeException ex) {

		}
	}

	@Test
	public void testFindPlaceWithCountryNotSupported() {
		try {

			finder.evaluate(new Text("IT"), new Text("text"));
			Assert.fail("Exception not thorwn");
		} catch (RuntimeException ex) {

		}
	}

	@Test
	public void testFindPlaceWithCountrySupported() {

		Text found = finder.evaluate(new Text("CH"), new Text("text"));
		Assert.assertEquals("UNKNOWN", found.toString());
	}

	@Test
	public void testFindPlaceWithCountrySupportedAndNullValue() {

		Text found = finder.evaluate(new Text("CH"), null);
		Assert.assertEquals("UNKNOWN", found.toString());
	}

	@Test
	public void testFindPlaceWithCountryLowerCaseSupportedAndNullValue() {

		Text found = finder.evaluate(new Text("CH".toLowerCase()), null);
		Assert.assertEquals("UNKNOWN", found.toString());

	}

	@Test
	public void testFindPlaceWithCountryLowerCaseSupportedAndEmptyText() {

		Text found = finder
				.evaluate(new Text("CH".toLowerCase()), new Text(""));
		Assert.assertEquals("UNKNOWN", found.toString());
	}

	@Test
	public void testFindPlaceWithCountryWithPlace() {

		Text found = finder.evaluate(new Text("CH".toLowerCase()), new Text(
				"i like Bern city"));
		Assert.assertEquals("Bern", found.toString());
	}

	@Test
	public void testFindPlaceWithDefaultCountry() {

		Text found = finder.evaluate(new Text("i like zürich city"));
		Assert.assertEquals("Zürich", found.toString());
	}
}
