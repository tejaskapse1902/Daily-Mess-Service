-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: localhost    Database: dailymessservice
-- ------------------------------------------------------
-- Server version	9.1.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `attendance`
--

DROP TABLE IF EXISTS `attendance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attendance` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_email` varchar(100) NOT NULL,
  `dish_id` int NOT NULL,
  `is_present` tinyint(1) NOT NULL,
  `marked_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_email` (`user_email`),
  KEY `dish_id` (`dish_id`),
  CONSTRAINT `attendance_ibfk_1` FOREIGN KEY (`user_email`) REFERENCES `users` (`email_id`),
  CONSTRAINT `attendance_ibfk_2` FOREIGN KEY (`dish_id`) REFERENCES `dishes` (`dish_id`)
) ENGINE=InnoDB AUTO_INCREMENT=109 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attendance`
--

LOCK TABLES `attendance` WRITE;
/*!40000 ALTER TABLE `attendance` DISABLE KEYS */;
INSERT INTO `attendance` VALUES (1,'tejaskapse19@gmail.com',2,1,'2024-11-26 08:42:06'),(2,'tejaskapse19@gmail.com',4,1,'2024-11-26 08:42:38'),(3,'tejaskapse19@gmail.com',3,1,'2024-11-26 08:47:16'),(4,'tejaskapse19@gmail.com',2,1,'2024-11-26 08:47:54'),(5,'tejaskapse19@gmail.com',1,1,'2024-11-26 08:56:05'),(6,'nitantdekate45@gmail.com',1,1,'2024-11-26 08:56:36'),(7,'nitantdekate45@gmail.com',3,1,'2024-11-26 08:56:41'),(8,'rakshakpatre409@gmail.com',1,1,'2024-11-26 09:05:37'),(9,'rakshakpatre409@gmail.com',2,1,'2024-11-26 09:05:41'),(10,'rakshakpatre409@gmail.com',3,1,'2024-11-26 09:11:04'),(11,'harshchandekar13@gmail.com',1,1,'2024-11-26 09:22:29'),(12,'harshchandekar13@gmail.com',2,1,'2024-11-26 09:22:35'),(13,'harshchandekar13@gmail.com',3,1,'2024-11-26 09:22:40'),(14,'harshchandekar13@gmail.com',4,1,'2024-11-26 09:22:45'),(15,'tejaskapse19@gmail.com',3,1,'2024-11-26 10:35:44'),(16,'tejaskapse19@gmail.com',7,1,'2024-11-27 09:58:44'),(17,'tejaskapse19@gmail.com',11,1,'2024-11-27 15:42:29'),(18,'tejaskapse19@gmail.com',11,1,'2024-11-28 13:04:06'),(19,'nikhilgalave1@gmail.com',11,1,'2024-12-02 09:29:41'),(20,'nikhilgalave1@gmail.com',10,1,'2024-12-02 09:29:50'),(21,'prajwal@gmail.com',11,1,'2024-12-16 05:32:02'),(22,'prajwal@gmail.com',11,1,'2024-12-19 08:55:52'),(23,'prajwal@gmail.com',10,1,'2024-12-19 08:55:56'),(24,'prajwal@gmail.com',11,1,'2024-12-19 08:56:01'),(25,'prajwal@gmail.com',10,1,'2024-12-19 08:56:04'),(26,'prajwal@gmail.com',11,1,'2024-12-19 08:56:08'),(27,'prajwal@gmail.com',10,1,'2024-12-19 08:56:11'),(28,'prajwal@gmail.com',11,1,'2024-12-19 08:56:14'),(29,'prajwal@gmail.com',10,1,'2024-12-19 08:56:19'),(30,'prajwal@gmail.com',11,1,'2024-12-19 08:56:23'),(31,'prajwal@gmail.com',11,1,'2024-12-19 08:56:25'),(32,'prajwal@gmail.com',11,1,'2024-12-19 08:56:28'),(33,'prajwal@gmail.com',11,1,'2024-12-19 08:56:31'),(34,'sonawanerohit160@gmail.com',13,1,'2024-12-26 06:13:22'),(35,'sonawanerohit160@gmail.com',11,1,'2024-12-26 06:13:29'),(36,'sonawanerohit160@gmail.com',10,1,'2024-12-26 06:13:33'),(37,'sonawanerohit160@gmail.com',12,1,'2024-12-26 06:13:39'),(38,'pratiksha@gmail.com',11,1,'2024-12-26 09:20:32'),(39,'pratiksha@gmail.com',13,1,'2024-12-26 09:20:38'),(40,'pratiksha@gmail.com',10,1,'2024-12-26 09:20:47'),(41,'prajwal@gmail.com',12,1,'2024-12-27 16:01:30'),(42,'prajwal@gmail.com',11,1,'2024-12-27 16:01:34'),(43,'prajwal@gmail.com',11,1,'2024-12-27 16:03:12'),(44,'prajwal@gmail.com',10,1,'2024-12-27 16:03:17'),(45,'prajwal@gmail.com',14,1,'2024-12-27 16:03:21'),(46,'prajwal@gmail.com',10,1,'2024-12-27 16:03:35'),(47,'prajwal@gmail.com',12,1,'2024-12-27 16:03:42'),(48,'prajwal@gmail.com',13,1,'2024-12-27 16:03:46'),(49,'prajwal@gmail.com',11,1,'2024-12-27 16:06:03'),(50,'prajwal@gmail.com',10,1,'2024-12-27 16:06:09'),(51,'prajwal@gmail.com',14,1,'2024-12-27 16:06:15'),(52,'prajwal@gmail.com',13,1,'2024-12-27 16:06:20'),(53,'prajwal@gmail.com',10,1,'2024-12-27 16:06:35'),(54,'prajwal@gmail.com',13,1,'2024-12-27 16:06:39'),(55,'prajwal@gmail.com',12,1,'2024-12-27 16:08:12'),(56,'prajwal@gmail.com',13,1,'2024-12-27 16:08:15'),(57,'prajwal@gmail.com',14,1,'2024-12-27 16:08:18'),(58,'prajwal@gmail.com',15,1,'2024-12-27 16:08:22'),(59,'prajwal@gmail.com',12,1,'2024-12-27 16:08:26'),(60,'tejaskapse19@gmail.com',12,1,'2025-01-02 13:34:20'),(61,'tejaskapse19@gmail.com',13,1,'2025-01-02 13:34:25'),(62,'tejaskapse19@gmail.com',10,1,'2025-01-02 13:34:28'),(63,'tejaskapse19@gmail.com',11,1,'2025-01-02 13:34:31'),(64,'tejaskapse19@gmail.com',13,1,'2025-01-02 13:34:35'),(65,'prajwal@gmail.com',11,1,'2025-01-02 13:34:56'),(66,'prajwal@gmail.com',12,1,'2025-01-02 13:35:00'),(67,'prajwal@gmail.com',12,1,'2025-01-02 13:37:37'),(68,'prajwal@gmail.com',11,1,'2025-01-02 13:37:41'),(69,'prajwal@gmail.com',15,1,'2025-01-02 13:37:44'),(70,'prajwal@gmail.com',12,1,'2025-01-02 13:37:47'),(71,'rakshakpatre409@gmail.com',11,1,'2025-01-02 13:38:18'),(72,'rakshakpatre409@gmail.com',13,1,'2025-01-02 13:38:21'),(73,'rakshakpatre409@gmail.com',12,1,'2025-01-02 13:38:25'),(74,'rakshakpatre409@gmail.com',10,1,'2025-01-02 13:38:28'),(75,'rakshakpatre409@gmail.com',13,1,'2025-01-02 13:38:32'),(76,'nikhilgalave@gmail.com',11,1,'2025-01-02 13:40:16'),(77,'nikhilgalave@gmail.com',12,1,'2025-01-02 13:40:19'),(78,'nikhilgalave@gmail.com',13,1,'2025-01-02 13:40:22'),(79,'nikhilgalave@gmail.com',10,1,'2025-01-02 13:40:25'),(80,'tejaskapse19@gmail.com',11,1,'2025-01-02 13:47:15'),(81,'rakshakpatre409@gmail.com',11,1,'2025-01-02 17:16:43'),(82,'rakshakpatre409@gmail.com',11,1,'2025-01-02 17:16:47'),(83,'prajwal@gmail.com',13,1,'2025-01-04 04:59:33'),(84,'prajwal@gmail.com',11,1,'2025-01-04 04:59:37'),(85,'prajwal@gmail.com',16,1,'2025-01-04 04:59:43'),(86,'prajwal@gmail.com',11,1,'2025-01-04 04:59:46'),(87,'tejaskapse19@gmail.com',11,1,'2025-01-04 05:00:06'),(88,'tejaskapse19@gmail.com',12,1,'2025-01-04 05:00:10'),(89,'tejaskapse19@gmail.com',14,1,'2025-01-04 05:00:15'),(90,'tejaskapse19@gmail.com',11,1,'2025-01-04 05:00:19'),(91,'tejaskapse19@gmail.com',10,1,'2025-01-04 05:00:23'),(92,'tejaskapse19@gmail.com',16,1,'2025-01-04 05:00:28'),(93,'rakshakpatre409@gmail.com',11,1,'2025-01-04 05:01:30'),(94,'rakshakpatre409@gmail.com',16,1,'2025-01-04 05:01:36'),(95,'rakshakpatre409@gmail.com',11,1,'2025-01-04 05:02:14'),(96,'rakshakpatre409@gmail.com',10,1,'2025-01-04 05:02:18'),(97,'prajwal@gmail.com',11,1,'2025-01-22 16:55:51'),(98,'prajwal@gmail.com',15,1,'2025-01-22 16:55:56'),(99,'prajwal@gmail.com',12,1,'2025-01-22 16:56:00'),(100,'manavgadhave@gmail.com',14,1,'2025-01-22 17:00:01'),(101,'manavgadhave@gmail.com',16,1,'2025-01-22 17:00:05'),(102,'manavgadhave@gmail.com',18,1,'2025-01-22 17:00:09'),(103,'manavgadhave@gmail.com',12,1,'2025-01-22 17:00:12'),(104,'manavgadhave@gmail.com',11,1,'2025-01-22 17:00:16'),(105,'tejaskapse19@gmail.com',13,1,'2025-01-22 17:07:15'),(106,'tejaskapse19@gmail.com',15,1,'2025-01-22 17:07:44'),(107,'manavgadhave@gmail.com',15,1,'2025-01-22 17:08:23'),(108,'tejaskapse19@gmail.com',13,1,'2025-01-29 16:33:59');
/*!40000 ALTER TABLE `attendance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dishes`
--

DROP TABLE IF EXISTS `dishes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dishes` (
  `dish_id` int NOT NULL AUTO_INCREMENT,
  `dish_name` varchar(100) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `Is_Active` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`dish_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dishes`
--

LOCK TABLES `dishes` WRITE;
/*!40000 ALTER TABLE `dishes` DISABLE KEYS */;
INSERT INTO `dishes` VALUES (1,'Vegetable Curry',50.00,0),(2,'Chicken Curry',120.00,0),(3,'Paneer Tikka',80.00,0),(4,'Dal Tadka',70.00,0),(5,'Chapati Bhaji',40.00,0),(7,'Full Veg Thali (Unlimited)',70.00,0),(8,'Vegetable Curries',50.00,0),(10,'Chapati Bhaji',40.00,0),(11,'Veg Thali (Unlimited)',60.00,1),(12,'Dhokala',30.00,1),(13,'Upama',20.00,1),(14,'Poha',20.00,1),(15,'Pao Bhaji',45.00,1),(16,'Palak Paneer',110.00,1),(17,'Chapati Bhaji',40.00,0),(18,'Chapati Bhaji',40.00,1);
/*!40000 ALTER TABLE `dishes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `full_name` varchar(100) NOT NULL,
  `email_id` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `mobile_no` varchar(15) NOT NULL,
  `role` enum('admin','user') DEFAULT 'user',
  `is_active` tinyint(1) DEFAULT '1',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_id` (`email_id`),
  UNIQUE KEY `mobile_no` (`mobile_no`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (4,'Admin User','admin@gmail.com','admin@123','8562459504','admin',1,'2024-11-23 15:27:23','2024-11-27 10:03:30'),(5,'Tejas Kapse','tejaskapse19@gmail.com','Pass@123','7558261684','user',1,'2024-11-23 15:27:23','2024-11-23 15:27:23'),(6,'Rakshak Patre','rakshakpatre409@gmail.com','Pass@123','7854129630','user',1,'2024-11-23 15:27:23','2024-11-23 15:27:23'),(7,'Nitant Dekate','nitantdekate45@gmail.com','Pass@123','9632587410','user',1,'2024-11-23 15:35:07','2024-11-23 15:35:07'),(9,'abc','abc@gmail.com','1234','123456789','user',1,'2024-11-24 09:47:47','2024-11-24 09:47:47'),(10,'Harsh Chandekar','harshchandekar13@gmail.com','Pass@123','7558261695','user',1,'2024-11-25 16:16:30','2024-11-25 16:16:30'),(11,'nikhil galave','nikhilgalave1@gmail.com','123456','9561031927','user',1,'2024-12-02 09:28:47','2024-12-02 09:28:47'),(12,'prajwal','prajwal@gmail.com','789456','7894561230','user',1,'2024-12-16 05:31:17','2024-12-16 05:31:17'),(13,'rohit patil','sonawanerohit160@gmail.com','Rohit@2003','9112898704','user',1,'2024-12-26 06:12:37','2024-12-26 06:12:37'),(15,'pratiksha','pratiksha@gmail.com','Pass@123','4568527410','user',1,'2024-12-26 09:19:57','2024-12-26 09:19:57'),(17,'Nikhil Galave','nikhilgalave@gmail.com','Pass@123','9561031926','user',1,'2025-01-02 13:39:55','2025-01-02 13:39:55'),(18,'Manav Gadhave','manavgadhave@gmail.com','Pass@123','7539517412369','user',1,'2025-01-22 16:59:39','2025-01-22 16:59:39');
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

-- Dump completed on 2025-01-29 22:35:20
