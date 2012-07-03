-- MySQL dump 10.13  Distrib 5.5.17, for Win32 (x86)
--
-- Host: localhost    Database: safety_home
-- ------------------------------------------------------
-- Server version	5.5.17

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
-- Table structure for table `device`
--

DROP TABLE IF EXISTS `device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device` (
  `device_id` int(11) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `location` varchar(50) DEFAULT NULL,
  `manufacturer` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`device_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `device`
--

LOCK TABLES `device` WRITE;
/*!40000 ALTER TABLE `device` DISABLE KEYS */;
INSERT INTO `device` VALUES (1,'Omnikey 5131 CL','Front Door','HID Global');
/*!40000 ALTER TABLE `device` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `device_to_users_to_smartcard`
--

DROP TABLE IF EXISTS `device_to_users_to_smartcard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device_to_users_to_smartcard` (
  `device_to_users_to_smartcard_id` int(11) NOT NULL,
  `device_id` int(11) NOT NULL,
  `users_id` int(11) NOT NULL,
  `smartcard_id` int(11) NOT NULL,
  PRIMARY KEY (`device_to_users_to_smartcard_id`),
  KEY `device_id` (`device_id`),
  KEY `users_id` (`users_id`),
  KEY `smartcard_id` (`smartcard_id`),
  CONSTRAINT `device_to_users_to_smartcard_ibfk_1` FOREIGN KEY (`device_id`) REFERENCES `device` (`device_id`) ON UPDATE CASCADE,
  CONSTRAINT `device_to_users_to_smartcard_ibfk_2` FOREIGN KEY (`users_id`) REFERENCES `users` (`users_id`) ON UPDATE CASCADE,
  CONSTRAINT `device_to_users_to_smartcard_ibfk_3` FOREIGN KEY (`smartcard_id`) REFERENCES `smartcard` (`smartcard_id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `device_to_users_to_smartcard`
--

LOCK TABLES `device_to_users_to_smartcard` WRITE;
/*!40000 ALTER TABLE `device_to_users_to_smartcard` DISABLE KEYS */;
INSERT INTO `device_to_users_to_smartcard` VALUES (1,1,1,1),(2,1,2,2),(3,1,5,3),(4,1,3,4),(5,1,4,5);
/*!40000 ALTER TABLE `device_to_users_to_smartcard` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `role_id` int(11) NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'Assisted Person',' '),(2,'Formal Caregiver',' '),(3,'Informal Caregiver',' ');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smartcard`
--

DROP TABLE IF EXISTS `smartcard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smartcard` (
  `smartcard_id` int(11) NOT NULL,
  `shortdescription` varchar(20) DEFAULT NULL,
  `description` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`smartcard_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smartcard`
--

LOCK TABLES `smartcard` WRITE;
/*!40000 ALTER TABLE `smartcard` DISABLE KEYS */;
INSERT INTO `smartcard` VALUES (1,'E007000012D70E90',' '),(2,'E007000012D70E94',' '),(3,'E007000012D70E93',' '),(4,'E007000012D70E92',' '),(5,'E007000012D70E91',' ');
/*!40000 ALTER TABLE `smartcard` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `users_id` int(11) NOT NULL,
  `firstname` varchar(50) DEFAULT NULL,
  `lastname` varchar(50) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `job` varchar(100) DEFAULT NULL,
  `email` varchar(70) DEFAULT NULL,
  `username` varchar(20) DEFAULT NULL,
  `password` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`users_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Kostas','Kalogirou',33,'senior software engineer','kalogir@certh.gr','kalogir','kalogir'),(2,'Paulos','Spanidis',31,'senior software engineer','spanidis@certh.gr','spanidis','spanidis'),(3,'Nikos','Dimokas',33,'junior software engineer','dimokas@certh.gr','dimokas','dimokas'),(4,'Taxiarchis','Tsaprounis',31,'junior software engineer','tsaprounis@certh.gr','tax','tax'),(5,'Olga','Gkaitatzi',27,'junior software engineer','ogkaitatzi@certh.gr','olga','olga');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_to_role`
--

DROP TABLE IF EXISTS `users_to_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users_to_role` (
  `users_to_role_id` int(11) NOT NULL,
  `users_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`users_to_role_id`),
  KEY `users_id` (`users_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `users_to_role_ibfk_1` FOREIGN KEY (`users_id`) REFERENCES `users` (`users_id`) ON UPDATE CASCADE,
  CONSTRAINT `users_to_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_to_role`
--

LOCK TABLES `users_to_role` WRITE;
/*!40000 ALTER TABLE `users_to_role` DISABLE KEYS */;
INSERT INTO `users_to_role` VALUES (1,5,1),(2,1,3),(3,2,2);
/*!40000 ALTER TABLE `users_to_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-05-29 17:02:07
