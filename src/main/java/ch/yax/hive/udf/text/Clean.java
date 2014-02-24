package ch.yax.hive.udf.text;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.hive.ql.metadata.HiveException;

import ch.yax.hive.udf.util.text.TextCleansing;

public class Clean extends UDF {

	public String evaluate(String text) throws HiveException {

		TextCleansing cleansing = new TextCleansing();

		return cleansing.cleanText(text);

	}

}
