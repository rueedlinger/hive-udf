package ch.yax.hive.udf.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.apache.derby.iapi.services.io.ArrayInputStream;
import org.apache.hadoop.hive.ql.metadata.HiveException;

public class MemoryContent implements Content {

	private List<String> content = new LinkedList<String>();

	public MemoryContent(String resource) throws HiveException {

		try {

			InputStream in = getClass().getResourceAsStream(resource);
			InputStreamReader is = new InputStreamReader(in, "UTF-8");
			BufferedReader br = new BufferedReader(is);

			while (true) {
				String read = br.readLine();

				if (read == null) {
					break;
				} else {

					String value = read.trim().toUpperCase();

					if (!value.startsWith("#") && value.length() > 0) {
						content.add(value);
					}
				}
			}

			Collections.sort(content, ALPHA_ORDER);

		} catch (Exception e) {
			throw new HiveException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.yax.hive.udf.util.Dictionary#getEntries()
	 */
	@Override
	public List<String> getEntries() {
		return content;
	}

	public String getContent() {

		StringBuilder buffer = new StringBuilder();
		for (String value : content) {
			buffer.append(value);
			buffer.append("\n");
		}

		return buffer.toString();
	}

	public InputStream getInputStream() {
		return new ArrayInputStream(getContent().getBytes());
	}

	private static Comparator<String> ALPHA_ORDER = new Comparator<String>() {
		public int compare(String str1, String str2) {
			int x = String.CASE_INSENSITIVE_ORDER.compare(str1, str2);
			if (x == 0) {
				x = str1.compareTo(str2);
			}
			return x;
		}
	};

}
