-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.1.34-community


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema agenda_reminder
--

CREATE DATABASE IF NOT EXISTS agenda_reminder;
USE agenda_reminder;

--
-- Definition of table `calendar`
--

DROP TABLE IF EXISTS `calendar`;
CREATE TABLE `calendar` (
  `calendarID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL DEFAULT 'calendar',
  `owner` varchar(145) NOT NULL DEFAULT 'http://anon/clanedar#',
  PRIMARY KEY (`calendarID`),
  UNIQUE KEY `name_2` (`name`,`owner`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=latin1 COMMENT='stored calendars';

--
-- Dumping data for table `calendar`
--

/*!40000 ALTER TABLE `calendar` DISABLE KEYS */;
INSERT INTO `calendar` (`calendarID`,`name`,`owner`) VALUES 
 (6,'Athletics','urn:org.persona.aal_space:igd_test_environment#saied'),
 (62,'dokimastiko','urn:org.persona.aal_space:igd_test_environment#Neos'),
 (21,'Medical','urn:org.persona.aal_space:igd_test_environment#saied'),
 (5,'Social','urn:org.persona.aal_space:igd_test_environment#saied'),
 (17,'Temporary','urn:org.persona.aal_space:igd_test_environment#saied'),
 (50,'test','urn:org.persona.aal_space:igd_test_environment#saied'),
 (61,'Test1','http://ontology.persona.upm.es/User.owl#Userkostas'),
 (20,'Work','urn:org.persona.aal_space:igd_test_environment#saied');
/*!40000 ALTER TABLE `calendar` ENABLE KEYS */;


--
-- Definition of table `extended_event`
--

DROP TABLE IF EXISTS `extended_event`;
CREATE TABLE `extended_event` (
  `eventID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `eCategory` varchar(45) DEFAULT NULL,
  `ePlace` varchar(45) DEFAULT NULL,
  `eSpokenLang` varchar(30) DEFAULT NULL,
  `eStartTime` datetime DEFAULT NULL,
  `eDbStartTime` bigint(20) unsigned DEFAULT NULL,
  `eEndTime` datetime DEFAULT NULL,
  `eDbEndTime` bigint(20) unsigned DEFAULT NULL,
  `eDescription` varchar(100) DEFAULT NULL,
  `rMessage` varchar(100) DEFAULT NULL,
  `rTime` datetime DEFAULT NULL,
  `rDbTime` bigint(20) unsigned DEFAULT NULL,
  `rRepeatTimes` int(10) unsigned DEFAULT '0',
  `rReminderType` smallint(5) unsigned DEFAULT NULL,
  `aCountryName1` varchar(45) DEFAULT NULL,
  `aExtAddress` varchar(80) DEFAULT NULL,
  `aLocality` varchar(45) DEFAULT NULL,
  `aPostalCode` varchar(10) DEFAULT NULL,
  `aRegion` varchar(45) DEFAULT NULL,
  `aStreetName` varchar(45) DEFAULT NULL,
  `aBuilding` varchar(10) DEFAULT NULL,
  `isPersistent` tinyint(1) DEFAULT '0',
  `parentID` int(10) unsigned NOT NULL,
  `rInterval` int(10) unsigned NOT NULL DEFAULT '0',
  `isVisible` tinyint(1) unsigned NOT NULL DEFAULT '1',
  PRIMARY KEY (`eventID`),
  KEY `FK_extended_event_1` (`parentID`),
  CONSTRAINT `FK_extended_event_1` FOREIGN KEY (`parentID`) REFERENCES `calendar` (`calendarID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=233 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `extended_event`
--

/*!40000 ALTER TABLE `extended_event` DISABLE KEYS */;
INSERT INTO `extended_event` (`eventID`,`eCategory`,`ePlace`,`eSpokenLang`,`eStartTime`,`eDbStartTime`,`eEndTime`,`eDbEndTime`,`eDescription`,`rMessage`,`rTime`,`rDbTime`,`rRepeatTimes`,`rReminderType`,`aCountryName1`,`aExtAddress`,`aLocality`,`aPostalCode`,`aRegion`,`aStreetName`,`aBuilding`,`isPersistent`,`parentID`,`rInterval`,`isVisible`) VALUES 
 (2,'Sport','Pale de sport','GR','2009-02-28 16:30:11',1235831411000,'2009-02-11 16:00:00',1234360800000,'Aris game not me','Aris game starts in few minutes','2009-02-28 16:00:11',1235829611326,1,1,'Spain','Valencia','Valencia Center','12234','Valencia','Rain 43','bb',0,5,0,1),
 (3,'Social','Kamara','ES','2009-02-15 20:22:00',1234722120000,'2009-02-15 20:00:00',1234720800000,'Music Festival','Go to festival','2009-02-15 20:00:00',1234720800000,0,0,'Hellas','Kalamaria','Aigeo','32233','Faliro','Ermou 14','3',0,5,0,1),
 (10,'monitorization','Agora','GR','2009-02-28 11:34:38',1235813678000,'2009-02-28 11:34:38',1235813678000,'Monitor vital signs','Put your garment','2009-02-28 11:34:38',1235813678000,0,2,'Hellas','Neapoli','Center','41 500','Nea politia','Kiprou 21','b3',1,5,0,1),
 (21,'Sports','Gym','GR','2009-02-28 12:00:10',1235815210000,'2009-02-28 18:00:00',1235836810000,'Football game!','Do not forget your game','2009-02-28 06:00:10',1235793610070,0,2,'Hellas','Thessaly','Larissa','41500','Central Thessaly','Olymbou 12','76',0,5,0,1);
INSERT INTO `extended_event` (`eventID`,`eCategory`,`ePlace`,`eSpokenLang`,`eStartTime`,`eDbStartTime`,`eEndTime`,`eDbEndTime`,`eDescription`,`rMessage`,`rTime`,`rDbTime`,`rRepeatTimes`,`rReminderType`,`aCountryName1`,`aExtAddress`,`aLocality`,`aPostalCode`,`aRegion`,`aStreetName`,`aBuilding`,`isPersistent`,`parentID`,`rInterval`,`isVisible`) VALUES 
 (40,'Aris magic','UoM','GR','2009-03-12 18:00:00',1236873600000,'2009-03-12 18:00:00',1236873600000,'Guitar lesson','Lesson time','2009-03-12 15:30:00',1234965383662,10,2,'Hellas','Neapoli','Thessalia','41 500','Nea politia','M. Aleksandrou 45','b3',0,6,0,1),
 (41,'Aris magic','Pale de spor','GR','2009-02-27 18:00:00',1235750400000,'2009-02-27 18:00:00',1235750400000,'Swimming','Gym time','2009-03-12 15:30:00',1236864600000,10,0,'Hellas','Neapoli','Thessalia','41 500','Nea politia','Kiprou 21','b3',0,6,0,1),
 (66,'No interest','Home','EN','2009-02-27 16:30:00',1235745000000,'2009-02-27 16:30:00',1235745000000,'Birthday of Angie','Message changed!','2009-02-20 16:04:00',1235138640000,40000,1,'Hellas','Neapoli','Macedonia','41 500','Nea politia','Kiprou 21','b3',0,17,0,1),
 (67,'ARIS REE','Alexandrio Vodafone Arena','ell','2009-03-29 20:00:45',1238346045000,'2009-03-30 00:23:00',1238361780000,'Euroleague Game','Reminder!',NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,5,0,1);
INSERT INTO `extended_event` (`eventID`,`eCategory`,`ePlace`,`eSpokenLang`,`eStartTime`,`eDbStartTime`,`eEndTime`,`eDbEndTime`,`eDescription`,`rMessage`,`rTime`,`rDbTime`,`rRepeatTimes`,`rReminderType`,`aCountryName1`,`aExtAddress`,`aLocality`,`aPostalCode`,`aRegion`,`aStreetName`,`aBuilding`,`isPersistent`,`parentID`,`rInterval`,`isVisible`) VALUES 
 (68,'111',NULL,'ell','2009-03-09 21:21:00',1236626460000,'2009-03-09 22:00:00',1236628800000,NULL,'Reminder!',NULL,NULL,0,2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,5,0,1),
 (69,'Sports final','Alabama','ell','2009-09-09 23:23:00',1252527780683,'2009-09-09 23:50:00',1252529400683,'NFL Finals','Reminder!','2009-07-09 23:15:32',1247170532899,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,5,0,1),
 (70,'Sporty','HANTH chess club','ell','2009-04-09 12:00:38',1239267638598,NULL,NULL,'Chess and other mind games','Reminder!',NULL,NULL,0,2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,5,0,1),
 (71,'TV series','Home','ell','2009-04-09 20:00:20',1239296420369,NULL,NULL,'Lost s05e10','Reminder!','2009-04-09 19:50:20',1239295820370,0,2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,5,0,1),
 (72,'test event','here','ell','2009-04-10 10:10:47',1239347447335,NULL,NULL,'three o\'clock','Reminder!',NULL,NULL,0,2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,5,0,1);
INSERT INTO `extended_event` (`eventID`,`eCategory`,`ePlace`,`eSpokenLang`,`eStartTime`,`eDbStartTime`,`eEndTime`,`eDbEndTime`,`eDescription`,`rMessage`,`rTime`,`rDbTime`,`rRepeatTimes`,`rReminderType`,`aCountryName1`,`aExtAddress`,`aLocality`,`aPostalCode`,`aRegion`,`aStreetName`,`aBuilding`,`isPersistent`,`parentID`,`rInterval`,`isVisible`) VALUES 
 (73,'Sport','Pilaia - Faliro','ell','2009-05-05 17:56:24',1241535384394,NULL,NULL,'Jogging','Reminder!','2009-05-05 17:50:24',1241535024394,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,5,0,1),
 (74,'Cooking','My kichen','ell','2009-05-05 12:10:48',1241514648108,NULL,NULL,NULL,'Reminder!',NULL,NULL,0,2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,5,0,1),
 (75,'TV Series',NULL,'ell','2009-05-05 18:00:42',1241535642558,NULL,NULL,'Heroes s02e03','Reminder!','2009-05-05 17:50:42',1241535042558,0,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,5,0,1),
 (76,'Meeting','UoM','ell','2009-05-05 10:10:26',1241507426365,NULL,NULL,'Company meeting','Reminder!',NULL,NULL,0,2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,5,0,1),
 (81,'sakis reeeee','ss','ell','2009-05-12 22:22:00',1242156120695,NULL,NULL,'eurovision super-duper fun','Reminder!',NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,6,0,1);
INSERT INTO `extended_event` (`eventID`,`eCategory`,`ePlace`,`eSpokenLang`,`eStartTime`,`eDbStartTime`,`eEndTime`,`eDbEndTime`,`eDescription`,`rMessage`,`rTime`,`rDbTime`,`rRepeatTimes`,`rReminderType`,`aCountryName1`,`aExtAddress`,`aLocality`,`aPostalCode`,`aRegion`,`aStreetName`,`aBuilding`,`isPersistent`,`parentID`,`rInterval`,`isVisible`) VALUES 
 (82,'test1','test3','ell','2009-09-09 09:09:00',1252476540500,NULL,NULL,'test2','Reminder!',NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,6,0,1),
 (83,'dsfsgdfgfdgc',NULL,'ell','2009-04-17 11:59:12',1239958752440,NULL,NULL,'sfgsvdfgsdv','Reminder!',NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,6,0,1),
 (84,'tricky',NULL,'ell','2014-10-30 13:13:23',1414667603421,NULL,NULL,'no description provided','Reminder!',NULL,NULL,0,2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,6,0,1),
 (85,'qwerty1',NULL,'ell','2014-10-30 11:03:56',1414659836653,NULL,NULL,'qwerty1','Reminder!',NULL,NULL,0,2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,6,0,1),
 (86,'et: blood party','p: under the fullmoon','ell','2009-03-11 00:01:00',1236722460001,'2009-03-11 01:01:00',1236726060001,'d: drink human blood :-)','Reminder! \nDon\'t forget to have a blood juise:)','2009-03-10 23:50:00',1236721800001,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,6,0,1),
 (201,'Put the event type here...','Put the place here...','ell','2009-12-12 10:10:00',1260605400113,'2009-12-12 12:00:00',1260612000114,'Put the description here...','Time to go!','2009-12-12 10:00:00',1260604800114,0,2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,21,0,1);
INSERT INTO `extended_event` (`eventID`,`eCategory`,`ePlace`,`eSpokenLang`,`eStartTime`,`eDbStartTime`,`eEndTime`,`eDbEndTime`,`eDescription`,`rMessage`,`rTime`,`rDbTime`,`rRepeatTimes`,`rReminderType`,`aCountryName1`,`aExtAddress`,`aLocality`,`aPostalCode`,`aRegion`,`aStreetName`,`aBuilding`,`isPersistent`,`parentID`,`rInterval`,`isVisible`) VALUES 
 (204,'11111111','333333333','ell','2010-10-10 10:10:00',1286694600599,NULL,NULL,'22222222','Reminder!',NULL,NULL,0,2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,6,0,1),
 (205,'sadddddddd','aaaaaaaaaaaaawwwwwwwww','ell','2111-10-10 10:10:00',4473904200876,NULL,NULL,'dddddddddddsssssssssss',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,6,0,1),
 (206,'qqqqqqqqqqqqqqqq','Εδώ είμαι','ell','2011-11-11 11:11:00',1321002660207,NULL,NULL,'wwwwwwwwwwwww',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,6,0,1),
 (207,'3222222222222222','35463456456','ell','2101-10-10 10:10:00',4158371400368,NULL,NULL,'5342534252452',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,6,0,1),
 (208,'qqqqqqqqqqq','dddddddddd','ell','2100-10-10 10:10:00',4126835400591,NULL,NULL,'11111qqqqq111111',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,6,0,1);
INSERT INTO `extended_event` (`eventID`,`eCategory`,`ePlace`,`eSpokenLang`,`eStartTime`,`eDbStartTime`,`eEndTime`,`eDbEndTime`,`eDescription`,`rMessage`,`rTime`,`rDbTime`,`rRepeatTimes`,`rReminderType`,`aCountryName1`,`aExtAddress`,`aLocality`,`aPostalCode`,`aRegion`,`aStreetName`,`aBuilding`,`isPersistent`,`parentID`,`rInterval`,`isVisible`) VALUES 
 (209,'sds',NULL,'ell','2009-05-19 10:00:00',1242716400536,'2009-05-19 11:00:00',1242720000536,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,17,0,1),
 (210,'11111','3333','ell','2009-05-19 11:11:00',1242720660001,NULL,NULL,'2222',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,6,0,1),
 (211,'q1q1q1','e3e3e3','ell','2009-05-19 10:10:00',1242717000908,NULL,NULL,'w2w2w2',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,6,0,1),
 (212,'dfsfd','sdf','ell','2009-05-19 17:50:00',1242744600246,NULL,NULL,'sdf',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,6,0,1),
 (213,'e',NULL,'ell','2009-05-19 10:20:00',1242717600070,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,6,0,1),
 (214,'e',NULL,'ell','2010-10-10 10:10:00',1286694600483,NULL,NULL,NULL,'wtf?','2009-05-19 17:19:00',1242742740483,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,6,0,1);
INSERT INTO `extended_event` (`eventID`,`eCategory`,`ePlace`,`eSpokenLang`,`eStartTime`,`eDbStartTime`,`eEndTime`,`eDbEndTime`,`eDescription`,`rMessage`,`rTime`,`rDbTime`,`rRepeatTimes`,`rReminderType`,`aCountryName1`,`aExtAddress`,`aLocality`,`aPostalCode`,`aRegion`,`aStreetName`,`aBuilding`,`isPersistent`,`parentID`,`rInterval`,`isVisible`) VALUES 
 (215,'yes','what','ell','2009-05-19 17:40:00',1242744000786,NULL,NULL,'no',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,6,0,1),
 (216,'mpla1','mpla3','ell','2009-05-20 10:30:00',1242804600290,NULL,NULL,'mpla2',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,50,0,1),
 (217,'bla','bla','ell','2009-05-20 11:00:00',1242806400002,NULL,NULL,'bla',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,50,0,1),
 (218,'bbb1','bbb3','ell','2009-05-20 11:57:00',1242809820065,NULL,NULL,'bbb2',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,50,0,1),
 (219,'aaaaaaaa','ddddddddddddd','ell','2009-05-20 12:25:00',1242811500718,NULL,NULL,'ssssssssssss',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,50,0,1),
 (222,'e','p','ell','2009-05-20 12:42:00',1242812520733,'2009-05-20 12:50:00',1242813000769,'d',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,50,0,1);
INSERT INTO `extended_event` (`eventID`,`eCategory`,`ePlace`,`eSpokenLang`,`eStartTime`,`eDbStartTime`,`eEndTime`,`eDbEndTime`,`eDescription`,`rMessage`,`rTime`,`rDbTime`,`rRepeatTimes`,`rReminderType`,`aCountryName1`,`aExtAddress`,`aLocality`,`aPostalCode`,`aRegion`,`aStreetName`,`aBuilding`,`isPersistent`,`parentID`,`rInterval`,`isVisible`) VALUES 
 (223,'a','a','ell','2009-05-20 12:55:00',1242813300838,NULL,NULL,'a',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,50,0,1),
 (224,'a','a','ell','2009-05-20 12:57:00',1242813420889,NULL,NULL,'a',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,50,0,1),
 (225,'zzzzzz','cccccccccc','ell','2009-05-20 14:40:00',1242819600463,'2009-05-20 14:50:00',1242820200602,'xxxxxxx','HELLO THERE','2009-05-20 14:51:00',1242820260190,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,50,0,1),
 (226,'WTF?','RT','ell','2009-05-21 12:03:00',1242896580603,'2009-05-21 12:10:00',1242897000114,'ASAP','hallo','2009-05-22 15:38:00',1242995880973,3,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,50,60000,1),
 (227,'test','test','ell','2009-05-22 16:20:00',1242998400545,'2009-05-22 16:22:00',1242998520545,'test','Don\'t forget the festival...','2009-05-22 16:18:00',1242998280469,3,2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,50,60000,1);
INSERT INTO `extended_event` (`eventID`,`eCategory`,`ePlace`,`eSpokenLang`,`eStartTime`,`eDbStartTime`,`eEndTime`,`eDbEndTime`,`eDescription`,`rMessage`,`rTime`,`rDbTime`,`rRepeatTimes`,`rReminderType`,`aCountryName1`,`aExtAddress`,`aLocality`,`aPostalCode`,`aRegion`,`aStreetName`,`aBuilding`,`isPersistent`,`parentID`,`rInterval`,`isVisible`) VALUES 
 (228,'a','d','ell','2009-05-22 16:17:00',1242998220989,'2009-05-22 16:12:00',1242997920545,'s','ehhhmmm','2009-05-22 16:16:00',1242998160466,2,4,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,50,60000,1),
 (229,'various','posidonio','ell','2009-06-24 10:00:00',1245826800216,'2009-06-24 12:00:00',1245834000216,'play basketball',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,50,0,1),
 (230,'sdasd','asda','ell','2009-06-24 10:10:00',1245827400299,'2009-06-25 10:10:00',1245913800300,'asd','It\'s 3:15','2009-06-23 17:28:00',1245767280924,3,4,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,50,60000,0),
 (231,'unknown','here & there','ell','2009-06-25 10:11:00',1245913860461,'2009-06-25 10:30:00',1245915000461,'no comment','τί ώρα είναι?\n¨Ωρα για μπάλα','2009-06-25 15:54:00',1245934440488,3,2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,50,0,0),
 (232,'Sports','Paralia','ell','2009-07-04 18:00:00',1246719600012,'2009-07-04 19:30:00',1246725000012,'Jogging','Get ready for your training course','2009-07-03 16:52:00',1246629120855,2,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,50,60000,1);
/*!40000 ALTER TABLE `extended_event` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
