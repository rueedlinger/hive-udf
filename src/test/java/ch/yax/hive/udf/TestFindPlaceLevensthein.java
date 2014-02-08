package ch.yax.hive.udf;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.junit.Assert;
import org.junit.Test;

public class TestFindPlaceLevensthein {

	private FindPlaceLevensthein finder = new FindPlaceLevensthein();

	private IntWritable threshold = new IntWritable(0);
	private IntWritable minLenght = new IntWritable(0);

	@Test
	public void testFindPlaceWithAllNull() {
		try {
			finder.evaluate(null, null, null, null);
			Assert.fail("Exception not thorwn");

		} catch (RuntimeException ex) {

		}
	}

	@Test
	public void testFindPlaceWithCountryNull() {
		try {
			finder.evaluate(null, new Text("dd"), threshold, minLenght);
			Assert.fail("Exception not thorwn");
		} catch (RuntimeException ex) {

		}
	}

	@Test
	public void testFindPlaceWithLenghtNull() {
		try {
			finder.evaluate(null, new Text("dd"), threshold, null);
			Assert.fail("Exception not thorwn");
		} catch (RuntimeException ex) {

		}
	}

	@Test
	public void testFindPlaceWithThresholdNull() {
		try {
			finder.evaluate(null, new Text("dd"), null, minLenght);
			Assert.fail("Exception not thorwn");
		} catch (RuntimeException ex) {

		}
	}

	@Test
	public void testFindPlaceWithCountryNotSupported() {
		try {
			finder.evaluate(new Text("IT"), new Text("text"), threshold,
					minLenght);
			Assert.fail("Exception not thorwn");

		} catch (RuntimeException ex) {

		}
	}

	@Test
	public void testFindPlaceWithCountrySupported() {

		Text found = finder.evaluate(new Text("CH"), new Text("text"),
				threshold, minLenght);
		Assert.assertEquals("UNKNOWN", found.toString());

	}

	@Test
	public void testFindPlaceWithCountrySupportedAndNullText() {

		Text found = finder
				.evaluate(new Text("CH"), null, threshold, minLenght);
		Assert.assertEquals("UNKNOWN", found.toString());
	}

	@Test
	public void testFindPlaceWithCountryLowerCaseSupportedAndNullText() {

		Text found = finder.evaluate(new Text("CH".toLowerCase()), null,
				threshold, minLenght);
		Assert.assertEquals("UNKNOWN", found.toString());
	}

	@Test
	public void testFindPlaceWithCountryLowerCaseSupportedAndEmptyText() {

		Text found = finder.evaluate(new Text("CH".toLowerCase()),
				new Text(""), threshold, minLenght);
		Assert.assertEquals("UNKNOWN", found.toString());
	}

	@Test
	public void testFindPlaceWithCountryWithPlace() {

		Text found = finder.evaluate(new Text("CH".toLowerCase()), new Text(
				"i like Bern city"), threshold, minLenght);
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
		Assert.assertEquals("UNKNOWN", found.toString());

		found = finder.evaluate(new Text("i like zuerich city"),
				new IntWritable(2));
		Assert.assertEquals("Zürich", found.toString());

		found = finder.evaluate(new Text("i like zürich's city"),
				new IntWritable(2));
		Assert.assertEquals("Zürich", found.toString());
	}

	@Test
	public void testFindPlaceWithDefaultCountryThreshold_1_exacttMatch() {

		Text found = finder.evaluate(new Text("jjjj Bernl Basel"),
				new IntWritable(1));
		Assert.assertEquals("Basel", found.toString());

	}

	@Test
	public void testFindPlaceWithDefaultCountryThreshold_10_exacttMatch() {

		Text found = finder.evaluate(new Text("Bern1 Ber Basel"),
				new IntWritable(10));
		Assert.assertEquals("Basel", found.toString());

	}

	@Test
	public void testFindPlaceWithThreshold_10_MinLength_1_exacttMatch() {

		Text found = finder.evaluate(new Text("CH"), new Text(
				"y Bern1 Ber Basel Gys"), new IntWritable(10), new IntWritable(
				1));
		Assert.assertEquals("Basel", found.toString());

	}

	@Test
	public void testFindPlaceWithSmallTokensWithDefaultLenght() {

		Text found = finder.evaluate(new Text(
				"xdd xddd x nddo x.... fff dddddddddddddddddddd"),
				new IntWritable(10));
		Assert.assertEquals("UNKNOWN", found.toString());

	}

}
