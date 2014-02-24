package ch.yax.hive.udf.number;

import org.junit.Assert;
import org.junit.Test;

public class TestAutoIncrement {

	@Test
	public void testIncrement() {
		AutoIncrement autoIncrement = new AutoIncrement();

		for (long l = 1; l < 1000; l++) {
			Assert.assertEquals(l, autoIncrement.evaluate());
		}

	}

}
