package ch.yax.hive.udf;

import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

import ch.yax.hive.util.ResourceReader;
import ch.yax.hive.util.StringFinder;

public class FindPlace extends UDF {

	private final static String RESOURCE = ".places.txt";
	private final static Text DEFAULT_COUNTRY = new Text("CH");
	private Map<String, ResourceReader> readers = new HashMap<String, ResourceReader>();

	public FindPlace() {

	}

	public Text evaluate(Text text) throws RuntimeException {
		return evaluate(DEFAULT_COUNTRY, text);
	}

	public Text evaluate(Text country, Text text) throws RuntimeException {

		checkParam(country);

		ResourceReader reader = getReader(country);

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

	private ResourceReader getReader(Text country) {
		// lazy initialization
		String countryTrim = country.toString().trim().toLowerCase();
		ResourceReader reader = readers.get(countryTrim);
		if (reader == null) {
			reader = new ResourceReader("/" + countryTrim + RESOURCE);
			readers.put(countryTrim, reader);
		}
		return reader;
	}

	private void checkParam(Text country) {
		if (country == null || country.toString().length() == 0) {
			throw new RuntimeException("country is not set");
		}
	}
}
