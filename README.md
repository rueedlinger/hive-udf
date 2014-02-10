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

	ADD JAR /home/dwh/projects/hive-udf/target/yax-hive-udf-1.0-SNAPSHOT.jar;
	CREATE TEMPORARY FUNCTION distance as 'ch.yax.hive.udf.Distance';
	CREATE TEMPORARY FUNCTION spell as 'ch.yax.hive.udf.Spell';
	
	
create a table dummy and a file dual.txt with value ‘X’. The load the file into the table.

	CREATE TABLE DUAL (dummy STRING);
	
	LOAD DATA LOCAL INPATH '/home/dwh/dual.txt' OVERWRITE INTO TABLE DUAL;

You can now execute the query with our spell User-Defined Functions (UDF).

	-- should return --> UNKNOWN";
	SELECT spell('some text') FROM DUAL; 

	-- should return --> Bern
	SELECT spell('Wandering through bern’s UNESCO-protected Old Town can be a magical experience') FROM DUAL;
	
	-- should return --> Zürich
	SELECT spell('i like zuerich') FROM DUAL;


	

##### findPlace

tbd

##### findPlaceLevensthein
tbd

	
### Initialize Eclipse
To initialize eclipse settings run the following maven command.

	mvn eclipse:eclipse