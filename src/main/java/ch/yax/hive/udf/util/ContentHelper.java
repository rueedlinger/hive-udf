package ch.yax.hive.udf.util;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ContentHelper implements Content {

	private Content content;
	private String split;

	public ContentHelper(Content content, String split) {
		this.content = content;
		this.split = split;
	}

	private List<List<String>> getTokens(String split) {
		List<List<String>> values = new ArrayList<List<String>>();

		for (String line : content.getEntries()) {
			List<String> column = new LinkedList<String>();
			for (String token : line.split(split)) {

				column.add(token.trim());
			}
			values.add(column);
		}

		return values;
	}

	public List<List<String>> getTokens() {
		return getTokens(split);
	}

	public List<String> getValues(int column) {

		List<String> values = new LinkedList<String>();

		for (List<String> row : getTokens(split)) {
			values.add(row.get(column));
		}

		return values;
	}

	public Set<String> getCategories(int column) {

		Set<String> values = new LinkedHashSet<String>();

		for (List<String> row : getTokens(split)) {
			values.add(row.get(column));
		}

		return values;
	}

	@Override
	public List<String> getEntries() {

		return content.getEntries();
	}

}