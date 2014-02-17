package ch.yax.hive.udf.util;

public class TextCleansing {

	public String cleanTesxt(String text) {

		text = text.replaceAll(";", " ");
		text = text.replaceAll(":", " ");
		text = text.replaceAll(",", " ");
		text = text.replaceAll("\\.", " ");
		text = text.replaceAll("\\\\", " ");
		text = text.replaceAll("/", " ");
		text = text.replaceAll("#", " ");
		text = text.replaceAll("_", " ");
		text = text.replaceAll("-", " ");
		text = text.replaceAll("\\s+", " ");

		return text.trim();
	}
}
