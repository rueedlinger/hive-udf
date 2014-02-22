package ch.yax.hive.udf.text;

import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.junit.Assert;
import org.junit.Test;

public class TestTextClassifier {

	@Test
	public void testTextClassifierSimple() throws HiveException {
		TextClassifier classifier = new TextClassifier();
		String cat = classifier.evaluate("test data",
				"/trainings_data_simple.csv");
		Assert.assertNotNull(cat);
	}

	@Test
	public void testTextClassifier() throws HiveException {
		TextClassifier classifier = new TextClassifier();
		String cat = classifier.evaluate("stau A1 zwischen bern und basel",
				"/trainings_data.csv");

		Assert.assertNotNull(cat);
	}
}
