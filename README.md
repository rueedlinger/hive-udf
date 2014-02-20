Hive UDF's for Text Mining
========
This project contains Apache Hive User-Defined Wrapper Functions for the Apache Lucene Search Spell API. 

This projects provides to main functions
+ **distance** - which calculates the distance between to strings based on selected algorithm (e.g Levenstein, Jaro Winkler, NGramDistance, etc.).
+ **suggestion** - based on a text based dictionary.
+ **clean** - clean text from whitspaces and other characters.



### Hive configuration

First you must build the JAR.

	mvn package
	
	
Start the Hive CLI and add the yax-hive-udf-1.0-SNAPSHOT.jar to the Hive class path.

	hive

	ADD JAR /home/dwh/projects/hive-udf/target/yax-hive-udf-1.0-SNAPSHOT-jar-with-dependencies.jar;
	CREATE TEMPORARY FUNCTION distance as 'ch.yax.hive.udf.text.Distance';
	CREATE TEMPORARY FUNCTION suggestion as 'ch.yax.hive.udf.text.Suggestion';
	CREATE TEMPORARY FUNCTION clean as 'ch.yax.hive.udf.text.Clean';
	CREATE TEMPORARY FUNCTION urlextractor as 'ch.yax.hive.udf.text.UrlExtractor';
	
	
create a table dummy and a file dual.txt with value ‘X’. The load the file into the table.

	CREATE TABLE DUAL (text STRING);
	
	LOAD DATA LOCAL INPATH '/data/dual.txt' OVERWRITE INTO TABLE DUAL;

	
	
You can now execute the query to calculate the Levenshtein distance between two strings.

	SELECT distance("L", "my text", "me text") FROM DUAL;
	
Or for the Jaro–Winkler distance 

	SELECT distance("J", "my text", "me text") FROM DUAL;

	
Or the suggestions function which returns the best match for "football" in the file "/tmp/sports.txt" based on the Levenshtein distance.

	ADD FILE /data/sport.txt;
	
	SELECT suggestion("L", "i love football", "/data/sport.txt") FROM DUAL;

This query should return FOOTBALL. You can also add the threshold a value from 0.0 to 1.0 and the minimum token length.

	SELECT suggestion("L", "i love foot", "/data/sport.txt", 0.5, 4) FROM DUAL;


#### float : distance (string strategy, string target, string other)

	
**parameters:**
	
+ strategy: the algorithm which should be used for calculating the distance.  L = LEVENSTEIN, J = JAROWINKLER or N2 = BIGRAM
+ target: string to compare
+ other: string  to compare
	
**returns:** the distance between the target and other as float.

#### string : suggestion (string strategy, string target, string file)

**parameters:**
	
+ strategy: the algorithm which should be used for calculating the distance.  L = LEVENSTEIN, J = JAROWINKLER or N2 = BIGRAM
+ target: string to compare
+ file: a file with suggestions which should be returned when they matched.
	
**returns:** the string from the file in upper-case which has the best match with the target string or 'UNKNOW' when not match was found. As default minimum token length is 4 and match must be equal or better than a threshold 0.85.


#### string : suggestion (string strategy, string target, string file, float threshold, integer minTokenLength)
	
**parameters:**
+ strategy: the algorithm which should be used for calculating the distance.  L = LEVENSTEIN, J = JAROWINKLER or N2 = BIGRAM
+ target: string to compare
+ file: a file with suggestions which should be returned when they matched.
+ threshold: the minimum threshold for a match
+ minTokenLength: minimum token length
	
**returns:** the string from the file in upper-case which has the best match with the target string or 'UNKNOW' when not match was found.


#### string : clean (string text)

	
**parameters:**
	
+ text: original text
	
**returns:** cleaned text

### Text Mining

	select clean(text), suggestion("L", clean(text),"/home/dwh/ch.place.txt") from tweets;


### Initialize Eclipse
To initialize eclipse settings run the following maven command.

	mvn eclipse:eclipse