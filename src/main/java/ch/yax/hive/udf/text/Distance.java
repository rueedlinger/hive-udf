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

public class Distance extends GenericUDF {

	private final int ARGS_LENGHT = 3;
	private Map<String, StringDistance> strategies = new HashMap<String, StringDistance>();
	private StringObjectInspector insStrategy;
	private StringObjectInspector insStringTarget;
	private StringObjectInspector insStringOther;

	@Override
	public Object evaluate(DeferredObject[] args) throws HiveException {

		if (args == null || args.length != ARGS_LENGHT) {
			throw new HiveException(
					"DeferredObject args null or not correct lenght");
		}

		String stratgey = insStrategy.getPrimitiveJavaObject(args[0]);
		String target = insStringTarget.getPrimitiveJavaObject(args[1]);
		String other = insStringOther.getPrimitiveJavaObject(args[2]);

		if (stratgey == null || target == null || other == null) {
			throw new HiveException("DeferredObject are not null");
		}

		StringDistance distance = strategies.get(stratgey.toUpperCase());

		if (distance == null) {
			throw new HiveException("Strategy " + stratgey
					+ " is not supported.");
		}

		return new Float(distance.getDistance(target, other));
	}

	@Override
	public String getDisplayString(String[] arg0) {
		return "distance takes 3 arguments: string, string, string";
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
		insStringOther = (StringObjectInspector) args[2];

		// init strategies
		strategies.put(Strategy.LEVENSTEIN.getName(),
				Strategy.LEVENSTEIN.getStrategy());
		strategies.put(Strategy.JAROWINKLER.getName(),
				Strategy.JAROWINKLER.getStrategy());
		strategies
				.put(Strategy.BIGRAM.getName(), Strategy.BIGRAM.getStrategy());

		// distance are returned as float
		return PrimitiveObjectInspectorFactory.javaFloatObjectInspector;
	}

}
