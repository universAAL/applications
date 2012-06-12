-- MySQL dump 10.13  Distrib 5.5.9, for Win32 (x86)
--
-- Host: localhost    Database: agendauniversaaldb
-- ------------------------------------------------------
-- Server version	5.5.8

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `agendauniversaaldb`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `agendauniversaaldb` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `agendauniversaaldb`;

--
-- Table structure for table `calendartype`
--

DROP TABLE IF EXISTS `calendartype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `calendartype` (
  `idCalendar` int(11) NOT NULL AUTO_INCREMENT,
  `Description` varchar(1000) DEFAULT NULL,
  `idUser` int(10) unsigned NOT NULL,
  PRIMARY KEY (`idCalendar`),
  KEY `fk_CalendarType_Users1` (`idUser`),
  CONSTRAINT `fk_CalendarType_Users1` FOREIGN KEY (`idUser`) REFERENCES `users` (`idUser`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `calendartype`
--

LOCK TABLES `calendartype` WRITE;
/*!40000 ALTER TABLE `calendartype` DISABLE KEYS */;
INSERT INTO `calendartype` VALUES (1,'Medical Calendar',1),(2,'Birthdays',1),(3,'TestNewcalendar',1);
/*!40000 ALTER TABLE `calendartype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `city`
--

DROP TABLE IF EXISTS `city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `city` (
  `idCity` int(11) NOT NULL AUTO_INCREMENT,
  `PostalCode` int(10) unsigned NOT NULL,
  `CityName` varchar(200) NOT NULL,
  `idCountry` int(11) NOT NULL,
  `idState` int(11) DEFAULT NULL,
  PRIMARY KEY (`idCity`),
  UNIQUE KEY `uniqueCity` (`PostalCode`,`CityName`,`idCountry`),
  KEY `fk_City_Country1` (`idCountry`),
  KEY `fk_City_State1` (`idState`),
  CONSTRAINT `fk_City_Country1` FOREIGN KEY (`idCountry`) REFERENCES `country` (`idCountry`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_City_State1` FOREIGN KEY (`idState`) REFERENCES `state` (`idState`) ON DELETE SET NULL ON UPDATE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `city`
--

