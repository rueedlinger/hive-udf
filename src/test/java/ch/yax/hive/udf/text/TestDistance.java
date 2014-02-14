package ch.yax.hive.udf.text;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.junit.Assert;
import org.junit.Test;

public class TestDistance {

	@Test
	public void testInit() {
		new Distance();
	}

	@Test
	public void testEvaluateWithNullValues() throws HiveException {
		Distance distance = new Distance();

		try {
			distance.evaluate(null, null, null);
			Assert.fail();
		} catch (HiveException ex) {

		}
	}

	@Test
	public void testEvaluateWithEmptyStrategy() throws HiveException {
		Distance distance = new Distance();

		try {
			distance.evaluate("", "dd", "dd");
			Assert.fail();
		} catch (HiveException ex) {

		}
	}

	@Test
	public void testEvaluateWithStrategyNotFound() throws HiveException {
		Distance distance = new Distance();

		try {
			distance.evaluate("dd", "dd", "dd");
			Assert.fail();
		} catch (HiveException ex) {

		}
	}

	@Test
	public void testEvaluateWith_LEVENSTEIN() throws HiveException {
		testStrategy(Strategy.LEVENSTEIN);
	}

	@Test
	public void testEvaluateWith_JAROWINKLER() throws HiveException {
		testStrategy(Strategy.JAROWINKLER);
	}

	@Test
	public void testEvaluateWith_BIGRAM() throws HiveException {
		testStrategy(Strategy.BIGRAM);
	}

	private void testStrategy(Strategy distanceStrategy)
			throws UDFArgumentException, HiveException {
		Distance distance = new Distance();

		Object result = distance.evaluate(distanceStrategy.getName(), "hello",
				"hello");
		Assert.assertNotNull(result);
		Float dist = (Float) result;
		Assert.assertEquals(1.0, dist, 0f);

	}

}
