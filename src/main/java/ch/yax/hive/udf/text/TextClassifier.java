package ch.yax.hive.udf.text;

import java.util.Map;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.hive.ql.metadata.HiveException;

import weka.classifiers.bayes.NaiveBayesMultinomialUpdateable;
import ch.yax.hive.udf.util.data.ContentHelper;
import ch.yax.hive.udf.util.data.MemoryContent;
import ch.yax.hive.udf.util.text.SimpleTextClassifier;

public class TextClassifier extends UDF {

	private double THRESHOLD = 0.75;

	public String evaluate(String text, String file) throws HiveException {

		ContentHelper content = new ContentHelper(new MemoryContent(file), ";");
		SimpleTextClassifier classifier = new SimpleTextClassifier(content,
				new NaiveBayesMultinomialUpdateable());

		try {
			Map<String, Double> result = classifier.classifyMessage(text);

			for (String key : result.keySet()) {
				Double ds = result.get(key);

				if (ds.compareTo(THRESHOLD) > 0) {
					return key.toUpperCase();
				}

			}

			return "UNKNOWN";

		} catch (Exception e) {
			throw new HiveException(e);
		}

	}
}
