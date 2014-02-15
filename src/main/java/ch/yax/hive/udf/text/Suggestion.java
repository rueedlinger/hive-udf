package ch.yax.hive.udf.text;

import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.lucene.search.spell.StringDistance;

import ch.yax.hive.udf.util.MemoryDictionary;

public class Suggestion extends UDF {

	private static final int DEFAULT_TOKEN_LENGTH = 4;

	private static final double DEFAULT_THRESHOLD = 0.85f;

	private static final String UNKNOW = "UNKNOW";

	private Map<String, StringDistance> strategies = new HashMap<String, StringDistance>();

	public Suggestion() {
		// init strategies
		strategies.put(Strategy.LEVENSTEIN.getName(),
				Strategy.LEVENSTEIN.getStrategy());
		strategies.put(Strategy.JAROWINKLER.getName(),
				Strategy.JAROWINKLER.getStrategy());
		strategies
				.put(Strategy.BIGRAM.getName(), Strategy.BIGRAM.getStrategy());
	}

	public String evaluate(String strategy, String target, String file)
			throws HiveException {
		return evaluate(strategy, target, file, DEFAULT_THRESHOLD,
				DEFAULT_TOKEN_LENGTH);
	}

	public String evaluate(String strategy, String target, String file,
			Double minThreshold, Integer minTokenLength) throws HiveException {

		if (strategy == null || target == null || file == null
				|| minThreshold == null || minTokenLength == null) {

			StringBuilder buffer = new StringBuilder();
			buffer.append("some inputs ar null");
			buffer.append(", strategy = " + strategy);
			buffer.append(", target = " + target);
			buffer.append(", file = " + file);
			buffer.append(", minThreshold = " + minThreshold);
			buffer.append(", minTokenLength = " + minTokenLength);

			throw new HiveException(buffer.toString());
		}

		MemoryDictionary reader = new MemoryDictionary(file);

		String text = target.trim().toUpperCase().replace(".", "")
				.replace(",", "").replace("!", "").replace("?", "");

		String[] tokens = text.split("\\s");

		StringDistance distance = strategies.get(strategy.toUpperCase());

		if (distance == null) {
			throw new HiveException("distance strategy not found: " + strategy);
		}

		double bestMatch = minThreshold;
		String found = UNKNOW;

		for (String dictValue : reader.getEntries()) {

			for (String token : tokens) {

				if (token.length() < minTokenLength) {
					continue;
				}

				float match = distance.getDistance(token, dictValue);

				if (match >= bestMatch) {
					found = dictValue;

					bestMatch = match;

					if (bestMatch == 1.0) {
						break;
					}

				}

			}
		}

		return found;

	}

}
