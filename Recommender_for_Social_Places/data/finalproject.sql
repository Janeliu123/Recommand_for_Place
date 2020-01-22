-- MySQL dump 10.13  Distrib 5.7.21, for macos10.13 (x86_64)
--
-- Host: localhost    Database: finalproject
-- ------------------------------------------------------
-- Server version	5.7.21

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
-- Table structure for table `SearchHistory`
--

DROP TABLE IF EXISTS `SearchHistory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SearchHistory` (
  `username` varchar(255) DEFAULT NULL,
  `item` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SearchHistory`
--

LOCK TABLES `SearchHistory` WRITE;
/*!40000 ALTER TABLE `SearchHistory` DISABLE KEYS */;
INSERT INTO `SearchHistory` VALUES ('user7','coffee','60616'),('user5','club','60616'),('user6','restaurant','60609'),('user8','coffee','60608'),('user1','food','chicago'),('user4','club','chicago');
/*!40000 ALTER TABLE `SearchHistory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `preference`
--

DROP TABLE IF EXISTS `preference`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `preference` (
  `username` varchar(40) DEFAULT NULL,
  `American` varchar(1) DEFAULT NULL,
  `Indian` varchar(1) DEFAULT NULL,
  `Asian` varchar(1) DEFAULT NULL,
  `Halal` varchar(1) DEFAULT NULL,
  `Mexican` varchar(1) DEFAULT NULL,
  `bars` varchar(1) DEFAULT NULL,
  `museum` varchar(1) DEFAULT NULL,
  `park` varchar(1) DEFAULT NULL,
  `shopping` varchar(1) DEFAULT NULL,
  `coffee` varchar(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `preference`
--

LOCK TABLES `preference` WRITE;
/*!40000 ALTER TABLE `preference` DISABLE KEYS */;
INSERT INTO `preference` VALUES ('user1','1','0','0','0','0','1','1','1','1','0'),('user2','0','1','1','0','0','1','1','1','1','1'),('user3','1','1','1','0','1','1','0','1','0','1'),('user4','1','0','1','0','1','0','1','1','0','1'),('user5','1','1','1','0','0','0','0','1','0','0'),('user6','0','1','1','0','1','1','0','1','1','0'),('user7','1','1','0','0','0','0','0','1','1','0'),('user8','1','0','0','1','1','1','0','1','0','0'),('user9','1','1','1','1','1','0','0','0','0','0'),('user9','1','1','1','1','1','0','0','0','0','0'),('user9','0','0','0','0','0','1','1','1','1','1');
/*!40000 ALTER TABLE `preference` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `repassword` varchar(100) DEFAULT NULL,
  `gender` varchar(100) DEFAULT NULL,
  `zipcode` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('user1','111','111','female','60616'),('user2','222','222','male','60608'),('user3','333','333','female','60606'),('user4','444','444','male','60600'),('user5','555','555','male','60616'),('user6','666','666','female','60609'),('user7','777','777','male','60616'),('user8','888','888','male','60608'),('user9','999','999','female','60616');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-11-22 15:20:15
