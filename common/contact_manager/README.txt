
--------------------------------------------
|	Contact Manager Service	   |	
--------------------------------------------

This folder contains the implementation
of Contact Manager service. 
The implementations contains *** projects. 

1. contact.manager.pom - parent pom for all contact manager subprojects
2. contact.manager.persistence - This project is used for persistence of the data. It handles tables creation and other supporting things (when needed), handles sql queries and provide generic interface for other projects . It uses derby database.   
3. contact.manager.service - This project contains the source code about the main Contact Manager Service project (like service providers, etc.).
4. contact.manager.shell.commands - This project contains Felix shell (GoGo shell) contacts commands used for demonstration purposes (to trigger service calls, etc.).