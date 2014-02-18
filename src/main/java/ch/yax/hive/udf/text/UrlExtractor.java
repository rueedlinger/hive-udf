package ch.yax.hive.udf.text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.hive.ql.metadata.HiveException;

public class UrlExtractor extends UDF {

	private String urlPattern = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";

	public String evaluate(String text) throws HiveException {

		Pattern p = Pattern.compile(urlPattern, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(text);

		if (m.find()) {
			return text.substring(m.start(0), m.end(0));
		}
		return null;
	}

}
