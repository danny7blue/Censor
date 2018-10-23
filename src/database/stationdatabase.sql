/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 80012
Source Host           : localhost:3306
Source Database       : stationdatabase

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2018-10-22 19:20:33
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
INSERT INTO `monitorinfo` VALUES ('2', 'M2', '成都');
INSERT INTO `monitorinfo` VALUES ('3', 'M3', '西安');

-- ----------------------------
-- Table structure for `testdatainfo`
-- ----------------------------
DROP TABLE IF EXISTS `testdatainfo`;
CREATE TABLE `testdatainfo` (
  `ID` int(255) unsigned NOT NULL AUTO_INCREMENT,
  `TestID` int(255) DEFAULT NULL,
  `TestData` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `Time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of testdatainfo
-- ----------------------------
INSERT INTO `testdatainfo` VALUES ('1', '1', '300', '2018-10-21 18:41:59');
INSERT INTO `testdatainfo` VALUES ('2', '2', '200', '2018-10-21 18:42:08');
INSERT INTO `testdatainfo` VALUES ('3', '3', '300', '2018-10-21 18:42:14');
INSERT INTO `testdatainfo` VALUES ('4', '4', '400', '2018-10-21 18:42:21');
INSERT INTO `testdatainfo` VALUES ('5', '1', '360', '2018-10-21 18:42:30');
INSERT INTO `testdatainfo` VALUES ('6', '2', '450', '2018-10-21 18:42:38');
INSERT INTO `testdatainfo` VALUES ('7', '3', '550', '2018-10-21 18:42:49');
INSERT INTO `testdatainfo` VALUES ('8', '4', '660', '2018-10-21 18:43:00');
INSERT INTO `testdatainfo` VALUES ('9', '1', '300', '2018-10-22 18:52:53');
INSERT INTO `testdatainfo` VALUES ('10', '2', '600', '2018-10-21 18:53:43');
INSERT INTO `testdatainfo` VALUES ('11', '3', '900', '2018-10-23 18:54:04');
INSERT INTO `testdatainfo` VALUES ('12', '4', '500', '2018-10-20 18:54:34');
INSERT INTO `testdatainfo` VALUES ('13', '1', '100', '2018-10-21 20:15:36');
INSERT INTO `testdatainfo` VALUES ('14', '2', '200', '2018-10-21 20:15:36');
INSERT INTO `testdatainfo` VALUES ('15', '3', '300', '2018-10-21 20:15:36');
INSERT INTO `testdatainfo` VALUES ('16', '4', '400', '2018-10-21 20:15:37');

-- ----------------------------
-- Table structure for `testinfo`
-- ----------------------------
DROP TABLE IF EXISTS `testinfo`;
CREATE TABLE `testinfo` (
  `TestID` int(255) unsigned NOT NULL,
  `TestName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TestMonitorID` int(255) NOT NULL,
  PRIMARY KEY (`TestID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of testinfo
-- ----------------------------
INSERT INTO `testinfo` VALUES ('1', 'T1', '1');
INSERT INTO `testinfo` VALUES ('2', 'T2', '1');
INSERT INTO `testinfo` VALUES ('3', 'T1', '2');
INSERT INTO `testinfo` VALUES ('4', 'T2', '2');
