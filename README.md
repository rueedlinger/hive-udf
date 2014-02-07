Hive User-Defined Functions (UDFs)
========
This project contains Apache Hive User-Defined Functions.
+ findPlace(text) - UDF which finds places (municipals) in Switzerland from a given text. At the moment there are only municipals supported from Switzerland.



	

### Hive configuration

First you must build the JAR.

	mvn package
	
	
Start the Hive CLI and add the yax-hive-udf-1.0-SNAPSHOT.jar to the Hive class path.

	ADD JAR /home/dwh/projects/hive-udf/target/yax-hive-udf-1.0-SNAPSHOT.jar;
	CREATE TEMPORARY FUNCTION findplace as 'ch.yax.hive.udf.FindPlace';

create a table dummy and a file dual.txt with value ‘X’. The load the file into the table.

	CREATE TABLE DUAL (dummy STRING);
	
	LOAD DATA LOCAL INPATH '/home/dwh/dual.txt' OVERWRITE INTO TABLE DUAL;

You can now execute the query with our findplace User-Defined Functions (UDF).

	-- should return --> UNKNOWN";
	SELECT findplace('some text') FROM DUAL; 

	-- should return --> UNKNOWN";
	SELECT findplace('CH', 'some text') FROM DUAL;
	
	-- should return --> Bern
	SELECT findplace('Wandering through bern’s UNESCO-protected Old Town can be a magical experience') FROM DUAL;

	

##### findPlace

tbd

	
### Initialize Eclipse
To initialize eclipse settings run the following maven command.

	mvn eclipse:eclipse