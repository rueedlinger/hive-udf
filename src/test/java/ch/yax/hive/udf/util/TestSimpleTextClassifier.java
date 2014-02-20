package ch.yax.hive.udf.util;

import junit.framework.Assert;

import org.junit.Test;

import weka.classifiers.bayes.NaiveBayesMultinomialUpdateable;
import ch.yax.hive.udf.util.classifier.SimpleTextClassifier;
import ch.yax.hive.udf.util.data.ContentHelper;
import ch.yax.hive.udf.util.data.MemoryContent;

public class TestSimpleTextClassifier {

	@Test
	public void testClassify() throws Exception {
		SimpleTextClassifier classifier = new SimpleTextClassifier(
				new ContentHelper(new MemoryContent("/trainingsdata.txt"), ";"),
				new NaiveBayesMultinomialUpdateable());

		Assert.assertNotNull(classifier
				.classifyMessage("cool is like running and java"));

		// System.out.println(classifier.classifyMessage("running and java"));

	}
}
