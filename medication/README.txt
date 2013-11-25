
--------------------------------------------
|	Medication Manager Service	   |	
--------------------------------------------

This folder contains the implementation
of Medication Manager service. 
The implementations contains 8 projects. 

1. medication.manager.service: This project contains the source code about the main Medication Manager Service project (like providers, subscribers, etc.). 

2. ont.medication: This project contains the source code of the Medication Manager Service ontology (it is located under the ontologies svn folder).
   
3. medication.missing.services.simulation. This project is used for simulating missing UniversAAL services and their actions (like publishing events, service callees, service callers, publishing and receiving events)	
	 
4. medication.manager.integration.tests. This project    
	 is used for integration tests (not supported currently)
	 
5. medication.manager.pom: This project contains  the parent pom inherit by all the medication manager projects (except ont.medication project)

6. medication.manager.shell.commands: This project contains Felix shell (GoGo shell) medication commands used for demonstration purposes (to trigger events, service calls, etc.).
	 
7. medication.manager.ui: This project contains medication manager UI related source code (like dialogs, etc.).
   
8. medication.manager.ui.runner: (not supported currently).

9. medication.manager.persistence : This project is used for persistence of the data. It handles tables creation and other supporting things (when needed), handles sql queries and provide generic interface for other projects . It uses derby database.   

10. medication.manager.configuration : This project is the base module that configures the Medication Manager Service. In more detail it handles the properties configuration, sql scripts and their parsing, some util classes, etc.

11. medication.manager.event.issuer - Issue events for upcoming intakes in the predefined interval (simulation)

12. medication.manager.karaf.feature - Used to install Medication Manager Service bundles in the karaf runtime

13. medication.manager.servlet.ui - Web application used by doctors to create prescriptions for patients.

14. medication.manager.servlet.ui.acquire.medicine - Web application used to fill medicine inventories for all patients.

15. medication.manager.servlet.ui.base - Base project used by all web application.

16. medication.manager.servlet.ui.configuration - Web application used for configuration of user, devices, properties, notifications, etc.

17. medication.manager.servlet.ui.review - Web application for reviewing intakes (plan, taken and upcoming).

18. medication.manager.user.management - Used for interaction with the CHE module of the UniversAAL platform.