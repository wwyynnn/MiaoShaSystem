# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.25)
# Database: miaosha
# Generation Time: 2020-08-12 08:18:32 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table item_info
# ------------------------------------------------------------

DROP TABLE IF EXISTS `item_info`;

CREATE TABLE `item_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(64) NOT NULL DEFAULT '"',
  `price` double(10,0) NOT NULL DEFAULT '0',
  `description` varchar(500) NOT NULL DEFAULT '"',
  `sales` int(11) NOT NULL DEFAULT '0',
  `img_url` varchar(500) NOT NULL DEFAULT '"',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

LOCK TABLES `item_info` WRITE;
/*!40000 ALTER TABLE `item_info` DISABLE KEYS */;

INSERT INTO `item_info` (`id`, `title`, `price`, `description`, `sales`, `img_url`)
VALUES
	(4,'华为-4',1000,'国产手机',0,'https://image.shutterstock.com/image-photo/vilnius-march-27-huawei-logo-600w-1056947945.jpg'),
	(5,'华为-5',1000,'国产手机',2,'https://image.shutterstock.com/image-photo/vilnius-march-27-huawei-logo-600w-1056947945.jpg'),
	(6,'华为-6',1000,'国产手机',2,'https://image.shutterstock.com/image-photo/vilnius-march-27-huawei-logo-600w-1056947945.jpg'),
	(7,'华为-7',1000,'国产手机',1,'https://image.shutterstock.com/image-photo/vilnius-march-27-huawei-logo-600w-1056947945.jpg'),
	(8,'华为8',1000,'国产手机',0,'https://image.shutterstock.com/image-photo/vilnius-march-27-huawei-logo-600w-1056947945.jpg'),
	(9,'华为9',1000,'国产手机',0,'https://image.shutterstock.com/image-photo/vilnius-march-27-huawei-logo-600w-1056947945.jpg'),
	(10,'华为10',1000,'国产手机',0,'https://image.shutterstock.com/image-photo/vilnius-march-27-huawei-logo-600w-1056947945.jpg'),
	(11,'华为11',1000,'国产手机',0,'https://image.shutterstock.com/image-photo/vilnius-march-27-huawei-logo-600w-1056947945.jpg'),
	(12,'huawei-12',100,'zhenhao',0,'https://image.shutterstock.com/image-photo/vilnius-march-27-huawei-logo-600w-1056947945.jpg'),
	(13,'huawei-13',22,'222',0,'https://image.shutterstock.com/image-photo/vilnius-march-27-huawei-logo-600w-1056947945.jpg'),
	(14,'huawei-14',33,'huawei3',0,'https://image.shutterstock.com/image-photo/vilnius-march-27-huawei-logo-600w-1056947945.jpg'),
	(15,'huawei15',444,'huawei4',0,'https://image.shutterstock.com/image-photo/vilnius-march-27-huawei-logo-600w-1056947945.jpg'),
	(16,'huawei16',55,'huawei5',0,'https://image.shutterstock.com/image-photo/vilnius-march-27-huawei-logo-600w-1056947945.jpg');

/*!40000 ALTER TABLE `item_info` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table item_stock
# ------------------------------------------------------------

DROP TABLE IF EXISTS `item_stock`;

CREATE TABLE `item_stock` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `stock` int(11) NOT NULL DEFAULT '0',
  `item_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

LOCK TABLES `item_stock` WRITE;
/*!40000 ALTER TABLE `item_stock` DISABLE KEYS */;

INSERT INTO `item_stock` (`id`, `stock`, `item_id`)
VALUES
	(4,100,4),
	(5,98,5),
	(6,98,6),
	(7,99,7),
	(8,100,8),
	(9,100,9),
	(10,100,10),
	(11,100,11),
	(12,1,12),
	(13,22,13),
	(14,333,14),
	(15,444,15),
	(16,555,16);

/*!40000 ALTER TABLE `item_stock` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table order_info
# ------------------------------------------------------------

DROP TABLE IF EXISTS `order_info`;

CREATE TABLE `order_info` (
  `id` varchar(32) NOT NULL DEFAULT '',
  `user_id` int(11) NOT NULL DEFAULT '0',
  `item_id` int(11) NOT NULL DEFAULT '0',
  `item_price` double(11,0) NOT NULL DEFAULT '0',
  `amount` int(11) NOT NULL DEFAULT '0',
  `order_price` double(11,0) NOT NULL DEFAULT '0',
  `promo_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `order_info` WRITE;
/*!40000 ALTER TABLE `order_info` DISABLE KEYS */;

