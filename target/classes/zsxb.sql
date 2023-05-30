/*
 Navicat Premium Data Transfer

 Source Server         : huawei
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : 121.37.155.73:3306
 Source Schema         : zsxb

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 10/05/2023 08:04:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer`  (
  `cus_id` int NOT NULL AUTO_INCREMENT,
  `cus_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '顾客姓名',
  `cus_gender` smallint NULL DEFAULT 1 COMMENT '说明：\r\n            0 女\r\n            1 男',
  `cus_telnum` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '顾客电话',
  `cus_email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '顾客邮箱',
  `cus_uid` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '顾客用户名',
  `cus_pwd` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '登陆密码',
  `cus_status` smallint NULL DEFAULT 1 COMMENT '说明：\r\n            0：禁用\r\n            1：启用',
  `cus_balance` decimal(10, 0) NULL DEFAULT 1000 COMMENT '账户余额',
  `cus_paypwd` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '支付密码',
  PRIMARY KEY (`cus_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of customer
-- ----------------------------

-- ----------------------------
-- Table structure for data_dict
-- ----------------------------
DROP TABLE IF EXISTS `data_dict`;
CREATE TABLE `data_dict`  (
  `dict_id` int NOT NULL AUTO_INCREMENT,
  `super_dict_id` int NULL DEFAULT NULL COMMENT '父id',
  `dict_index` int NULL DEFAULT NULL COMMENT '同级顺序',
  `dict_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '数据字典名称',
  `dict_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '数据字典值',
  PRIMARY KEY (`dict_id`) USING BTREE,
  INDEX `FK_super_child_dict`(`super_dict_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of data_dict
-- ----------------------------
INSERT INTO `data_dict` VALUES (1, NULL, 1, '数据字典', '根');
INSERT INTO `data_dict` VALUES (2, 1, 1, 'PLAYTYPE', '影片类型');
INSERT INTO `data_dict` VALUES (3, 1, 2, 'PLAYLANG', '影片语言');
INSERT INTO `data_dict` VALUES (4, 1, 3, '锁失效时间', '10');
INSERT INTO `data_dict` VALUES (5, 2, 1, 'History', '历史');
INSERT INTO `data_dict` VALUES (6, 2, 2, 'Anime', '动漫');
INSERT INTO `data_dict` VALUES (7, 2, 3, 'Drama', '生活');
INSERT INTO `data_dict` VALUES (8, 2, 4, 'Horror', '恐怖');
INSERT INTO `data_dict` VALUES (9, 2, 5, 'War', '战争');
INSERT INTO `data_dict` VALUES (10, 2, 6, 'Sci-Fi', '科幻');
INSERT INTO `data_dict` VALUES (11, 2, 7, 'Romance', '爱情');
INSERT INTO `data_dict` VALUES (12, 2, 8, 'Comedy', '喜剧');
INSERT INTO `data_dict` VALUES (13, 2, 9, 'Action', '动作');
INSERT INTO `data_dict` VALUES (14, 3, 1, 'Chinese', '国语');
INSERT INTO `data_dict` VALUES (15, 3, 2, 'English', '英语');
INSERT INTO `data_dict` VALUES (16, 3, 3, 'Japanese', '日语');
INSERT INTO `data_dict` VALUES (17, 3, 4, 'Korean', '韩语');

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee`  (
  `emp_id` int NOT NULL AUTO_INCREMENT,
  `emp_no` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '雇员编号',
  `emp_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '雇员姓名',
  `emp_gender` smallint NULL DEFAULT 1 COMMENT '说明：\r\n            0：女\r\n            1：男',
  `emp_telnum` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '雇员手机号',
  `emp_email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '雇员邮箱',
  `emp_pwd` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '雇员登录密码',
  `emp_status` smallint NULL DEFAULT 1 COMMENT '说明：\r\n            0：禁用\r\n            1：启用',
  PRIMARY KEY (`emp_id`) USING BTREE,
  INDEX `FK_emp_position`(`dict_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of employee
-- ----------------------------

-- ----------------------------
-- Table structure for play
-- ----------------------------
DROP TABLE IF EXISTS `play`;
CREATE TABLE `play`  (
  `play_id` int NOT NULL AUTO_INCREMENT,
  `dict_type_id` int NULL DEFAULT NULL COMMENT '剧目类型id',
  `dict_lang_id` int NULL DEFAULT NULL COMMENT '剧目语言id',
  `play_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '剧目名称',
  `play_introduction` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '剧目简介',
  `play_image` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '剧目图片（路径）',
  `play_video` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '剧目媒体（路径）',
  `play_length` int NULL DEFAULT NULL COMMENT '剧目时长',
  `play_ticket_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '剧目票价',
  `play_status` smallint NULL DEFAULT NULL COMMENT '取值含义：\r\n            0：待安排演出\r\n            1：已安排演出\r\n            -1：下线',
  PRIMARY KEY (`play_id`) USING BTREE,
  INDEX `FK_dict_lan_play`(`dict_lang_id` ASC) USING BTREE,
  INDEX `FK_dict_type_play`(`dict_type_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of play
-- ----------------------------

-- ----------------------------
-- Table structure for resource
-- ----------------------------
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource`  (
  `res_id` int NOT NULL AUTO_INCREMENT,
  `res_parent` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '父资源id',
  `res_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '资源名称',
  `res_URL` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '资源url',
  PRIMARY KEY (`res_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of resource
-- ----------------------------

-- ----------------------------
-- Table structure for role_resource
-- ----------------------------
DROP TABLE IF EXISTS `role_resource`;
CREATE TABLE `role_resource`  (
  `role_res_id` int NOT NULL AUTO_INCREMENT,
  `role_id` int NULL DEFAULT NULL COMMENT '角色id',
  `res_id` int NULL DEFAULT NULL COMMENT '资源id',
  PRIMARY KEY (`role_res_id`) USING BTREE,
  INDEX `FK_res_role`(`res_id` ASC) USING BTREE,
  INDEX `FK_role_resource`(`role_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of role_resource
-- ----------------------------

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`  (
  `role_id` int NOT NULL AUTO_INCREMENT,
  `role_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '角色名称',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of roles
-- ----------------------------

-- ----------------------------
-- Table structure for sale
-- ----------------------------
DROP TABLE IF EXISTS `sale`;
CREATE TABLE `sale`  (
  `sale_id` bigint NOT NULL AUTO_INCREMENT,
  `emp_id` int NULL DEFAULT NULL COMMENT '雇员id',
  `cus_id` int NULL DEFAULT NULL COMMENT '顾客id',
  `sale_time` datetime NULL DEFAULT NULL COMMENT '销售时间',
  `sale_payment` decimal(10, 2) NULL DEFAULT NULL COMMENT '支付金额',
  `sale_change` decimal(10, 2) NULL DEFAULT NULL COMMENT '找零',
  `sale_type` smallint NULL DEFAULT NULL COMMENT '类别取值含义：\r\n            1：销售单\r\n            -1：退款单',
  `sale_status` smallint NULL DEFAULT NULL COMMENT '销售单状态如下：\r\n            0：代付款\r\n            1：已付款',
  `sale_sort` smallint NULL DEFAULT NULL COMMENT '取值说明：\r\n            1：顾客自购/退票\r\n            0：售票员销售/退票',
  PRIMARY KEY (`sale_id`) USING BTREE,
  INDEX `FK_customer_sale`(`cus_id` ASC) USING BTREE,
  INDEX `FK_employee_sale`(`emp_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of sale
-- ----------------------------

-- ----------------------------
-- Table structure for sale_item
-- ----------------------------
DROP TABLE IF EXISTS `sale_item`;
CREATE TABLE `sale_item`  (
  `sale_item_id` bigint NOT NULL AUTO_INCREMENT,
  `ticket_id` bigint NULL DEFAULT NULL COMMENT '票id',
  `sale_id` bigint NULL DEFAULT NULL COMMENT '销售单id',
  `sale_item_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '售价',
  PRIMARY KEY (`sale_item_id`) USING BTREE,
  INDEX `FK_sale_sale_item`(`sale_id` ASC) USING BTREE,
  INDEX `FK_ticket_sale_item`(`ticket_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of sale_item
-- ----------------------------

-- ----------------------------
-- Table structure for schedule
-- ----------------------------
DROP TABLE IF EXISTS `schedule`;
CREATE TABLE `schedule`  (
  `sched_id` int NOT NULL AUTO_INCREMENT,
  `studio_id` int NULL DEFAULT NULL COMMENT '演出厅id',
  `play_id` int NULL DEFAULT NULL COMMENT '剧目id',
  `sched_time` datetime NOT NULL COMMENT '演出时间',
  `sched_ticket_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '票价',
  `sched_status` smallint NULL DEFAULT NULL COMMENT '演出计划状态	0：编辑(默认)	1：执行	-1：取消',
  PRIMARY KEY (`sched_id`) USING BTREE,
  INDEX `FK_play_sched`(`play_id` ASC) USING BTREE,
  INDEX `FK_studio_sched`(`studio_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of schedule
-- ----------------------------

-- ----------------------------
-- Table structure for seat
-- ----------------------------
DROP TABLE IF EXISTS `seat`;
CREATE TABLE `seat`  (
  `seat_id` int NOT NULL AUTO_INCREMENT,
  `studio_id` int NULL DEFAULT NULL COMMENT '演出厅id',
  `seat_row` int NULL DEFAULT NULL COMMENT '座位行号',
  `seat_column` int NULL DEFAULT NULL COMMENT '座位列号',
  `seat_status` smallint NULL DEFAULT NULL COMMENT '座位状态	1：有效(默认)	2：过道	 0：损坏',
  PRIMARY KEY (`seat_id`) USING BTREE,
  INDEX `FK_studio_seat`(`studio_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of seat
-- ----------------------------

-- ----------------------------
-- Table structure for studio
-- ----------------------------
DROP TABLE IF EXISTS `studio`;
CREATE TABLE `studio`  (
  `studio_id` int NOT NULL AUTO_INCREMENT,
  `studio_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '演出厅名称',
  `studio_row_count` int NULL DEFAULT NULL COMMENT '座位行数',
  `studio_col_count` int NULL DEFAULT NULL COMMENT '座位列数',
  `studio_introduction` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '演出厅简介',
  `studio_status` smallint NULL DEFAULT 1 COMMENT '说明：\r\n   0：禁用\r\n   1：启用',
  PRIMARY KEY (`studio_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of studio
-- ----------------------------
INSERT INTO `studio` VALUES (1, '1#号厅', 8, 8, '1号厅', 1);
INSERT INTO `studio` VALUES (2, '激光MAX厅', 8, 8, '激光MAX厅', 1);
INSERT INTO `studio` VALUES (3, '全景声MAX厅', 10, 10, '全景声MAX厅', 1);
INSERT INTO `studio` VALUES (4, 'VIP厅', 8, 8, 'VIP厅', 1);
INSERT INTO `studio` VALUES (5, '杜比厅', 9, 9, '杜比厅', 1);

-- ----------------------------
-- Table structure for ticket
-- ----------------------------
DROP TABLE IF EXISTS `ticket`;
CREATE TABLE `ticket`  (
  `ticket_id` bigint NOT NULL AUTO_INCREMENT,
  `seat_id` int NULL DEFAULT NULL COMMENT '座位id',
  `sched_id` int NULL DEFAULT NULL COMMENT '演出计划id',
  `ticket_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '票价',
  `ticket_status` smallint NULL DEFAULT NULL COMMENT '含义如下：\r\n            0：待销售\r\n            1：锁定\r\n            9：卖出',
  `ticket_locktime` timestamp NULL DEFAULT NULL COMMENT '加锁时间(下单后加锁)',
  PRIMARY KEY (`ticket_id`) USING BTREE,
  INDEX `FK_sched_ticket`(`sched_id` ASC) USING BTREE,
  INDEX `FK_seat_ticket`(`seat_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of ticket
-- ----------------------------

-- ----------------------------
-- Table structure for usr_role
-- ----------------------------
DROP TABLE IF EXISTS `usr_role`;
CREATE TABLE `usr_role`  (
  `usr_role_id` int NOT NULL AUTO_INCREMENT,
  `emp_id` int NULL DEFAULT NULL COMMENT '雇员id',
  `role_id` int NULL DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`usr_role_id`) USING BTREE,
  INDEX `FK_role_user`(`role_id` ASC) USING BTREE,
  INDEX `FK_user_role`(`emp_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of usr_role
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
