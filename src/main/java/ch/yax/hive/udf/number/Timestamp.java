package ch.yax.hive.udf.number;

import java.util.Calendar;
import java.util.Date;

import org.apache.hadoop.hive.ql.exec.UDF;

public class Timestamp extends UDF {
	public long evaluate() {

		Calendar c = Calendar.getInstance();
		c.setTime(new Date());

		return c.getTimeInMillis();
	}
}
