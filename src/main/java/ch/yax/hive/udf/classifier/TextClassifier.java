package ch.yax.hive.udf.classifier;

import java.util.List;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.hive.ql.metadata.HiveException;

import ch.yax.hive.udf.util.data.ContentHelper;
import ch.yax.hive.udf.util.data.MemoryContent;

public class TextClassifier extends UDF {

	public String evaluate(String text, String file) throws HiveException {

		ContentHelper content = new ContentHelper(new MemoryContent(file), ";");

		List<String> lines = content.getEntries();

		return "";
	}
}
