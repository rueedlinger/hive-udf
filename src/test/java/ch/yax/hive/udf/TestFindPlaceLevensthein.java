package ch.yax.hive.udf;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.junit.Assert;
import org.junit.Test;

public class TestFindPlaceLevensthein {

	private FindPlaceLevensthein finder = new FindPlaceLevensthein();

	private IntWritable threshold = new IntWritable(0);

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
			finder.evaluate(null, new Text("dd"), threshold);
			Assert.fail("Exception not thorwn");
		} catch (RuntimeException ex) {

		}
	}

	@Test
	public void testFindPlaceWithCountryNotSupported() {
		try {
			finder.evaluate(new Text("IT"), new Text("text"), threshold);
			Assert.fail("Exception not thorwn");

		} catch (RuntimeException ex) {

		}
	}

	@Test
	public void testFindPlaceWithCountrySupported() {

		Text found = finder.evaluate(new Text("CH"), new Text("text"),
				threshold);
		Assert.assertEquals("UNKNOWN", found.toString());

	}

	@Test
	public void testFindPlaceWithCountrySupportedAndNullValue() {

		Text found = finder.evaluate(new Text("CH"), null, threshold);
		Assert.assertEquals("UNKNOWN", found.toString());
	}

	@Test
	public void testFindPlaceWithCountryLowerCaseSupportedAndNullValue() {

		Text found = finder.evaluate(new Text("CH".toLowerCase()), null,
				threshold);
		Assert.assertEquals("UNKNOWN", found.toString());
	}

	@Test
	public void testFindPlaceWithCountryLowerCaseSupportedAndEmptyText() {

		Text found = finder.evaluate(new Text("CH".toLowerCase()),
				new Text(""), threshold);
		Assert.assertEquals("UNKNOWN", found.toString());
	}

	@Test
	public void testFindPlaceWithCountryWithPlace() {

		Text found = finder.evaluate(new Text("CH".toLowerCase()), new Text(
				"i like Bern city"), threshold);
		Assert.assertEquals("Bern", found.toString());
	}

	@Test
	public void testFindPlaceWithDefaultCountry() {

		Text found = finder.evaluate(new Text("i like zürich city"), threshold);
		Assert.assertEquals("Zürich", found.toString());
	}

	@Test
	public void testFindPlaceWithDefaultCountryAndThreshold() {

		Text found = finder.evaluate(new Text("i like zürich city"),
				new IntWritable());
		Assert.assertEquals("Zürich", found.toString());
	}

	@Test
	public void testFindPlaceWithDefaultCountryThreshold_1() {

		Text found = finder.evaluate(new Text("i like zürich city"),
				new IntWritable(1));
		Assert.assertEquals("Zürich", found.toString());

		found = finder.evaluate(new Text("i like zrich city"), new IntWritable(
				1));
		Assert.assertEquals("Zürich", found.toString());

		found = finder.evaluate(new Text("i like zürich's city"),
				new IntWritable(1));
		Assert.assertEquals("Zürich", found.toString());

	}

	@Test
	public void testFindPlaceWithDefaultCountryThreshold_2() {

		Text found = finder.evaluate(new Text("i like zich city"),
				new IntWritable(2));
		Assert.assertEquals("Zürich", found.toString());

		found = finder.evaluate(new Text("i like zuerich city"),
				new IntWritable(2));
		Assert.assertEquals("Zürich", found.toString());

		found = finder.evaluate(new Text("i like zürich's city"),
				new IntWritable(2));
		Assert.assertEquals("Zürich", found.toString());
	}

}
