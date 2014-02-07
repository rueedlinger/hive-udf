package ch.yax.hive.udf;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ch.yax.hive.util.StringFinder;

public class TestStringFinder {

	private final static String NOTFOUND = "UNKNOWN";
	private StringFinder finder = new StringFinder(NOTFOUND);
	private List<String> values = new ArrayList<String>();

	@Before
	public void setUp() {
		values.add("My First Entry");
		values.add("Second Value ");
		values.add(" 3th value ");
	}

	@Test
	public void testFindOtherDefaultNotFound() {
		Assert.assertNull(new StringFinder(null).findFirstMatch("NOT FOUND",
				values));
	}

	@Test
	public void testFindWithNullAndEmptyList() {
		Assert.assertEquals(NOTFOUND,
				finder.findFirstMatch(null, new ArrayList<String>()));
	}

	@Test
	public void testFindWithNullAndNullList() {
		Assert.assertEquals(NOTFOUND, finder.findFirstMatch(null, null));
	}

	@Test
	public void testFindWIthNull() {
		Assert.assertEquals(NOTFOUND, finder.findFirstMatch(null, values));
	}

	@Test
	public void testNotFound() {
		Assert.assertEquals(NOTFOUND,
				finder.findFirstMatch("NOT FOUND", values));
	}

	@Test
	public void testFound() {
		String found = "My First Entry";
		Assert.assertEquals(found, finder.findFirstMatch(found, values));
	}

	@Test
	public void testFoundUpperCase() {
		String found = "My First Entry";
		Assert.assertEquals(found,
				finder.findFirstMatch(found.toUpperCase(), values));
	}

	@Test
	public void testFoundLowerCase() {
		String found = "My First Entry";
		Assert.assertEquals(found,
				finder.findFirstMatch(found.toLowerCase(), values));
	}

	@Test
	public void testFoundWithSpaces() {
		String found = "My First Entry";
		Assert.assertEquals(found,
				finder.findFirstMatch(" " + found.toUpperCase() + " ", values));
	}

	@Test
	public void testFoundSecondValue() {
		String found = "Second Value";
		Assert.assertEquals(found, finder.findFirstMatch(found, values));
	}

	@Test
	public void testFoundLastValue() {
		String found = "3th value";
		Assert.assertEquals(found, finder.findFirstMatch(found, values));
	}

	@Test
	public void testFoundInText() {
		String text = "this MY 3tH value is that";
		String found = "3th value";
		Assert.assertEquals(found, finder.findFirstMatch(text, values));
	}
}
