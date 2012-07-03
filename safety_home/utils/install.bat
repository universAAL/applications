call mvn install:install-file -Dfile=./external-jars/safety_home/askit_domoticsV5.0.jar -DgroupId=org.universAAL.control.safetyDevices -DartifactId=control.safetyDevices -Dversion=5.0 -Dpackaging=jar

call mvn install:install-file -Dfile=./external-jars/safety_home/mysql-connector-java-5.1.18-bin.jar -DgroupId=org.universAAL.safety.database -DartifactId=mysql.connector.java -Dversion=5.1.18 -Dpackaging=jar

mysqladmin -u root -p -h localhost CREATE safety_home
mysql -u root -p -h localhost safety_home < ./database/safety_security/safety_home.sql