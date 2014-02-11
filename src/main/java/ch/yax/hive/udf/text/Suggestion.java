package ch.yax.hive.udf.text;

import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.StringObjectInspector;
import org.apache.lucene.search.spell.StringDistance;

import ch.yax.hive.udf.util.MemoryDictionary;

public class Suggestion extends GenericUDF {

	private static final String UNKNOW = "UNKNOW";
	private static final float MIN_THRESHOLD = 0.75f;
	private static final float MIN_TOKEN_LENGTH = 4;
	private final int ARGS_LENGHT = 3;

	private StringObjectInspector insStrategy;
	private StringObjectInspector insStringTarget;
	private StringObjectInspector insStringFile;

	private Map<String, StringDistance> strategies = new HashMap<String, StringDistance>();

	@Override
	public Object evaluate(DeferredObject[] args) throws HiveException {

		if (args == null || args.length != ARGS_LENGHT) {
			throw new HiveException(
					"DeferredObject args null or not correct lenght");
		}

		String stratgey = insStrategy.getPrimitiveJavaObject(args[0]);
		String target = insStringTarget.getPrimitiveJavaObject(args[1]);
		String file = insStringFile.getPrimitiveJavaObject(args[1]);

		if (stratgey == null || target == null || file == null) {
			throw new HiveException("DeferredObject are not null");
		}

		MemoryDictionary reader = new MemoryDictionary(file);

		String text = target.trim().toUpperCase().replace(".", "")
				.replace(",", "").replace("!", "").replace("?", "");

		String[] tokens = text.split("\\s");

		StringDistance distance = strategies.get(stratgey.toUpperCase());

		if (distance == null) {
			return new HiveException("distance startgey not found: " + stratgey);
		}

		float bestMatch = MIN_THRESHOLD;
		String found = UNKNOW;

		for (String dictValue : reader.getEntries()) {

			for (String token : tokens) {

				if (token.length() < MIN_TOKEN_LENGTH) {
					continue;
				}

				float match = distance.getDistance(token, dictValue);

				if (match >= bestMatch) {
					found = dictValue;

					bestMatch = match;

					// System.out.println(" --> " + bestMatch + " --> " +
					// found);

					if (bestMatch == 1.0) {
						break;
					}

				}

			}
		}

		return found;

	}

	@Override
	public String getDisplayString(String[] args) {
		return "spell takes 3 arguments: string, string, string";
	}

	@Override
	public ObjectInspector initialize(ObjectInspector[] args)
			throws UDFArgumentException {

		if (args == null || args.length != ARGS_LENGHT) {
			throw new UDFArgumentException(
					"missing argumenst (strategy, target, other)");
		}

		insStrategy = (StringObjectInspector) args[0];
		insStringTarget = (StringObjectInspector) args[1];
		insStringFile = (StringObjectInspector) args[2];

		// init strategies
		strategies.put(Strategy.LEVENSTEIN.getName(),
				Strategy.LEVENSTEIN.getStrategy());
		strategies.put(Strategy.JAROWINKLER.getName(),
				Strategy.JAROWINKLER.getStrategy());
		strategies
				.put(Strategy.BIGRAM.getName(), Strategy.BIGRAM.getStrategy());

		return PrimitiveObjectInspectorFactory.javaStringObjectInspector;

	}

}
