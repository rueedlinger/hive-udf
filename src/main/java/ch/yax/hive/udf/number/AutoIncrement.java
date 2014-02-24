package ch.yax.hive.udf.number;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.hive.ql.udf.UDFType;

@UDFType(stateful = true)
public class AutoIncrement extends UDF {
	private long count = 0;

	public long evaluate() {
		count++;
		return count;
	}
}