package ch.yax.hive.udf.text;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.junit.Assert;
import org.junit.Test;

public class TestSuggestion {

	@Test
	public void testInit() throws UDFArgumentException {
		new Suggestion();
	}

	@Test
	public void testEvaluateWithNullValues() throws HiveException {
		Suggestion suggestion = new Suggestion();

		try {
			suggestion.evaluate(null, null, null, null, null);
			Assert.fail();
		} catch (HiveException ex) {

		}
	}

	@Test
	public void testEvaluateEmpty() throws HiveException {
		Suggestion suggestion = new Suggestion();

		try {
			suggestion.evaluate("", "", "", 0f, 0);
			Assert.fail();
		} catch (HiveException ex) {

		}
	}

	@Test
	public void testEvaluateWith_LEVENSTEIN() throws HiveException {
		Assert.assertEquals("BERN",
				testStrategy(Strategy.LEVENSTEIN, "i love the city of Bern"));

		Assert.assertEquals("BERN",
				testStrategy(Strategy.LEVENSTEIN, "i love the city of bärn"));

		Assert.assertEquals("ZÜRICH",
				testStrategy(Strategy.LEVENSTEIN, "i love the city of zurich"));

	}

	@Test
	public void testEvaluateWith_JAROWINKLER() throws HiveException {
		Assert.assertEquals("BERN",
				testStrategy(Strategy.JAROWINKLER, "i love the city of berns"));

		Assert.assertEquals("BERN",
				testStrategy(Strategy.JAROWINKLER, "i love the city of bern"));

		Assert.assertEquals("ZÜRICH",
				testStrategy(Strategy.JAROWINKLER, "i love the city of zurich"));

		Assert.assertEquals("GENÈVE",
				testStrategy(Strategy.JAROWINKLER, "i like the city of genev"));
	}

	@Test
	public void testEvaluateWith_BIGRAM() throws HiveException {
		Assert.assertEquals("BERN",
				testStrategy(Strategy.BIGRAM, "i love the city of bern"));
	}

	private String testStrategy(Strategy distanceStrategy, String text)
			throws UDFArgumentException, HiveException {
		Suggestion suggestion = new Suggestion();

		String result = suggestion.evaluate(distanceStrategy.getName(), text,
				"/ch.places.small.txt", 0.7f, 4);
		Assert.assertNotNull(result);
		return result;

	}

}
