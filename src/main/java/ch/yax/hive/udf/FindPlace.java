package ch.yax.hive.udf;

import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

import ch.yax.hive.util.ResourceReader;
import ch.yax.hive.util.StringFinder;

public class FindPlace extends UDF {

	private final static String RESOURCE = ".places.txt";
	private Map<String, ResourceReader> readers = new HashMap<String, ResourceReader>();

	public FindPlace() {

	}

	public Text evaluate(Text country, Text text) throws RuntimeException {

		if (country == null || country.toString().length() == 0) {
			throw new RuntimeException("country is not set");
		}

		// lazy initialization
		String countryTrim = country.toString().trim().toLowerCase();
		ResourceReader reader = readers.get(countryTrim);
		if (reader == null) {
			reader = new ResourceReader("/" + countryTrim + RESOURCE);
			readers.put(countryTrim, reader);
		}

		StringFinder finder = new StringFinder();

		if (text == null) {
			String found = finder.findFirstMatch(null, reader.getEntries());
			return new Text(found);
		} else {
			String found = finder.findFirstMatch(text.toString(),
					reader.getEntries());
			return new Text(found);
		}
	}
}
