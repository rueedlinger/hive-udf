package ch.yax.hive.util;

import org.junit.Assert;
import org.junit.Test;

public class TestLevenshtein {

	private Levenshtein levenshtein = new Levenshtein();

	@Test
	public void testEqual() {
		Assert.assertEquals(0, levenshtein.distance("", ""));
		Assert.assertEquals(0, levenshtein.distance("A", "A"));
		Assert.assertEquals(0, levenshtein.distance("AA", "AA"));
		Assert.assertEquals(0, levenshtein.distance("ab", "AB"));
		Assert.assertEquals(0, levenshtein.distance("AB", "ab"));
		Assert.assertEquals(0, levenshtein.distance("aB", "Ab"));
	}

	@Test
	public void testDistance_1() {
		Assert.assertEquals(1, levenshtein.distance("AA", "A"));
		Assert.assertEquals(1, levenshtein.distance("A", "AA"));
		Assert.assertEquals(1, levenshtein.distance("AB", "AA"));
		Assert.assertEquals(1, levenshtein.distance("AA", "BA"));
	}

	@Test
	public void testDistance_2() {
		Assert.assertEquals(2, levenshtein.distance("AA", ""));
		Assert.assertEquals(2, levenshtein.distance("", "AA"));
		Assert.assertEquals(2, levenshtein.distance("AB", "BA"));
		Assert.assertEquals(2, levenshtein.distance("BA", "AB"));
	}

}
