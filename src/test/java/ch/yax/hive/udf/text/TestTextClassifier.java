package ch.yax.hive.udf.text;

import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.junit.Assert;
import org.junit.Test;

public class TestTextClassifier {

	@Test
	public void testTextClassifier() throws HiveException {
		TextClassifier classifier = new TextClassifier();
		String cat = classifier.evaluate("test data", "/trainingsdata.txt");
		Assert.assertNotNull(cat);
	}
}
