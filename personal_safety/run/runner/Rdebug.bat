java -Dfelix.config.properties=file:felix/config.ini -Dfelix.system.properties=file:felix/system.properties -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005 -jar bundles/org.apache.felix.main_1.4.1.jar
