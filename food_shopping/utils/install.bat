call mvn install:install-file -Dfile=./external-jars/food_shopping/MetratecReaderLibrary_r1-1.jar -DgroupId=org.universAAL.control.foodDevices.RFid -DartifactId=control.foodDevices.MetratecReaderLibrary -Dversion=1.1 -Dpackaging=jar

call mvn install:install-file -Dfile=./external-jars/food_shopping/RXTXcomm.jar -DgroupId=org.universAAL.control.foodDevices.RFid -DartifactId=control.foodDevices.RXTXcomm -Dversion=1.1 -Dpackaging=jar

call mvn install:install-file -Dfile=./external-jars/food_shopping/jd2xx_20602.jar -DgroupId=org.universAAL.control.foodDevices.RFid -DartifactId=control.foodDevices.jd2xx_20602 -Dversion=1.1 -Dpackaging=jar

call mvn install:install-file -Dfile=./external-jars/food_shopping/mysql-connector-java-5.1.18-bin.jar -DgroupId=org.universAAL.food.database -DartifactId=mysql.connector.java -Dversion=5.1.18 -Dpackaging=jar

mysqladmin -u root -p -h localhost CREATE food_shopping
mysql -u root -p -h localhost food_shopping < ./database/food_shopping/food_shopping.sql