LOCK TABLES `city` WRITE;
/*!40000 ALTER TABLE `city` DISABLE KEYS */;
INSERT INTO `city` VALUES (1,10000,'Zagreb',1,1);
/*!40000 ALTER TABLE `city` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `country`
--

DROP TABLE IF EXISTS `country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `country` (
  `idCountry` int(11) NOT NULL AUTO_INCREMENT,
  `CountryName` varchar(200) NOT NULL,
  PRIMARY KEY (`idCountry`),
  UNIQUE KEY `CountryName_UNIQUE` (`CountryName`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `country`
--

LOCK TABLES `country` WRITE;
/*!40000 ALTER TABLE `country` DISABLE KEYS */;
INSERT INTO `country` VALUES (1,'Croatia');
/*!40000 ALTER TABLE `country` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event`
--

DROP TABLE IF EXISTS `event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `event` (
  `idEvent` int(11) NOT NULL AUTO_INCREMENT,
  `idCalendar` int(11) NOT NULL,
  `idReminder` int(11) DEFAULT NULL,
  `Title` varchar(50) NOT NULL,
  `Description` varchar(1000) DEFAULT NULL,
  `StartTime` bigint(20) NOT NULL,
  `EndTime` bigint(20) DEFAULT NULL,
  `Location` varchar(150) DEFAULT NULL,
  `RepeatInterval` int(11) DEFAULT NULL,
  `Active` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`idEvent`),
  KEY `fk_Event_Calendar1` (`idCalendar`),
  KEY `fk_Event_Reminder1` (`idReminder`),
  CONSTRAINT `fk_Event_Calendar1` FOREIGN KEY (`idCalendar`) REFERENCES `calendartype` (`idCalendar`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_Event_Reminder1` FOREIGN KEY (`idReminder`) REFERENCES `reminder` (`idReminder`) ON DELETE SET NULL ON UPDATE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event`
--

LOCK TABLES `event` WRITE;
/*!40000 ALTER TABLE `event` DISABLE KEYS */;
INSERT INTO `event` VALUES (2,2,2,'Dad\'s birthday','say congrats',1327748400908,NULL,'home',NULL,1),(4,1,3,'sdf','asd',1337249100524,NULL,'aa',NULL,1),(5,3,5,'Tip','opis',1338131460454,NULL,'mjesto',NULL,1),(6,3,6,'tip','opis',1338217920915,NULL,'mjesto',NULL,1),(7,2,NULL,'rodendan','opis rodendana',1338559980348,1338561180348,'mjesto rodendana',NULL,1),(8,2,9,'sdf','sdf',1338572940000,NULL,'sdf',NULL,1),(9,2,10,'ss','asdasdasaaeeg',1338719520000,NULL,'asdasd',NULL,1),(10,1,11,'asd','dasd',1338720900000,NULL,'a',NULL,1),(11,1,12,'party','go there and have fun',1338828840000,NULL,'son\'s house',NULL,1),(12,1,13,'','',1338830520000,NULL,'',NULL,1),(13,1,14,'fun','goin to zoo',1338753660000,NULL,'zoo',NULL,1),(14,1,15,'','',1338762540000,NULL,'',NULL,1),(15,2,16,'odense event','descr',1338898320000,NULL,'odense',NULL,1),(16,1,17,'odenseHello','',1338907380000,NULL,'odense',NULL,1),(17,1,18,'ss','af4f',1338919980000,NULL,'asdad',NULL,1);
/*!40000 ALTER TABLE `event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `getevents`
--

DROP TABLE IF EXISTS `getevents`;
/*!50001 DROP VIEW IF EXISTS `getevents`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `getevents` (
  `CalendarType` varchar(1000),
  `Title` varchar(50),
  `StartTime` bigint(20),
  `EndTime` bigint(20),
  `Description` varchar(1000),
  `Location` varchar(150),
  `RepeatInterval` int(11),
  `Active` tinyint(1),
  `idCalendar` int(11),
  `idReminder` int(11),
  `idEvent` int(11)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `reminder`
--

DROP TABLE IF EXISTS `reminder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reminder` (
  `idReminder` int(11) NOT NULL AUTO_INCREMENT,
  `idReminderType` int(11) NOT NULL,
  `StartTime` bigint(20) NOT NULL,
  `RepeatInterval` int(11) DEFAULT NULL,
  `RepeatTime` int(11) DEFAULT NULL,
  `Message` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idReminder`),
  KEY `fk_Reminder_ReminderType1` (`idReminderType`),
  CONSTRAINT `fk_Reminder_ReminderType1` FOREIGN KEY (`idReminderType`) REFERENCES `remindertype` (`idReminderType`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reminder`
--

LOCK TABLES `reminder` WRITE;
/*!40000 ALTER TABLE `reminder` DISABLE KEYS */;
INSERT INTO `reminder` VALUES (1,1,1314867600000,5,5,'Take meds now!'),(2,3,1327744800038,820130816,1,'its dad\'s birthday'),(3,2,1337245500222,1640261632,0,'message3'),(4,3,1338217860377,-694967296,1,'gggg'),(5,3,1338131460551,-2084901888,1,' '),(6,3,1338217920461,1515098112,1,'poruka!'),(8,3,1338560880030,-1389934592,1,'rree'),(9,3,1338572940000,60000,1,'take pills'),(10,3,1338719640000,60000,1,'rem'),(11,3,1338720900000,60000,1,'asdasdaeq'),(12,3,1338742620000,60000,1,'remember son\'s party'),(13,3,1338744300000,60000,1,'take pills now!'),(14,3,1338753900000,300000,1,'remember to go to zoo'),(15,3,1338762780000,60000,1,'remember the concert'),(16,3,1338898560000,60000,1,'hello odense'),(17,3,1338907560000,60000,1,'hello Odense'),(18,3,1338920160000,60000,1,'hi Gema');
/*!40000 ALTER TABLE `reminder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `remindertype`
--

DROP TABLE IF EXISTS `remindertype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `remindertype` (
  `idReminderType` int(11) NOT NULL AUTO_INCREMENT,
  `RTypeName` varchar(45) NOT NULL,
  PRIMARY KEY (`idReminderType`),
  UNIQUE KEY `RTypeName_UNIQUE` (`RTypeName`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `remindertype`
--

LOCK TABLES `remindertype` WRITE;
/*!40000 ALTER TABLE `remindertype` DISABLE KEYS */;
INSERT INTO `remindertype` VALUES (1,'Blinking light'),(2,'Sound'),(3,'Visual Message'),(4,'Voice Message');
/*!40000 ALTER TABLE `remindertype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `state`
--

DROP TABLE IF EXISTS `state`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `state` (
  `idState` int(11) NOT NULL AUTO_INCREMENT,
  `StateName` varchar(200) NOT NULL,
  PRIMARY KEY (`idState`),
  UNIQUE KEY `StateName_UNIQUE` (`StateName`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `state`
--

LOCK TABLES `state` WRITE;
/*!40000 ALTER TABLE `state` DISABLE KEYS */;
INSERT INTO `state` VALUES (1,'City of Zagreb');
/*!40000 ALTER TABLE `state` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `idUser` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `idUserType` int(10) unsigned NOT NULL,
  `UName` varchar(100) NOT NULL,
  `USurname` varchar(100) NOT NULL,
  `Address` varchar(100) DEFAULT NULL,
  `idCity` int(11) DEFAULT NULL,
  `UserName` varchar(45) NOT NULL,
  `Password` varchar(45) NOT NULL,
  PRIMARY KEY (`idUser`),
  UNIQUE KEY `UserName_UNIQUE` (`UserName`),
  KEY `fk_User_UserType` (`idUserType`),
  KEY `fk_Users_City1` (`idCity`),
  CONSTRAINT `fk_Users_City1` FOREIGN KEY (`idCity`) REFERENCES `city` (`idCity`) ON DELETE SET NULL ON UPDATE SET NULL,
  CONSTRAINT `fk_User_UserType` FOREIGN KEY (`idUserType`) REFERENCES `usertype` (`idUserType`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1 COMMENT='User data.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,1,'Probo','Probic','Probna 22',1,'saied','a'),(2,2,'Il','medico','KBC',1,'d','d');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usersrelationship`
--

DROP TABLE IF EXISTS `usersrelationship`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usersrelationship` (
  `idUser` int(10) unsigned NOT NULL,
  `idRelative` int(10) unsigned NOT NULL,
  PRIMARY KEY (`idUser`,`idRelative`),
  KEY `fk_User_has_User_User` (`idUser`),
  KEY `fk_User_has_User_User1` (`idRelative`),
  CONSTRAINT `fk_User_has_User_User` FOREIGN KEY (`idUser`) REFERENCES `users` (`idUser`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_User_has_User_User1` FOREIGN KEY (`idRelative`) REFERENCES `users` (`idUser`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usersrelationship`
--

LOCK TABLES `usersrelationship` WRITE;
/*!40000 ALTER TABLE `usersrelationship` DISABLE KEYS */;
/*!40000 ALTER TABLE `usersrelationship` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,TRADITIONAL,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER beforeInsert BEFORE INSERT ON UsersRelationship
FOR EACH ROW 
BEGIN
    IF (NEW.idUser = NEW.idRelative) THEN
    SET NEW.idUser = 1/0;
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `usertype`
--

DROP TABLE IF EXISTS `usertype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usertype` (
  `idUserType` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Description` varchar(200) NOT NULL,
  PRIMARY KEY (`idUserType`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usertype`
--

LOCK TABLES `usertype` WRITE;
/*!40000 ALTER TABLE `usertype` DISABLE KEYS */;
INSERT INTO `usertype` VALUES (1,'Assisted Person'),(2,'Informal'),(3,'Formal');
/*!40000 ALTER TABLE `usertype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'agendauniversaaldb'
--
/*!50003 DROP FUNCTION IF EXISTS `updateReminder` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,TRADITIONAL,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 FUNCTION `updateReminder`(idE INT, idRT INT, startTime BIGINT, rptInt INT, rptTime INT, msg VARCHAR(45)) RETURNS int(11)
BEGIN

DECLARE idR INT;
SELECT e.idReminder INTO idR FROM `agendauniversaaldb`.`event` e WHERE 
e.idEvent=idE;

IF idR IS NOT NULL THEN
    UPDATE agendaUniversAALDB.Reminder 
    SET idReminderType=idRT, StartTime=startTime, RepeatInterval=rptInt, RepeatTime=rptTime,
    Message=msg WHERE idReminder=idR;
    RETURN idR;
ELSE
    RETURN -1;
END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `cancelReminder` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,TRADITIONAL,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `cancelReminder`(eID INT)
BEGIN
DECLARE rID INT;
SELECT idReminder INTO rID FROM event where idEvent = eID;
DELETE FROM reminder WHERE idReminder=rID;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `EventReminder` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,TRADITIONAL,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `EventReminder`(idE INT)
BEGIN
DECLARE idR INT;
SELECT idReminder INTO idR FROM event WHERE idEvent=idE;
IF idR IS NOT NULL THEN
    SELECT r.* FROM reminder r WHERE r.idReminder=idR;
ELSE
    SELECT r.* FROM reminder r WHERE r.idReminder=-1;
END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `removeEvent` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,TRADITIONAL,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `removeEvent`(eID INT)
BEGIN
DECLARE rID INT;
SELECT e.idReminder INTO rID FROM Event e WHERE e.idEvent=eID;

IF rID IS NOT NULL THEN
    DELETE FROM Reminder WHERE idReminder = rID;
END IF;

DELETE FROM Event WHERE idEvent=eID;

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `userData` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,TRADITIONAL,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `userData`(usr VARCHAR(45), pwd VARCHAR(45))
BEGIN

DECLARE idCity INT;
DECLARE uname VARCHAR(100);
DECLARE idState INT;
SELECT u.idCity, u.Uname INTO idCity, uname FROM users u WHERE u.UserName=usr AND u.Password=pwd; 

IF uname IS NOT NULL THEN

    IF idCity IS NOT NULL THEN
    
        SELECT c.idState INTO idState FROM city c WHERE c.idCity=idCity;
        
        IF idState IS NOT NULL THEN
            SELECT u.idUser, u.UName, u.USurname, u.Address, u.UserName, u.Password, c.PostalCode, c.CityName, s.StateName, co.CountryName
            FROM `agendaUniversAALDB`.`Users` u, 
            `agendaUniversAALDB`.`City` c,
            `agendaUniversAALDB`.`Country` co,
            `agendaUniversAALDB`.`State` s WHERE
            (u.idCity = c.idCity AND 
            c.idCountry = co.idCountry AND 
            c.idState=s.idState);
        ELSE
            SELECT u.idUser, u.UName, u.USurname, u.Address, u.UserName, u.Password, c.PostalCode, c.CityName, co.CountryName
            FROM `agendaUniversAALDB`.`Users` u, 
            `agendaUniversAALDB`.`City` c,
            `agendaUniversAALDB`.`Country` co WHERE
            (u.idCity = c.idCity AND 
            c.idCountry = co.idCountry);
        END IF;
        
    END IF;
    
END IF;    

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Current Database: `agendauniversaaldb`
--

USE `agendauniversaaldb`;

--
-- Final view structure for view `getevents`
--

/*!50001 DROP TABLE IF EXISTS `getevents`*/;
/*!50001 DROP VIEW IF EXISTS `getevents`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `getevents` AS select `cal`.`Description` AS `CalendarType`,`e`.`Title` AS `Title`,`e`.`StartTime` AS `StartTime`,`e`.`EndTime` AS `EndTime`,`e`.`Description` AS `Description`,`e`.`Location` AS `Location`,`e`.`RepeatInterval` AS `RepeatInterval`,`e`.`Active` AS `Active`,`e`.`idCalendar` AS `idCalendar`,`e`.`idReminder` AS `idReminder`,`e`.`idEvent` AS `idEvent` from (`event` `e` join `calendartype` `cal`) where (`e`.`idCalendar` = `cal`.`idCalendar`) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-06-06 14:13:16
