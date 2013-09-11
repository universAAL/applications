-- MySQL dump 10.13  Distrib 5.5.17, for Win32 (x86)
--
-- Host: localhost    Database: food_shopping
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
-- Table structure for table `code`
--

DROP TABLE IF EXISTS `code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `code` (
  `code_id` int(11) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `size` varchar(50) DEFAULT NULL,
  `company` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`code_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `code`
--

LOCK TABLES `code` WRITE;
/*!40000 ALTER TABLE `code` DISABLE KEYS */;
INSERT INTO `code` VALUES (1,'Fresh Milk','1lt','MEVGAL'),(2,'Fresh Milk','1lt','DELTA'),(3,'Fresh Milk','1lt','FAGE'),(4,'Fresh Milk','1lt','AGNO'),(5,'Fresh Milk','0.5lt','MEVGAL'),(6,'Fresh Milk','0.5lt','DELTA'),(7,'Fresh Milk','0.5lt','FAGE'),(8,'Fresh Milk','0.5lt','AGNO'),(9,'Water','1lt','BIKOS'),(10,'Water','1lt','AYRA'),(11,'Water','1lt','IOLI'),(12,'Water','1lt','KORPI'),(13,'Water','0.5lt','BIKOS'),(14,'Water','0.5lt','AYRA'),(15,'Water','0.5lt','IOLI'),(16,'Water','0.5lt','KORPI'),(17,'Egg','6 pieces','Golden Egg'),(18,'Egg','12 pieces','Golden Egg');
/*!40000 ALTER TABLE `code` ENABLE KEYS */;
UNLOCK TABLES;

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
INSERT INTO `device` VALUES (1,'PulsarMX','Kitchen','metraTec');
/*!40000 ALTER TABLE `device` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `include`
--

DROP TABLE IF EXISTS `include`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `include` (
  `include_id` int(11) NOT NULL,
  `price` float(7,2) DEFAULT NULL,
  `code_id` int(11) NOT NULL,
  `shoppinglist_id` int(11) NOT NULL,
  PRIMARY KEY (`include_id`),
  KEY `code_id` (`code_id`),
  KEY `shoppinglist_id` (`shoppinglist_id`),
  CONSTRAINT `include_ibfk_1` FOREIGN KEY (`code_id`) REFERENCES `code` (`code_id`) ON UPDATE CASCADE,
  CONSTRAINT `include_ibfk_2` FOREIGN KEY (`shoppinglist_id`) REFERENCES `shoppinglist` (`shoppinglist_id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `include`
--

LOCK TABLES `include` WRITE;
/*!40000 ALTER TABLE `include` DISABLE KEYS */;
INSERT INTO `include` VALUES (1,0.95,1,1),(2,0.95,1,1),(3,0.50,13,1),(4,0.50,13,1),(5,0.50,13,1),(6,0.50,13,1),(7,0.00,2,4),(8,0.00,9,4),(9,0.00,9,4),(10,0.00,9,4),(11,0.00,10,4),(12,0.00,10,4),(13,0.00,18,4),(14,0.00,1,4),(15,0.00,13,4);
/*!40000 ALTER TABLE `include` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item` (
  `item_id` int(11) NOT NULL,
  `insertion` date DEFAULT NULL,
  `expiration` date DEFAULT NULL,
  `quantity` double(7,2) DEFAULT NULL,
  `code_id` int(11) NOT NULL,
  `device_id` int(11) NOT NULL,
  `rfidtag_id` int(11) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  PRIMARY KEY (`item_id`),
  KEY `code_id` (`code_id`),
  KEY `device_id` (`device_id`),
  KEY `rfidtag_id` (`rfidtag_id`),
  CONSTRAINT `item_ibfk_1` FOREIGN KEY (`code_id`) REFERENCES `code` (`code_id`) ON UPDATE CASCADE,
  CONSTRAINT `item_ibfk_2` FOREIGN KEY (`device_id`) REFERENCES `device` (`device_id`) ON UPDATE CASCADE,
  CONSTRAINT `item_ibfk_3` FOREIGN KEY (`rfidtag_id`) REFERENCES `rfidtag` (`rfidtag_id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
INSERT INTO `item` VALUES (1,'2012-03-26','2012-04-29',1.00,1,1,2,1),(2,'2012-03-26','2012-05-11',1.00,13,1,1,1);
/*!40000 ALTER TABLE `item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rfidtag`
--

DROP TABLE IF EXISTS `rfidtag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rfidtag` (
  `rfidtag_id` int(11) NOT NULL,
  `shortdescription` varchar(30) DEFAULT NULL,
  `description` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`rfidtag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rfidtag`
--

LOCK TABLES `rfidtag` WRITE;
/*!40000 ALTER TABLE `rfidtag` DISABLE KEYS */;
INSERT INTO `rfidtag` VALUES (1,'CC0000000000000000000003',''),(2,'E2003411B802011665038137','');
/*!40000 ALTER TABLE `rfidtag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shoppinglist`
--

DROP TABLE IF EXISTS `shoppinglist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shoppinglist` (
  `shoppinglist_id` int(11) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `creation` date DEFAULT NULL,
  `price` double(7,2) DEFAULT NULL,
  `users_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`shoppinglist_id`),
  KEY `users_id` (`users_id`),
  CONSTRAINT `shoppinglist_ibfk_1` FOREIGN KEY (`users_id`) REFERENCES `users` (`users_id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shoppinglist`
--

LOCK TABLES `shoppinglist` WRITE;
/*!40000 ALTER TABLE `shoppinglist` DISABLE KEYS */;
INSERT INTO `shoppinglist` VALUES (1,'first shopping list',1,'2012-03-27',100.00,1),(4,'second shopping list',0,'2012-05-30',0.00,1);
/*!40000 ALTER TABLE `shoppinglist` ENABLE KEYS */;
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
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-05-29 17:02:41
