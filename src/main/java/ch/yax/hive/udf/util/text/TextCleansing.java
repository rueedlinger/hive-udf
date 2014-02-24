package ch.yax.hive.udf.util.text;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextCleansing {

	private String urlPattern = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";

	public List<String> extractUrlsFromText(String text) {
		Pattern p = Pattern.compile(urlPattern, Pattern.CASE_INSENSITIVE);

		List<String> urls = new ArrayList<String>();
		if (text == null) {
			return urls;
		}
		String[] textTokens = text.split(" ");

		for (String token : textTokens) {

			Matcher m = p.matcher(token);

			if (m.find()) {

				urls.add(token.substring(m.start(0), m.end(0)));

			}
		}

		return urls;
	}

	public String cleanText(String text) {

		List<String> urls = extractUrlsFromText(text);

		text = removeUrlsFromText(text, urls);

		text = text.replaceAll("\\{", " ");
		text = text.replaceAll("\\}", " ");
		text = text.replaceAll("\\(", " ");
		text = text.replaceAll("\\)", " ");
		text = text.replaceAll("\\[", " ");
		text = text.replaceAll("\\]", " ");
		text = text.replaceAll("\\?", " ");
		text = text.replaceAll("\\!", " ");
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

	private String removeUrlsFromText(String text, List<String> urls) {
		for (String url : urls) {
			// escape '?' in url
			text = text.replaceAll(url.replace("?", "\\?"), "");
		}
		return text;
	}
}
