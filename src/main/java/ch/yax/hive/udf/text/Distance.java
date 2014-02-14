package ch.yax.hive.udf.text;

import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.lucene.search.spell.StringDistance;

public class Distance extends UDF {

	private Map<String, StringDistance> strategies = new HashMap<String, StringDistance>();

	public Distance() {
		strategies.put(Strategy.LEVENSTEIN.getName(),
				Strategy.LEVENSTEIN.getStrategy());
		strategies.put(Strategy.JAROWINKLER.getName(),
				Strategy.JAROWINKLER.getStrategy());
		strategies
				.put(Strategy.BIGRAM.getName(), Strategy.BIGRAM.getStrategy());

	}

	public Float evaluate(String strategy, String target, String other)
			throws HiveException {

		if (strategy == null || target == null || other == null) {

			StringBuilder buffer = new StringBuilder();
			buffer.append("some inputs ar null");
			buffer.append(", strategy = " + strategy);
			buffer.append(", target = " + target);
			buffer.append(", other = " + other);

			throw new HiveException(buffer.toString());
		}

		StringDistance distance = strategies.get(strategy.toUpperCase());

		if (distance == null) {
			throw new HiveException("Strategy " + strategy
					+ " is not supported.");
		}

		return new Float(distance.getDistance(target, other));
	}

}
