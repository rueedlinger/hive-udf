package ch.yax.hive.udf.text;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;

public class Spell extends GenericUDF {

	@Override
	public Object evaluate(DeferredObject[] args) throws HiveException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDisplayString(String[] args) {
		return "spell takes 3 arguments: string, string, string";
	}

	@Override
	public ObjectInspector initialize(ObjectInspector[] arg0)
			throws UDFArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

}
