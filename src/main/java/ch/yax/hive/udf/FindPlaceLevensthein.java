package ch.yax.hive.udf;

import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

import ch.yax.hive.util.Levenshtein;
import ch.yax.hive.util.ResourceReader;

public class FindPlaceLevensthein extends UDF {

	private final static String RESOURCE = ".places.txt";
	private final static Text DEFAULT_COUNTRY = new Text("CH");
	private final static Text NOT_FOUND = new Text("UNKNOWN");
	private Map<String, ResourceReader> readers = new HashMap<String, ResourceReader>();

	private final static IntWritable DEFAULT_MIN_LENGTH = new IntWritable(4);

	public FindPlaceLevensthein() {

	}

	public Text evaluate(Text text, IntWritable threshold)
			throws RuntimeException {
		return evaluate(DEFAULT_COUNTRY, text, threshold, DEFAULT_MIN_LENGTH);
	}

	public Text evaluate(Text country, Text text, IntWritable threshold,
			IntWritable minLength) throws RuntimeException {

		checkParams(country, threshold, minLength);

		ResourceReader reader = getReader(country);

		if (text == null) {
			return NOT_FOUND;
		}

		int bestDistance = threshold.get() + 1;
		Text found = NOT_FOUND;

		// try levenshtein
		String tokens[] = cleanText(text).split(" ");
		Levenshtein levenshtein = new Levenshtein();
		for (String value : reader.getEntries()) {
			for (int i = 0; i < tokens.length; i++) {

				// ignore tokens which are smaller than min length
				if (tokens[i].length() < minLength.get()) {
					continue;
				}

				int distance = levenshtein.distance(value, tokens[i]);
				if (distance < bestDistance) {
					if (distance <= threshold.get()) {
						bestDistance = distance;
						found = new Text(value);
					}

					if (bestDistance == 0) {
						return found;
					}
				}
			}
		}

		return found;

	}

	private ResourceReader getReader(Text country) {
		// lazy initialization
		String countryTrim = country.toString().trim().toLowerCase();
		ResourceReader reader = readers.get(countryTrim);
		if (reader == null) {
			reader = new ResourceReader("/" + countryTrim + RESOURCE);
			readers.put(countryTrim, reader);
		}
		return reader;
	}

	private void checkParams(Text country, IntWritable threshold,
			IntWritable minLength) {
		if (country == null || country.toString().length() == 0) {
			throw new RuntimeException("country is not set");
		}

		if (threshold == null) {
			throw new RuntimeException("threshold is not set");
		}

		if (minLength == null) {
			throw new RuntimeException("minLength is not set");
		}

	}

	private String cleanText(Text text) {
		return text.toString().trim().replaceAll(",", "").replace(".", "")
				.replace(";", "").replace("'", " ");
	}
}
