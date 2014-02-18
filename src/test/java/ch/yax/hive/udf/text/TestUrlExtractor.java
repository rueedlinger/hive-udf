package ch.yax.hive.udf.text;

import junit.framework.Assert;

import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.junit.Test;

public class TestUrlExtractor {

	private UrlExtractor extractor = new UrlExtractor();
	private String urlHttp = "http://www.google.com/dddd/hh";
	private String urlHttps = "https://www.google.com/dddd/hh?query=2&param=5";

	@Test
	public void testExtractUrlHttp() throws HiveException {

		Assert.assertEquals(urlHttp, extractor.evaluate("hhhh " + urlHttp));

		Assert.assertEquals(urlHttp,
				extractor.evaluate("my url " + urlHttp + " ff"));

		Assert.assertEquals(urlHttp, extractor.evaluate(urlHttp + " ff"));
	}

	@Test
	public void testExtractUrlHttps() throws HiveException {

		Assert.assertEquals(urlHttps, extractor.evaluate("hhhh " + urlHttps));

		Assert.assertEquals(urlHttps,
				extractor.evaluate("my url " + urlHttps + " ff"));

		Assert.assertEquals(urlHttps, extractor.evaluate(urlHttps + " ff"));
	}

}
