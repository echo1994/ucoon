/*
Navicat MySQL Data Transfer

Source Server         : localmysql
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : ucoon

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2016-08-30 09:01:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `admin_id` int(11) NOT NULL AUTO_INCREMENT,
  `admin_name` varchar(255) NOT NULL,
  `admin_psw` varchar(255) NOT NULL,
  `admin_group_id` int(11) NOT NULL,
  PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------

-- ----------------------------
-- Table structure for `admin_group`
-- ----------------------------
DROP TABLE IF EXISTS `admin_group`;
CREATE TABLE `admin_group` (
  `group_id` int(11) NOT NULL AUTO_INCREMENT,
  `group_name` varchar(255) NOT NULL,
  `group_rights` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin_group
-- ----------------------------

-- ----------------------------
-- Table structure for `apply`
-- ----------------------------
DROP TABLE IF EXISTS `apply`;
CREATE TABLE `apply` (
  `apply_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户的id',
  `mission_id` int(11) NOT NULL COMMENT '订单的id',
  `take_time` datetime NOT NULL,
  `take_state` int(11) NOT NULL COMMENT '接单状态（详细查看需求说明书）',
  `note` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`apply_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of apply
-- ----------------------------
INSERT INTO `apply` VALUES ('3', '1', '9', '2016-08-25 22:20:53', '1', null);
INSERT INTO `apply` VALUES ('4', '1', '8', '2016-08-25 22:42:01', '1', null);
INSERT INTO `apply` VALUES ('5', '1', '10', '2016-08-29 15:31:28', '1', null);

-- ----------------------------
-- Table structure for `comment`
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `comment_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `mission_id` int(11) NOT NULL,
  `content` varchar(255) NOT NULL,
  `comment_time` datetime NOT NULL,
  PRIMARY KEY (`comment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comment
-- ----------------------------

-- ----------------------------
-- Table structure for `comment_child`
-- ----------------------------
DROP TABLE IF EXISTS `comment_child`;
CREATE TABLE `comment_child` (
  `comment_child_id` int(11) NOT NULL AUTO_INCREMENT,
  `comment_id` int(11) NOT NULL,
  `fromuser_id` int(11) NOT NULL,
  `touser_id` int(11) NOT NULL,
  `content` varchar(255) NOT NULL,
  `comment_time` datetime NOT NULL,
  PRIMARY KEY (`comment_child_id`),
  KEY `commentid` (`comment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comment_child
-- ----------------------------

-- ----------------------------
-- Table structure for `evaluate`
-- ----------------------------
DROP TABLE IF EXISTS `evaluate`;
CREATE TABLE `evaluate` (
  `mission_id` int(11) NOT NULL COMMENT '任务id',
  `publish_id` int(11) NOT NULL COMMENT '发布者id',
  `executor_id` int(11) NOT NULL COMMENT '执行者id',
  `publish_evaluate` varchar(255) DEFAULT NULL COMMENT '发布对执行评价',
  `executor_evaluate` varchar(255) DEFAULT NULL COMMENT '执行对发布评价',
  `publish_score` float DEFAULT NULL COMMENT '发布对执行评分',
  `executor_score` float DEFAULT NULL COMMENT '执行对发布评分',
  `epevaluate_time` datetime DEFAULT NULL,
  `peevaluate_time` datetime DEFAULT NULL COMMENT '评价时间',
  PRIMARY KEY (`mission_id`,`publish_id`,`executor_id`),
  KEY `missionid` (`mission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of evaluate
-- ----------------------------

-- ----------------------------
-- Table structure for `messages`
-- ----------------------------
DROP TABLE IF EXISTS `messages`;
CREATE TABLE `messages` (
  `message_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '消息id',
  `message_detail` varchar(255) NOT NULL COMMENT '消息内容',
  `message_status` int(11) NOT NULL COMMENT '接收状态：默认为0  1表示已接受',
  `psot_time` datetime NOT NULL COMMENT '发送时间',
  `message_type` int(11) NOT NULL COMMENT '消息类型ID ：文字、语音、图片',
  `from_user_id` int(11) NOT NULL COMMENT '发送者ID',
  `to_user_id` int(11) DEFAULT NULL COMMENT '接收者ID',
  PRIMARY KEY (`message_id`),
  KEY `typeid` (`message_type`),
  CONSTRAINT `typeid` FOREIGN KEY (`message_type`) REFERENCES `messages_type` (`message_type_id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of messages
-- ----------------------------

-- ----------------------------
-- Table structure for `messages_type`
-- ----------------------------
DROP TABLE IF EXISTS `messages_type`;
CREATE TABLE `messages_type` (
  `message_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `message_type_name` varchar(255) NOT NULL COMMENT '类型的名称',
  PRIMARY KEY (`message_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of messages_type
-- ----------------------------

-- ----------------------------
-- Table structure for `mission`
-- ----------------------------
DROP TABLE IF EXISTS `mission`;
CREATE TABLE `mission` (
  `mission_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '任务id',
  `mission_type_id` int(11) DEFAULT NULL,
  `mission_title` varchar(100) NOT NULL COMMENT '任务的标题',
  `mission_describe` varchar(255) DEFAULT NULL COMMENT '任务的描述',
  `pictures` varchar(255) DEFAULT NULL COMMENT '照片（1~5张），可为空',
  `people_count` int(11) NOT NULL COMMENT '执行任务的人数',
  `place` varchar(255) NOT NULL COMMENT '执行任务的地点',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime NOT NULL COMMENT '结束时间',
  `publish_time` datetime NOT NULL COMMENT '发布时间',
  `user_id` int(11) NOT NULL COMMENT '发布人用户id',
  `view_count` int(11) NOT NULL DEFAULT '0' COMMENT '浏览次数，根据点击量来计算',
  `mission_price` int(11) NOT NULL,
  `telephone` varchar(255) NOT NULL,
  `mission_status` int(11) NOT NULL,
  `pic_count` int(11) DEFAULT NULL,
  PRIMARY KEY (`mission_id`),
  KEY `missiontypeid` (`mission_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mission
-- ----------------------------
INSERT INTO `mission` VALUES ('8', null, '拿快递', '到月明楼二楼拿快递，手机号110', '114721159084310635cc3ecfa1414c8f6de64cee36e07a', '1', '厦门市集美区月明楼', '2016-08-27 16:02:25', '2016-08-26 17:00:00', '2016-08-25 17:05:08', '1', '0', '9', '18120783578', '2', '4');
INSERT INTO `mission` VALUES ('9', null, '买饭', '到孙厝买5块钱的福鼎肉片', '114721166085129003ecd5702c468da40159f4d5f1f862', '2', '厦门市集美区集美大学-第五社区学生公寓5号楼', '2016-08-25 19:00:00', '2016-08-25 21:00:00', '2016-08-25 17:16:49', '1', '0', '7', '18120783578', '2', '4');
INSERT INTO `mission` VALUES ('10', null, '另', '伟大撒旦撒旦撒', '11472455870971826cd38e1d9c49c7a9c906e74b41be06', '2', '福建省厦门市集美区塘埔路125-10号', '2016-08-29 10:10:00', '2016-08-31 10:10:00', '2016-08-29 15:31:11', '1', '0', '32', '13074852391', '2', '2');
INSERT INTO `mission` VALUES ('11', null, '领料地', '饿1321', '11472487556152965456f9ba2042c4bc89a270a81a51c7', '1', '福建省厦门市集美区银江路辅路', '2016-08-09 10:10:00', '2016-08-11 10:10:00', '2016-08-30 00:19:16', '1', '0', '1211', '13074852391', '1', '1');
INSERT INTO `mission` VALUES ('12', null, '领料地', '饿1321', '11472487614240aa59c4b59d834cd59ca18037a73f7d55', '1', '福建省厦门市集美区银江路辅路', '2016-08-09 10:10:00', '2016-08-11 10:10:00', '2016-08-30 00:20:14', '1', '0', '1211', '13074852391', '1', '1');
INSERT INTO `mission` VALUES ('13', null, '领料地', '饿1321', '114724876424035a834f1143b24a32a10e48ce4d338cc6', '1', '福建省厦门市集美区银江路辅路', '2016-08-09 10:10:00', '2016-08-11 10:10:00', '2016-08-30 00:20:42', '1', '0', '1211', '13074852391', '1', '1');
INSERT INTO `mission` VALUES ('14', null, '3213', '3213', '11472487815923c901a7c53908477bb46084b23c633659', '1', '福建省厦门市集美区塘埔路125-10号', '2016-08-08 10:10:00', '2016-08-10 10:10:00', '2016-08-30 00:23:36', '1', '0', '213', '13074852391', '1', '1');

-- ----------------------------
-- Table structure for `mission_type`
-- ----------------------------
DROP TABLE IF EXISTS `mission_type`;
CREATE TABLE `mission_type` (
  `mission_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `mission_type_name` varchar(255) NOT NULL COMMENT '任务类型名称',
  PRIMARY KEY (`mission_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mission_type
-- ----------------------------
INSERT INTO `mission_type` VALUES ('1', '全部2');

-- ----------------------------
-- Table structure for `orders`
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `order_num` varchar(255) NOT NULL COMMENT '订单号  唯一。规则：年月日时分秒+随机码(1)+用户id+随机码(1)',
  `user_id` int(11) NOT NULL COMMENT '用户的id',
  `mission_id` int(11) NOT NULL COMMENT '任务的id',
  `order_time` datetime NOT NULL,
  `finish_time` datetime DEFAULT NULL COMMENT '任务完成的时间',
  `state` int(11) NOT NULL COMMENT '订单状态（详细查看需求说明书）',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES ('3', '201608271015418019', '1', '8', '2016-08-27 10:15:41', null, '0');
INSERT INTO `orders` VALUES ('4', '201608291526389817', '1', '9', '2016-08-29 15:26:38', null, '0');
INSERT INTO `orders` VALUES ('5', '2016082915314210517', '1', '10', '2016-08-29 15:31:43', null, '0');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `open_id` varchar(255) NOT NULL COMMENT '唯一,用户微信公众号识别码',
  `nick_name` varchar(50) NOT NULL COMMENT '微信授权获取的微信名',
  `weixin_id` varchar(255) DEFAULT NULL COMMENT '用户自己可填写的微信号',
  `sex` int(11) NOT NULL COMMENT '微信授权获取的性别',
  `signature` varchar(100) DEFAULT NULL COMMENT '用户的个性签名',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  `phone` varchar(40) DEFAULT NULL COMMENT '用户绑定的手机',
  `name` varchar(20) DEFAULT NULL COMMENT '真实姓名',
  `head_img_url` varchar(255) NOT NULL COMMENT '微信授权获取的头像',
  `intro` varchar(300) DEFAULT NULL COMMENT '用户的个人简介',
  `credit` float NOT NULL DEFAULT '0' COMMENT '根据评价分数的平均的分来展示，默认为0',
  `regist_time` datetime NOT NULL COMMENT '注册时间',
  `country` varchar(50) NOT NULL COMMENT '微信第一次授权的时间',
  `province` varchar(50) NOT NULL COMMENT '微信授权获取的省份',
  `city` varchar(50) NOT NULL COMMENT '微信授权获取的城市',
  `state` int(11) NOT NULL COMMENT '用户的登陆状态0代表未登录 1代表登陆',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '1', '1', '1', '2', '1', '2016-08-08 16:26:44', '1', '1', '1', '1', '1', '2016-08-08 16:26:52', '1', '1', '1', '1');
INSERT INTO `user` VALUES ('2', '2', '2', '2', '2', '2', '2016-08-08 17:05:01', '2', '2', '2', '2', '2', '2016-08-08 17:04:59', '2', '2', '2', '2');
