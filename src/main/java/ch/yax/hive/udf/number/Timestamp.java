package ch.yax.hive.udf.number;

import java.util.Calendar;

public class Timestamp {
	public long evaluate() {

		Calendar c = Calendar.getInstance();

		return c.getTimeInMillis();
	}
}
