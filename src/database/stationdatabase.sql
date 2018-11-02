/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 80012
Source Host           : localhost:3306
Source Database       : stationdatabase

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2018-11-02 14:24:57
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `measuredatainfo`
-- ----------------------------
DROP TABLE IF EXISTS `measuredatainfo`;
CREATE TABLE `measuredatainfo` (
  `MeasureDataID` int(255) unsigned NOT NULL AUTO_INCREMENT,
  `MeasureData` int(255) NOT NULL,
  `ReceivedTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `MeasurePointID` int(255) NOT NULL,
  `IsDeleted` tinyint(1) unsigned zerofill DEFAULT '0',
  PRIMARY KEY (`MeasureDataID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of measuredatainfo
-- ----------------------------

-- ----------------------------
-- Table structure for `measurepointinfo`
-- ----------------------------
DROP TABLE IF EXISTS `measurepointinfo`;
CREATE TABLE `measurepointinfo` (
  `MeasurePointID` int(255) unsigned NOT NULL AUTO_INCREMENT,
  `MeasurePointNo` int(255) DEFAULT NULL,
  `MeasurePointName` varchar(255) DEFAULT NULL,
  `Parameter` float(255,2) DEFAULT NULL,
  `MonitorID` int(255) NOT NULL,
  `IsDeleted` tinyint(1) unsigned zerofill NOT NULL DEFAULT '0',
  PRIMARY KEY (`MeasurePointID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of measurepointinfo
-- ----------------------------

-- ----------------------------
-- Table structure for `monitorinfo`
-- ----------------------------
DROP TABLE IF EXISTS `monitorinfo`;
CREATE TABLE `monitorinfo` (
  `MonitorID` int(255) unsigned NOT NULL AUTO_INCREMENT,
  `MonitorName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `MonitorPosition` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`MonitorID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of monitorinfo
-- ----------------------------
