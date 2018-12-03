/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : project

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2018-12-04 06:31:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for day_video_access_topn_stat
-- ----------------------------
DROP TABLE IF EXISTS `day_video_access_topn_stat`;
CREATE TABLE `day_video_access_topn_stat` (
  `day` varchar(8) NOT NULL,
  `cms_id` bigint(10) NOT NULL,
  `times` bigint(10) NOT NULL,
  PRIMARY KEY (`day`,`cms_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for day_video_city_access_topn_stat
-- ----------------------------
DROP TABLE IF EXISTS `day_video_city_access_topn_stat`;
CREATE TABLE `day_video_city_access_topn_stat` (
  `day` varchar(8) NOT NULL,
  `cms_id` bigint(10) NOT NULL,
  `city` varchar(20) NOT NULL,
  `times` bigint(10) NOT NULL,
  `times_rank` int(11) NOT NULL,
  PRIMARY KEY (`day`,`cms_id`,`city`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for day_video_traffics_topn_stat
-- ----------------------------
DROP TABLE IF EXISTS `day_video_traffics_topn_stat`;
CREATE TABLE `day_video_traffics_topn_stat` (
  `day` varchar(8) NOT NULL,
  `cms_id` bigint(10) NOT NULL,
  `traffics` bigint(20) NOT NULL,
  PRIMARY KEY (`day`,`cms_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
