package ch.yax.hive.util;

import java.util.List;

public class StringFinder {

	private static String UNKNOWN = "UNKNOWN";

	private String notFound;

	public StringFinder() {
		this(UNKNOWN);
	}

	public StringFinder(String notFound) {
		this.notFound = notFound;
	}

	public String findFirstMatch(String find, List<String> values) {

		if (find == null || find.length() == 0) {
			return notFound;
		}

		if (values == null || values.size() == 0) {
			return notFound;
		}

		String findTrim = find.trim().toLowerCase();

		for (String value : values) {
			String valTrim = value.trim().toLowerCase();
			if (findTrim.contains(valTrim)) {
				return value.trim();
			}
		}

		// not found
		return notFound;
	}
}
