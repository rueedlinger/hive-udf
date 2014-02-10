package ch.yax.hive.udf.text;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF.DeferredObject;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.StringObjectInspector;
import org.junit.Assert;
import org.junit.Test;

public class TestDistance {

	@Test
	public void testInitNotSize3() throws UDFArgumentException {
		Distance distance = new Distance();
		ObjectInspector[] args = new ObjectInspector[1];
		try {
			distance.initialize(args);
			Assert.fail();
		} catch (UDFArgumentException ex) {

		}
	}

	@Test
	public void testInitEmpty() throws UDFArgumentException {
		Distance distance = new Distance();
		ObjectInspector[] args = new ObjectInspector[3];
		distance.initialize(args);
	}

	@Test
	public void testInitNull() throws UDFArgumentException {
		Distance distance = new Distance();
		try {
			distance.initialize(null);
			Assert.fail();
		} catch (UDFArgumentException ex) {

		}
	}

	@Test
	public void testEvaluateNull() throws HiveException {
		Distance distance = new Distance();
		ObjectInspector[] args = new ObjectInspector[3];
		initObjInspectors(args);

		try {
			distance.initialize(null);
			Assert.fail();
		} catch (HiveException ex) {

		}
	}

	@Test
	public void testEvaluateEmpty() throws HiveException {
		Distance distance = new Distance();
		ObjectInspector[] args = new ObjectInspector[3];
		initObjInspectors(args);

		distance.initialize(args);
		DeferredObject[] dobjs = new DeferredObject[3];
		try {
			distance.evaluate(dobjs);
			Assert.fail();
		} catch (HiveException ex) {

		}
	}

	@Test
	public void testEvaluateWithNullValues() throws HiveException {
		Distance distance = new Distance();
		ObjectInspector[] args = new ObjectInspector[3];
		initObjInspectors(args);

		distance.initialize(args);
		DeferredObject[] dobjs = new DeferredObject[3];
		DeferredObject strategy = mock(DeferredObject.class);
		DeferredObject target = mock(DeferredObject.class);
		DeferredObject other = mock(DeferredObject.class);

		dobjs[0] = strategy;
		dobjs[1] = target;
		dobjs[2] = other;

		try {
			distance.evaluate(dobjs);
			Assert.fail();
		} catch (HiveException ex) {

		}

	}

	@Test
	public void testEvaluateWith_LEVENSTEIN() throws HiveException {
		testStrategy(Strategy.LEVENSTEIN);
	}

	@Test
	public void testEvaluateWith_JAROWINKLER() throws HiveException {
		testStrategy(Strategy.JAROWINKLER);
	}

	@Test
	public void testEvaluateWith_BIGRAM() throws HiveException {
		testStrategy(Strategy.BIGRAM);
	}

	private void testStrategy(Strategy distanceStrategy)
			throws UDFArgumentException, HiveException {
		Distance distance = new Distance();
		StringObjectInspector[] args = new StringObjectInspector[3];
		initObjInspectors(args);

		distance.initialize(args);
		DeferredObject[] dobjs = new DeferredObject[3];
		DeferredObject strategy = mock(DeferredObject.class);
		DeferredObject target = mock(DeferredObject.class);
		DeferredObject other = mock(DeferredObject.class);

		dobjs[0] = strategy;
		dobjs[1] = target;
		dobjs[2] = other;

		when(args[0].getPrimitiveJavaObject(any())).thenReturn(
				distanceStrategy.getName().toLowerCase());
		when(args[1].getPrimitiveJavaObject(any())).thenReturn("hallo");
		when(args[2].getPrimitiveJavaObject(any())).thenReturn("hallo");

		Object result = distance.evaluate(dobjs);
		Assert.assertNotNull(result);
		Float dist = (Float) result;
		Assert.assertEquals(1.0, dist, 0f);

	}

	private void initObjInspectors(ObjectInspector[] args) {
		args[0] = mock(StringObjectInspector.class);
		args[1] = mock(StringObjectInspector.class);
		args[2] = mock(StringObjectInspector.class);
	}

}
