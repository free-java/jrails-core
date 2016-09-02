/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Version : 50625
 Source Host           : localhost
 Source Database       : core_test

 Target Server Version : 50625
 File Encoding         : utf-8

 Date: 09/01/2016 16:31:27 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `account`
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `id` char(16) NOT NULL,
  `name` varchar(36) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `age` int(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
