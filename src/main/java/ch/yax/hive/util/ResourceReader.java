package ch.yax.hive.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class ResourceReader {

	private String resource = null;
	private List<String> entries = null;

	public ResourceReader(String resource) throws IOException {
		this.resource = resource;
		this.entries = init();
	}

	public List<String> getEntries() {
		return entries;
	}

	private List<String> init() throws IOException {
		InputStream in = getClass().getResourceAsStream(resource);
		InputStreamReader is = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(is);

		List<String> entries = new LinkedList<String>();

		while (true) {
			String read = br.readLine();

			if (read == null) {
				break;
			} else {
				String value = read.trim().toLowerCase();
				if (!value.startsWith("#")) {
					entries.add(read.trim().toLowerCase());
				}
			}
		}

		return entries;
	}
}
