package ch.yax.hive.udf.number;

import java.util.Calendar;

import org.apache.hadoop.hive.ql.exec.UDF;

public class Timestamp extends UDF {
	public long evaluate() {

		Calendar c = Calendar.getInstance();

		return c.getTimeInMillis();
	}
}
