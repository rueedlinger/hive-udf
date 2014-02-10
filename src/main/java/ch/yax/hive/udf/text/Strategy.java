package ch.yax.hive.udf.text;

import org.apache.lucene.search.spell.JaroWinklerDistance;
import org.apache.lucene.search.spell.LevensteinDistance;
import org.apache.lucene.search.spell.NGramDistance;
import org.apache.lucene.search.spell.StringDistance;

public enum Strategy {

	LEVENSTEIN("L", new LevensteinDistance()), JAROWINKLER("J",
			new JaroWinklerDistance()), BIGRAM("N2", new NGramDistance(2));

	private String name;
	private StringDistance strategy;

	private Strategy(String name, StringDistance strategy) {
		this.name = name;
		this.strategy = strategy;
	}

	public String getName() {
		return name;
	}

	public StringDistance getStrategy() {
		return strategy;
	}

}
