package ch.yax.hive.udf.util;

import java.util.ArrayList;
import java.util.List;

public class SimpleContent implements Content {

	@Override
	public List<String> getEntries() {

		List<String> list = new ArrayList<String>();
		list.add("BERN");
		list.add("ZÜRICH");
		list.add("GENÈVE");

		return list;
	}
}
