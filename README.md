Hive User-Defined Functions (UDFs)
========
This project contains Apache Hive User-Defined Functions.
+ findPlace(text) - UDF which finds places (municipals) in Switzerland from a given text. At the moment there are only municipals supported from Switzerland.


##### findPlace

	

### Hive configuration

First you must build the JAR.

	mvn package
	
	
Start the Hive CLI and add the yax-hive-udf-1.0-SNAPSHOT.jar to the Hive class path.

	ADD JAR yax-hive-udf-1.0-SNAPSHOT.jar;

	
	
### Initialize Eclipse
To initialize eclipse settings run the following maven command.

	mvn eclipse:eclipse