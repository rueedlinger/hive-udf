package ch.yax.hive.udf.util;

import junit.framework.Assert;

import org.junit.Test;

public class TestTextCleansing {

	private static final String EXPECTED_SIMPLE_TEXT = "hello hello";
	private TextCleansing cleansing = new TextCleansing();

	@Test
	public void testRemoveTab() {
		String text = cleansing.cleanTesxt("hello\thello");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

		text = cleansing.cleanTesxt("hello \t hello");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);
	}

	@Test
	public void testNewLine() {
		String text = cleansing.cleanTesxt("hello\nhello");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

		text = cleansing.cleanTesxt("hello \n hello");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);
	}

	@Test
	public void testLineFeed() {
		String text = cleansing.cleanTesxt("hello\rhello");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

		text = cleansing.cleanTesxt("hello \r hello");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);
	}

	@Test
	public void testLineFeedAndNewLine() {
		String text = cleansing.cleanTesxt("hello\n\rhello");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

		text = cleansing.cleanTesxt("hello \n \r hello");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);
	}

	@Test
	public void testUnderscore() {
		String text = cleansing.cleanTesxt("hello_hello_");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

		text = cleansing.cleanTesxt("hello _ hello_");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);
	}

	@Test
	public void testSpace() {
		String text = cleansing.cleanTesxt("hello hello ");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

		text = cleansing.cleanTesxt("hello  hello ");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

	}

	@Test
	public void testSlash() {
		String text = cleansing.cleanTesxt("hello/hello ");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

		text = cleansing.cleanTesxt("hello / hello");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

	}

	@Test
	public void testBackslash() {
		String text = cleansing.cleanTesxt("hello\\hello ");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

		text = cleansing.cleanTesxt("hello \\ hello");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

	}

	@Test
	public void testComma() {
		String text = cleansing.cleanTesxt("hello,hello ");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

		text = cleansing.cleanTesxt("hello, hello");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

	}

	@Test
	public void testHash() {
		String text = cleansing.cleanTesxt("hello#hello ");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

		text = cleansing.cleanTesxt("hello #hello ");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

		text = cleansing.cleanTesxt("hello # hello ");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);
	}

	@Test
	public void testDot() {
		String text = cleansing.cleanTesxt("hello.hello ");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

		text = cleansing.cleanTesxt("hello. hello ");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

	}

	@Test
	public void testSemicolon() {
		String text = cleansing.cleanTesxt("hello;hello ");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

		text = cleansing.cleanTesxt("hello; hello ");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

	}

	@Test
	public void testColon() {
		String text = cleansing.cleanTesxt("hello:hello ");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

		text = cleansing.cleanTesxt("hello: hello ");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);
	}

	@Test
	public void testQuestionMark() {
		String text = cleansing.cleanTesxt("hello?hello?");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

		text = cleansing.cleanTesxt("hello? hello?");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);
	}

	@Test
	public void testExclamationMark() {
		String text = cleansing.cleanTesxt("hello!hello!");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

		text = cleansing.cleanTesxt("hello! hello!");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);
	}

	@Test
	public void testHyphen() {
		String text = cleansing.cleanTesxt("hello-hello!");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

		text = cleansing.cleanTesxt("hello- hello-");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);
	}

	@Test
	public void testBracktes() {
		String text = cleansing.cleanTesxt("hello{hello}");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

		text = cleansing.cleanTesxt("hello {hello}");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

		text = cleansing.cleanTesxt("hello(hello)");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

		text = cleansing.cleanTesxt("hello (hello)");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

		text = cleansing.cleanTesxt("hello[hello]");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

		text = cleansing.cleanTesxt("hello [hello]");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

	}

	@Test
	public void testMixedSpaceAndTabs() {
		String text = cleansing.cleanTesxt("hello \t hello ");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

	}

	@Test
	public void testMixed() {
		String text = cleansing.cleanTesxt(" #hello:\n \t hello. ");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

		text = cleansing.cleanTesxt(" __#hello:\n \t hello.;");
		Assert.assertEquals(EXPECTED_SIMPLE_TEXT, text);

	}

}
