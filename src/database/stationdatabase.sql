/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 80012
Source Host           : localhost:3306
Source Database       : stationdatabase

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2018-10-29 15:05:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `monitorinfo`
-- ----------------------------
DROP TABLE IF EXISTS `monitorinfo`;
CREATE TABLE `monitorinfo` (
  `MonitorID` int(255) NOT NULL,
  `MonitorName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `MonitorPosition` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`MonitorID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of monitorinfo
-- ----------------------------
INSERT INTO `monitorinfo` VALUES ('1', 'M1', '贵州');
INSERT INTO `monitorinfo` VALUES ('2', 'M2', '西安');
INSERT INTO `monitorinfo` VALUES ('3', 'M3', '成都');
INSERT INTO `monitorinfo` VALUES ('4', 'M4', '重庆');

-- ----------------------------
-- Table structure for `testdatainfo`
-- ----------------------------
DROP TABLE IF EXISTS `testdatainfo`;
CREATE TABLE `testdatainfo` (
  `ID` int(255) unsigned NOT NULL AUTO_INCREMENT,
  `TestID` int(255) DEFAULT NULL,
  `TestData` int(255) DEFAULT NULL,
  `Time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of testdatainfo
-- ----------------------------
INSERT INTO `testdatainfo` VALUES ('1', '1', '100', '2018-10-29 15:03:33');
INSERT INTO `testdatainfo` VALUES ('2', '2', '200', '2018-10-29 15:03:42');
INSERT INTO `testdatainfo` VALUES ('3', '3', '300', '2018-10-29 15:03:49');
INSERT INTO `testdatainfo` VALUES ('4', '4', '400', '2018-10-29 15:03:56');
INSERT INTO `testdatainfo` VALUES ('5', '5', '500', '2018-10-29 15:04:02');
INSERT INTO `testdatainfo` VALUES ('6', '6', '600', '2018-10-29 15:04:09');
INSERT INTO `testdatainfo` VALUES ('7', '7', '700', '2018-10-29 15:04:15');
INSERT INTO `testdatainfo` VALUES ('8', '8', '800', '2018-10-29 15:04:21');

-- ----------------------------
-- Table structure for `testinfo`
-- ----------------------------
DROP TABLE IF EXISTS `testinfo`;
CREATE TABLE `testinfo` (
  `TestID` int(255) unsigned NOT NULL,
  `TestName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `MonitorID` int(255) NOT NULL,
  PRIMARY KEY (`TestID`),
  KEY `MonitorID` (`MonitorID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of testinfo
-- ----------------------------
INSERT INTO `testinfo` VALUES ('1', 'T1', '1');
INSERT INTO `testinfo` VALUES ('2', 'T2', '1');
INSERT INTO `testinfo` VALUES ('3', 'T1', '2');
INSERT INTO `testinfo` VALUES ('4', 'T2', '2');
INSERT INTO `testinfo` VALUES ('5', 'T1', '3');
INSERT INTO `testinfo` VALUES ('6', 'T2', '3');
INSERT INTO `testinfo` VALUES ('7', 'T1', '4');
INSERT INTO `testinfo` VALUES ('8', 'T2', '4');
