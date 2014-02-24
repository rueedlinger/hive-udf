package ch.yax.hive.udf.text;

import java.util.List;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.hive.ql.metadata.HiveException;

import ch.yax.hive.udf.util.text.TextCleansing;

public class UrlExtractor extends UDF {

	public String evaluate(String text) throws HiveException {

		TextCleansing textCleansing = new TextCleansing();

		List<String> urls = textCleansing.extractUrlsFromText(text);

		if (urls.size() > 0) {
			// return first match
			return urls.get(0);
		}

		// no match return null
		return null;
	}

}
