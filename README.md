Hive UDF (Lucene Search Spell Wrapper)
========
This project contains Apache Hive User-Defined Wrapper Functions for the Apache Lucene Search Spell API. 

This projects provides to main functions
+distance - which calculates the distance between to strings based on selected algorithm (e.g Levenstein, Jaro Winkler, NGramDistance, etc.).
+spell - based on a text based dictionary.



### Hive configuration

First you must build the JAR.

	mvn package
	
	
Start the Hive CLI and add the yax-hive-udf-1.0-SNAPSHOT.jar to the Hive class path.

	hive -hiveconf hive.root.logger=DEBUG,console

	ADD JAR /home/dwh/projects/hive-udf/target/yax-hive-udf-1.0-SNAPSHOT-jar-with-dependencies.jar;
	CREATE TEMPORARY FUNCTION distance as 'ch.yax.hive.udf.text.Distance';
	CREATE TEMPORARY FUNCTION suggestion as 'ch.yax.hive.udf.text.Suggestion';
	
	
create a table dummy and a file dual.txt with value ‘X’. The load the file into the table.

	CREATE TABLE DUAL (text STRING);
	
	LOAD DATA LOCAL INPATH '/home/dwh/dual.txt' OVERWRITE INTO TABLE DUAL;

	
	
You can now execute the query to calculate the Levenshtein distance between two strings.

	SELECT distance("L", "my text", "me text") FROM DUAL;
	
Or for the Jaro–Winkler distance 

	SELECT distance("J", "my text", "me text") FROM DUAL;

	
Or the suggestions function which returns the best match for "football" in the file "/tmp/sports.txt" based on the Levenshtein distance.

	ADD FILE /tmp/sports.txt
	
	SELECT distance("L", "football", "./tmp/places.txt") FROM DUAL;

	

##### findPlace

tbd

##### findPlaceLevensthein
tbd

	
### Initialize Eclipse
To initialize eclipse settings run the following maven command.

	mvn eclipse:eclipse