INSERT INTO `order_info` (`id`, `user_id`, `item_id`, `item_price`, `amount`, `order_price`, `promo_id`)
VALUES
	('2020081100000000',7,5,0,1,0,0),
	('2020081100000100',7,5,0,1,0,0),
	('2020081100000200',7,7,0,1,0,0),
	('2020081100000300',7,6,1000,1,1000,0),
	('2020081100000400',7,6,1,1,1,1);

/*!40000 ALTER TABLE `order_info` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table promo
# ------------------------------------------------------------

DROP TABLE IF EXISTS `promo`;

CREATE TABLE `promo` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `promo_name` varchar(255) NOT NULL DEFAULT '0',
  `start_date` datetime NOT NULL DEFAULT '1000-01-01 00:00:00',
  `end_date` datetime NOT NULL DEFAULT '1000-01-01 00:00:00',
  `item_id` int(11) NOT NULL DEFAULT '0',
  `promo_item_price` double(11,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

LOCK TABLES `promo` WRITE;
/*!40000 ALTER TABLE `promo` DISABLE KEYS */;

INSERT INTO `promo` (`id`, `promo_name`, `start_date`, `end_date`, `item_id`, `promo_item_price`)
VALUES
	(1,'iphone 4抢购活动','2020-08-11 11:23:00','2020-08-20 12:00:00',6,1.00);

/*!40000 ALTER TABLE `promo` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table sequence_info
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sequence_info`;

CREATE TABLE `sequence_info` (
  `name` varchar(64) NOT NULL DEFAULT '',
  `current_value` int(11) NOT NULL DEFAULT '0',
  `step` int(11) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `sequence_info` WRITE;
/*!40000 ALTER TABLE `sequence_info` DISABLE KEYS */;

INSERT INTO `sequence_info` (`name`, `current_value`, `step`)
VALUES
	('order_info',5,1);

/*!40000 ALTER TABLE `sequence_info` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table user_info
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_info`;

CREATE TABLE `user_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(64) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `gender` tinyint(11) NOT NULL COMMENT '//1-male; 2-female',
  `age` int(11) NOT NULL,
  `telphone` varchar(11) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `register_mode` varchar(11) COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '//by phone; by wechat; by alipay',
  `third_party_id` varchar(64) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `phonenum_unique_index` (`telphone`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

LOCK TABLES `user_info` WRITE;
/*!40000 ALTER TABLE `user_info` DISABLE KEYS */;

INSERT INTO `user_info` (`id`, `name`, `gender`, `age`, `telphone`, `register_mode`, `third_party_id`)
VALUES
	(1,'first user',1,44,'13991299176','by phone','0'),
	(3,'王堰楠',1,12,'13226678387','byphone',''),
	(4,'Admin',2,12,'110','byphone',''),
	(5,'Admin',1,12,'1399199176','byphone',''),
	(7,'123',1,12,'123','byphone',''),
	(8,'james',1,1,'1','byphone',''),
	(9,'wang yannan',1,24,'07422631994','byphone',''),
	(11,'w',1,1,'111','byphone',''),
	(13,'67262',1,2,'222','byphone','');

/*!40000 ALTER TABLE `user_info` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table user_password
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_password`;

CREATE TABLE `user_password` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `encrpt_password` varchar(128) NOT NULL DEFAULT '',
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

LOCK TABLES `user_password` WRITE;
/*!40000 ALTER TABLE `user_password` DISABLE KEYS */;

INSERT INTO `user_password` (`id`, `encrpt_password`, `user_id`)
VALUES
	(1,'fnjdkshkjds',1),
	(3,'ICy5YqxZB1uWSwcVLSNLcA==',3),
	(4,'ICy5YqxZB1uWSwcVLSNLcA==',4),
	(5,'ICy5YqxZB1uWSwcVLSNLcA==',5),
	(6,'ICy5YqxZB1uWSwcVLSNLcA==',7),
	(7,'xMpCOKC5I4INzFCab3WEmw==',8),
	(8,'ICy5YqxZB1uWSwcVLSNLcA==',9),
	(9,'aY1RoZ2KEhzlgUmde3AWaA==',11),
	(10,'vL4zZeasleosA0OiOVg03Q==',13);

/*!40000 ALTER TABLE `user_password` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
