-- MySQL dump 10.13  Distrib 5.7.27, for Linux (x86_64)
--
-- Host: localhost    Database: chat_application
-- ------------------------------------------------------
-- Server version	5.7.27-0ubuntu0.18.04.1

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
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message` (
  `sname` varchar(50) NOT NULL,
  `rname` varchar(50) NOT NULL,
  `time` datetime NOT NULL,
  `content` varchar(500) DEFAULT NULL,
  `display` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`sname`,`rname`,`time`),
  KEY `rname` (`rname`),
  CONSTRAINT `message_ibfk_1` FOREIGN KEY (`sname`) REFERENCES `users` (`uname`),
  CONSTRAINT `message_ibfk_2` FOREIGN KEY (`rname`) REFERENCES `users` (`uname`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES ('user_1','user_2','2019-11-07 06:42:15','Hello','user_2'),('user_1','user_2','2019-11-07 06:42:29','This is the testing message!','user_2'),('user_1','user_2','2019-11-07 06:43:51','Ok then chek for live messaging','user_2'),('user_1','user_2','2019-11-07 06:44:34','just a minute','user_2'),('user_1','user_2','2019-11-07 06:47:54','can you get that','user_2'),('user_1','user_2','2019-11-07 06:48:07','me too','user_2'),('user_1','user_2','2019-11-07 06:51:16','yes! tell me','user_2'),('user_1','user_2','2019-11-07 06:51:30','Is everything fine?','user_2'),('user_1','user_2','2019-11-07 06:52:25','again it interrupts','user_2'),('user_1','user_2','2019-11-07 06:59:03','yes','user_2'),('user_1','user_2','2019-11-07 07:00:35','It seems it works fine for some times and the again fails... :(','user_2'),('user_1','user_2','2019-11-07 16:08:52','how the things are going?','user_2'),('user_1','user_2','2019-11-07 16:18:31','no','user_2'),('user_1','user_2','2019-11-07 16:20:00','hello','user_2'),('user_1','user_2','2019-11-07 16:20:35','234','user_2'),('user_1','user_2','2019-11-07 16:21:00','ok','user_2'),('user_1','user_2','2019-11-07 19:49:02','hello','user_2'),('user_1','user_2','2019-11-07 19:49:20','its final time','user_2'),('user_1','user_2','2019-11-07 20:06:06','new message','user_2'),('user_1','user_2','2019-11-07 20:38:40','new message','user_2'),('user_1','user_2','2019-11-10 05:44:08','han be gandu','user_2'),('user_1','user_2','2019-11-10 05:44:32','chutiye','user_2'),('user_1','user_3','2019-11-07 06:55:19','hii','user_3'),('user_1','user_3','2019-11-07 06:55:59','dkdkd','user_3'),('user_1','user_3','2019-11-07 06:56:10','just a minute','user_3'),('user_1','user_3','2019-11-07 07:06:11','It not seems that good','user_3'),('user_1','user_3','2019-11-07 16:05:26','good morning','user_3'),('user_1','user_3','2019-11-07 16:06:15','should we have to test it again?','user_3'),('user_1','user_3','2019-11-07 16:07:02','ok then going to send some msgs to user_2','user_3'),('user_1','user_3','2019-11-07 16:07:18','you should have to be online till then','user_3'),('user_1','user_3','2019-11-07 16:19:10','got this','user_3'),('user_2','user_1','2019-11-07 06:42:56','Hii','user_2'),('user_2','user_1','2019-11-07 06:43:09','I am recieving the messages!','user_2'),('user_2','user_1','2019-11-07 06:49:34','hii','user_2'),('user_2','user_1','2019-11-07 06:50:00','jjdfklaf','user_2'),('user_2','user_1','2019-11-07 06:50:18','sorry for that','user_2'),('user_2','user_1','2019-11-07 06:50:26','I just got frustated!','user_2'),('user_2','user_1','2019-11-07 06:51:00','hey are you there?','user_2'),('user_2','user_1','2019-11-07 06:51:41','not probably','user_2'),('user_2','user_1','2019-11-07 06:58:56','got it??','user_2'),('user_2','user_1','2019-11-07 06:59:17','nice','user_2'),('user_2','user_1','2019-11-07 07:00:53','may be server problem... I think so','user_2'),('user_2','user_1','2019-11-07 07:02:29','bye bye','user_2'),('user_2','user_1','2019-11-07 07:02:54','one minute','user_2'),('user_2','user_1','2019-11-07 16:08:26','hii','user_2'),('user_2','user_1','2019-11-07 16:08:59','not well (','user_2'),('user_2','user_1','2019-11-07 16:19:49','hii','user_2'),('user_2','user_1','2019-11-07 16:20:31','123','user_2'),('user_2','user_1','2019-11-07 16:20:51','not got','user_2'),('user_2','user_1','2019-11-07 16:21:05','hiii','user_2'),('user_2','user_1','2019-11-07 19:49:09','got it','user_2'),('user_2','user_1','2019-11-07 20:06:17','mil gya','user_2'),('user_2','user_1','2019-11-07 20:39:13','confirm','user_2'),('user_2','user_1','2019-11-10 05:41:53','aur bhosdik','user_2'),('user_2','user_1','2019-11-10 05:44:44','nhi aayi bhodik','user_2'),('user_2','user_1','2019-11-10 05:44:51','dkfjakl','user_2'),('user_2','user_3','2019-11-07 06:57:09','hii',NULL),('user_2','user_3','2019-11-07 07:03:27','again got failed',NULL),('user_2','user_3','2019-11-07 16:11:55','hiii',NULL),('user_2','user_3','2019-11-07 16:17:17','okay',NULL),('user_2','user_4','2019-11-07 20:51:24','hello',NULL),('user_3','user_1','2019-11-07 06:54:42','hello','user_3'),('user_3','user_1','2019-11-07 06:54:50','hello','user_3'),('user_3','user_1','2019-11-07 06:56:20','why you not recieve','user_3'),('user_3','user_1','2019-11-07 07:03:39','but I got your message','user_3'),('user_3','user_1','2019-11-07 07:05:22','checking with single user','user_3'),('user_3','user_1','2019-11-07 07:05:29','hope It will work','user_3'),('user_3','user_1','2019-11-07 07:06:26','yeah! :(','user_3'),('user_3','user_1','2019-11-07 07:06:48',':(','user_3'),('user_3','user_1','2019-11-07 07:07:03','may be some problem there','user_3'),('user_3','user_1','2019-11-07 16:05:55','good morning','user_3'),('user_3','user_1','2019-11-07 16:06:30','Yeah! I think so','user_3'),('user_3','user_1','2019-11-07 16:18:10','reply when you get this','user_3'),('user_3','user_1','2019-11-07 21:07:19','i m going to delete everything','user_3'),('user_3','user_1','2019-11-07 21:07:26','is it okay?','user_3'),('user_3','user_2','2019-11-07 16:10:07','sorry dude',NULL),('user_3','user_2','2019-11-07 16:11:33','hello',NULL),('user_3','user_2','2019-11-07 16:11:39','are you there??',NULL),('user_3','user_2','2019-11-07 16:12:15','got it',NULL),('user_3','user_2','2019-11-07 16:17:12','final time testing',NULL),('user_3','user_2','2019-11-07 16:17:26','look how it works',NULL),('user_3','user_2','2019-11-07 16:17:51','going to logout... ',NULL),('user_3','user_4','2019-11-07 20:52:21','hello','user_4'),('user_3','user_4','2019-11-07 20:52:58','got it','user_4'),('user_4','user_2','2019-11-07 20:51:15','hii',NULL),('user_4','user_3','2019-11-07 20:52:46','hii','user_4'),('user_4','user_3','2019-11-07 20:53:03','ok','user_4'),('user_4','user_4','2019-11-07 20:52:30','hii',NULL);
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `uname` varchar(50) NOT NULL,
  `password` varchar(50) DEFAULT NULL,
  `fname` varchar(50) DEFAULT NULL,
  `lname` varchar(50) DEFAULT NULL,
  `mail` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`uname`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('user_1','12345','test','user','user1@gmail.com'),('user_2','12345','test','user2','user2@gmail.com'),('user_3','12345','test','user3','user3@gmail.com'),('user_4','12345','new','user','user4@gmail.com');
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

-- Dump completed on 2019-11-18 22:45:52
