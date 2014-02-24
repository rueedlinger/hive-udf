package ch.yax.hive.udf.util;

import junit.framework.Assert;

import org.junit.Test;

import ch.yax.hive.udf.util.text.TextCleansing;

public class TestTextCleansing {

	private static final String EXPECTED_SIMPLE_TEXT = "hello hello";
	private TextCleansing cleansing = new TextCleansing();

	@Test
	public void testRemoveTab() {
		String text = cleansing.cleanText("hello\thello");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

		text = cleansing.cleanText("hello \t hello");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);
	}

	@Test
	public void testNewLine() {
		String text = cleansing.cleanText("hello\nhello");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

		text = cleansing.cleanText("hello \n hello");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);
	}

	@Test
	public void testLineFeed() {
		String text = cleansing.cleanText("hello\rhello");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

		text = cleansing.cleanText("hello \r hello");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);
	}

	@Test
	public void testLineFeedAndNewLine() {
		String text = cleansing.cleanText("hello\n\rhello");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

		text = cleansing.cleanText("hello \n \r hello");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);
	}

	@Test
	public void testUnderscore() {
		String text = cleansing.cleanText("hello_hello_");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

		text = cleansing.cleanText("hello _ hello_");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);
	}

	@Test
	public void testSpace() {
		String text = cleansing.cleanText("hello hello ");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

		text = cleansing.cleanText("hello  hello ");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

	}

	@Test
	public void testSlash() {
		String text = cleansing.cleanText("hello/hello ");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

		text = cleansing.cleanText("hello / hello");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

	}

	@Test
	public void testBackslash() {
		String text = cleansing.cleanText("hello\\hello ");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

		text = cleansing.cleanText("hello \\ hello");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

	}

	@Test
	public void testComma() {
		String text = cleansing.cleanText("hello,hello ");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

		text = cleansing.cleanText("hello, hello");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

	}

	@Test
	public void testHash() {
		String text = cleansing.cleanText("hello#hello ");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

		text = cleansing.cleanText("hello #hello ");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

		text = cleansing.cleanText("hello # hello ");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);
	}

	@Test
	public void testDot() {
		String text = cleansing.cleanText("hello.hello ");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

		text = cleansing.cleanText("hello. hello ");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

	}

	@Test
	public void testSemicolon() {
		String text = cleansing.cleanText("hello;hello ");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

		text = cleansing.cleanText("hello; hello ");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

	}

	@Test
	public void testColon() {
		String text = cleansing.cleanText("hello:hello ");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

		text = cleansing.cleanText("hello: hello ");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);
	}

	@Test
	public void testQuestionMark() {
		String text = cleansing.cleanText("hello?hello?");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

		text = cleansing.cleanText("hello? hello?");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);
	}

	@Test
	public void testExclamationMark() {
		String text = cleansing.cleanText("hello!hello!");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

		text = cleansing.cleanText("hello! hello!");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);
	}

	@Test
	public void testHyphen() {
		String text = cleansing.cleanText("hello-hello!");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

		text = cleansing.cleanText("hello- hello-");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);
	}

	@Test
	public void testPlusSign() {
		String text = cleansing.cleanText("hello+hello!");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

		text = cleansing.cleanText("hello+ hello+");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);
	}

	@Test
	public void testBracktes() {
		String text = cleansing.cleanText("hello{hello}");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

		text = cleansing.cleanText("hello {hello}");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

		text = cleansing.cleanText("hello(hello)");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

		text = cleansing.cleanText("hello (hello)");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

		text = cleansing.cleanText("hello[hello]");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

		text = cleansing.cleanText("hello [hello]");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

	}

	@Test
	public void testMixedSpaceAndTabs() {
		String text = cleansing.cleanText("hello \t hello ");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

	}

	@Test
	public void testMixed() {
		String text = cleansing.cleanText(" #hello:\n \t hello. ");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

		text = cleansing.cleanText(" __#hello:\n \t hello.;");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

	}

	@Test
	public void testUrl() {
		String text = cleansing
				.cleanText("hello (http://www.foo.com/test?test=1&query=ab) http://www.foo.ch/mypdf.pdf hello ");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

		text = cleansing
				.cleanText("hello [http://www.foo.com/test?test=1&query=ab] http://www.foo.ch/mypdf.pdf hello ");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

	}

}
