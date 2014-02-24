package ch.yax.hive.udf.text;

import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.UDFType;

import weka.classifiers.bayes.NaiveBayesMultinomialUpdateable;
import ch.yax.hive.udf.util.data.ContentHelper;
import ch.yax.hive.udf.util.data.MemoryContent;
import ch.yax.hive.udf.util.text.SimpleTextClassifier;

@UDFType(stateful = true)
public class TextClassifier extends UDF {

	private static final String UNKNOWN = "UNKNOWN";
	private double THRESHOLD = 0.75;

	private Map<String, SimpleTextClassifier> classifiers = new HashMap<String, SimpleTextClassifier>();

	public String evaluate(String text, String file) throws HiveException {

		SimpleTextClassifier classifier = getClassifier(file);

		try {
			Map<String, Double> result = classifier.classifyMessage(text);

			for (String key : result.keySet()) {
				Double ds = result.get(key);

				if (ds.compareTo(THRESHOLD) > 0) {
					return key.toUpperCase();
				}

			}

			return UNKNOWN;

		} catch (Exception e) {
			throw new HiveException(e);
		}

	}

	private SimpleTextClassifier getClassifier(String file)
			throws HiveException {
		SimpleTextClassifier classifier = null;
		if (classifiers.get(file) == null) {

			ContentHelper content = new ContentHelper(new MemoryContent(file),
					";");
			classifier = new SimpleTextClassifier(content,
					new NaiveBayesMultinomialUpdateable());

		} else {
			classifier = classifiers.get(file);
		}
		return classifier;
	}
}
