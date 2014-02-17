package ch.yax.hive.udf.text;

import junit.framework.Assert;

import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.junit.Test;

public class TestClean {

	@Test
	public void testClean() throws HiveException {
		Clean clean = new Clean();
		Assert.assertEquals("text text", clean.evaluate(" text#text "));
	}

}
