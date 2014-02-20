package ch.yax.hive.udf.util.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class ResourceReader {

	private String resource = null;
	private List<String> entries = null;

	public ResourceReader(String resource) {
		this.resource = resource;
		try {
			this.entries = init();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public List<String> getEntries() {
		return entries;
	}

	private List<String> init() throws IOException {
		InputStream in = getClass().getResourceAsStream(resource);
		InputStreamReader is = new InputStreamReader(in, "UTF-8");
		BufferedReader br = new BufferedReader(is);

		List<String> entries = new LinkedList<String>();

		while (true) {
			String read = br.readLine();

			if (read == null) {
				break;
			} else {

				String value = read.trim();

				if (!value.startsWith("#") && value.length() > 0) {
					entries.add(value);
				}
			}
		}

		return entries;
	}

}
