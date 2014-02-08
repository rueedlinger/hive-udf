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

		for (String value : values) {
			String findLower = find.trim().toLowerCase();
			String valueLower = value.trim().toLowerCase();

			if (findLower.contains(valueLower)) {
				return value.trim();
			}
		}

		// not found
		return notFound;
	}
}
