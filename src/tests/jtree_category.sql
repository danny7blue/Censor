/*
Navicat MySQL Data Transfer

Source Server         : MYSQL_LOCAL
Source Server Version : 80012
Source Host           : localhost:3306
Source Database       : jtree_category

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2018-10-16 17:38:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `catid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `catname` varchar(45) NOT NULL,
  PRIMARY KEY (`catid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES ('1', 'Monitor');
INSERT INTO `category` VALUES ('2', 'Mouse');
INSERT INTO `category` VALUES ('3', 'Keyboard');
INSERT INTO `category` VALUES ('4', 'Mainboard');
INSERT INTO `category` VALUES ('5', 'Harddisk');

-- ----------------------------
-- Table structure for price_data
-- ----------------------------
DROP TABLE IF EXISTS `price_data`;
CREATE TABLE `price_data` (
  `scatid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `品牌名` varchar(255) DEFAULT NULL,
  `价格` float(255,0) DEFAULT NULL,
  PRIMARY KEY (`scatid`),
  CONSTRAINT `FK_Price_Data` FOREIGN KEY (`scatid`) REFERENCES `subcategory` (`scatid`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of price_data
-- ----------------------------
INSERT INTO `price_data` VALUES ('1', 'Samsung', '1000');
INSERT INTO `price_data` VALUES ('2', 'A4Tech', '100');
INSERT INTO `price_data` VALUES ('4', 'Sonic', '50');
INSERT INTO `price_data` VALUES ('6', 'MSI', '700');
INSERT INTO `price_data` VALUES ('7', 'Samsung', '500');

-- ----------------------------
-- Table structure for subcategory
-- ----------------------------
DROP TABLE IF EXISTS `subcategory`;
CREATE TABLE `subcategory` (
  `scatid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `catid` int(10) unsigned NOT NULL,
  `scatname` varchar(45) NOT NULL,
  PRIMARY KEY (`scatid`),
  KEY `FK_subcategory_1` (`catid`),
  CONSTRAINT `FK_subcategory_1` FOREIGN KEY (`catid`) REFERENCES `category` (`catid`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of subcategory
-- ----------------------------
INSERT INTO `subcategory` VALUES ('1', '1', 'Samsung');
INSERT INTO `subcategory` VALUES ('2', '2', 'A4Tech');
INSERT INTO `subcategory` VALUES ('3', '1', 'Philips');
INSERT INTO `subcategory` VALUES ('4', '2', 'Sonic');
INSERT INTO `subcategory` VALUES ('5', '3', 'Perfect');
INSERT INTO `subcategory` VALUES ('6', '4', 'MSI');
INSERT INTO `subcategory` VALUES ('7', '5', 'Samsung');
