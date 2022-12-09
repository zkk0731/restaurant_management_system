CREATE TABLE IF NOT EXISTS `members` (
  `member_id` int NOT NULL AUTO_INCREMENT,
  `member_account` varchar(30) NOT NULL,
  `pwd` varchar(30) NOT NULL,
  `authority` tinyint NOT NULL,
  `member_name` varchar(30) NOT NULL,
  `phone` varchar(12) NOT NULL,
  `age_range` int DEFAULT '0',
  `line_id` varchar(30) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `points` int DEFAULT '0',
  PRIMARY KEY (`member_id`));

CREATE TABLE IF NOT EXISTS `menu` (
  `commodity_name` varchar(30) NOT NULL,
  `price` int NOT NULL,
  `category` varchar(30) NOT NULL,
  `sales_volume` int DEFAULT NULL,
  PRIMARY KEY (`commodity_name`));

CREATE TABLE IF NOT EXISTS `orders` (
  `order_id` int NOT NULL AUTO_INCREMENT,
  `order_info` varchar(100) NOT NULL,
  `total_price` int NOT NULL,
  `member_account` varchar(30) DEFAULT NULL,
  `order_datetime` datetime NOT NULL,
  `order_state` varchar(30) NOT NULL,
  `points_get` int DEFAULT NULL,
  `points_cost` int DEFAULT NULL,
  PRIMARY KEY (`order_id`));

CREATE TABLE IF NOT EXISTS `points_exchange` (
  `point_id` INT NOT NULL AUTO_INCREMENT,
  `point_name` VARCHAR(30) NOT NULL,
  `discount` INT NULL,
  `points_cost` INT NULL,
  PRIMARY KEY (`point_id`));

