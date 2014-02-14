package ch.yax.hive.udf.text;

import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.FloatObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.IntObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.StringObjectInspector;
import org.apache.lucene.search.spell.StringDistance;

import ch.yax.hive.udf.util.MemoryDictionary;

public class Suggestion extends GenericUDF {

	private static final String UNKNOW = "UNKNOW";
	private final int ARGS_LENGHT = 5;

	private StringObjectInspector soiStrategy;
	private StringObjectInspector soiTargetString;
	private StringObjectInspector soiDictonaryFilePath;
	private FloatObjectInspector soiMinThreshold;
	private IntObjectInspector soiMinTokenLenght;

	private Map<String, StringDistance> strategies = new HashMap<String, StringDistance>();

	@Override
	public Object evaluate(DeferredObject[] args) throws HiveException {

		if (args == null || args.length != ARGS_LENGHT) {
			throw new HiveException(
					"DeferredObject args null or not correct lenght");
		}

		String stratgey = soiStrategy.getPrimitiveJavaObject(args[0]);
		String target = soiTargetString.getPrimitiveJavaObject(args[1]);
		String file = soiDictonaryFilePath.getPrimitiveJavaObject(args[2]);
		float minThreashold = soiMinThreshold.get(args[3]);
		int minTokenLength = soiMinTokenLenght.get(args[4]);

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

		float bestMatch = minThreashold;
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

	@Override
	public String getDisplayString(String[] args) {
		StringBuilder buffer = new StringBuilder();
		buffer.append("suggestions UDF: ");
		for (String val : args) {
			buffer.append(val + ",");
		}
		return buffer.toString();
	}

	@Override
	public ObjectInspector initialize(ObjectInspector[] args)
			throws UDFArgumentException {

		if (args == null || args.length != ARGS_LENGHT) {
			throw new UDFArgumentException(
					"missing argumenst (strategy, target, other, threashold, tokenlength)");
		}

		soiStrategy = (StringObjectInspector) args[0];
		soiTargetString = (StringObjectInspector) args[1];
		soiDictonaryFilePath = (StringObjectInspector) args[2];
		soiMinThreshold = (FloatObjectInspector) args[3];
		soiMinTokenLenght = (IntObjectInspector) args[4];

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
