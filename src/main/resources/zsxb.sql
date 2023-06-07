/*
 Navicat Premium Data Transfer

 Source Server         : new_huawei
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : 
 Source Schema         : zsxb

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 07/06/2023 19:41:12
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer`  (
  `cus_id` int NOT NULL AUTO_INCREMENT,
  `cus_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '顾客姓名',
  `cus_gender` smallint NOT NULL DEFAULT 1 COMMENT '说明：\r\n            0 女\r\n            1 男',
  `cus_telnum` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '顾客电话',
  `cus_email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '顾客邮箱',
  `cus_uid` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '顾客用户名',
  `cus_pwd` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '登陆密码',
  `is_delete` smallint NULL DEFAULT 0 COMMENT '是否被删除（1删除，0未删除）',
  `cus_balance` decimal(10, 0) NOT NULL DEFAULT 100 COMMENT '账户余额',
  `cus_paypwd` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '支付密码',
  PRIMARY KEY (`cus_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee`  (
  `emp_id` int NOT NULL AUTO_INCREMENT,
  `emp_uid` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '雇员用户名',
  `emp_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '雇员姓名',
  `emp_gender` smallint NULL DEFAULT 1 COMMENT '说明：\r\n            0：女\r\n            1：男',
  `emp_telnum` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '雇员手机号',
  `emp_email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '雇员邮箱',
  `emp_pwd` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '雇员登录密码',
  `is_delete` smallint NULL DEFAULT 0 COMMENT '是否被删除（1删除 0未删除）',
  `type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '雇员类型',
  PRIMARY KEY (`emp_id`) USING BTREE,
  UNIQUE INDEX `uk_emp_uid`(`emp_uid` ASC) USING BTREE COMMENT '用户名唯一索引'
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for play
-- ----------------------------
DROP TABLE IF EXISTS `play`;
CREATE TABLE `play`  (
  `play_id` int NOT NULL AUTO_INCREMENT,
  `play_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '剧目名称',
  `play_introduction` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '剧目简介',
  `play_image` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT 'https://zsxb-ttms.oss-cn-shanghai.aliyuncs.com/play/default.jpg' COMMENT '剧目图片（路径）',
  `play_video` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '剧目媒体（路径）',
  `play_length` int NULL DEFAULT NULL COMMENT '剧目时长（分钟）',
  `play_ticket_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '剧目票价',
  `play_status` smallint NULL DEFAULT 0 COMMENT '取值含义：\r\n            0：待安排演出\r\n            1：已安排演出\r\n            -1：下线',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '剧目类型',
  `language` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '剧目语言',
  PRIMARY KEY (`play_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for sale
-- ----------------------------
DROP TABLE IF EXISTS `sale`;
CREATE TABLE `sale`  (
  `sale_id` bigint NOT NULL AUTO_INCREMENT,
  `emp_id` int NULL DEFAULT NULL COMMENT '雇员id',
  `cus_id` int NULL DEFAULT NULL COMMENT '顾客id',
  `sale_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '销售时间',
  `sale_payment` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '支付金额',
  `sale_change` decimal(10, 2) NULL DEFAULT NULL COMMENT '找零',
  `sale_type` smallint NULL DEFAULT NULL COMMENT '类别取值含义：\r\n            1：销售单\r\n            -1：退款单',
  `sale_status` smallint NULL DEFAULT NULL COMMENT '销售单状态如下：\r\n            0：代付款\r\n            1：已付款',
  `sale_sort` smallint NULL DEFAULT NULL COMMENT '取值说明：\r\n            1：顾客自购/退票\r\n            0：售票员销售/退票',
  `play_id` int NULL DEFAULT NULL COMMENT '剧目id',
  `ticket_id` int NULL DEFAULT 0 COMMENT '演出票id',
  PRIMARY KEY (`sale_id`) USING BTREE,
  INDEX `FK_customer_sale`(`cus_id` ASC) USING BTREE,
  INDEX `FK_employee_sale`(`emp_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = COMPACT;

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
  `is_delete` smallint NOT NULL DEFAULT 0 COMMENT '是否删除（1删除 0未删除）',
  `emp_id` int NULL DEFAULT NULL COMMENT '创建演出计划的管理员id',
  PRIMARY KEY (`sched_id`) USING BTREE,
  INDEX `FK_play_sched`(`play_id` ASC) USING BTREE,
  INDEX `FK_studio_sched`(`studio_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for seat
-- ----------------------------
DROP TABLE IF EXISTS `seat`;
CREATE TABLE `seat`  (
  `seat_id` int NOT NULL AUTO_INCREMENT,
  `studio_id` int NULL DEFAULT NULL COMMENT '演出厅id',
  `seat_row` int NULL DEFAULT NULL COMMENT '座位行号',
  `seat_column` int NULL DEFAULT NULL COMMENT '座位列号',
  PRIMARY KEY (`seat_id`) USING BTREE,
  INDEX `FK_studio_seat`(`studio_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1521 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = COMPACT;

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
  `is_delete` smallint NULL DEFAULT 0 COMMENT '是否被删除（1删除 0未删除）',
  PRIMARY KEY (`studio_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for ticket
-- ----------------------------
DROP TABLE IF EXISTS `ticket`;
CREATE TABLE `ticket`  (
  `ticket_id` bigint NOT NULL AUTO_INCREMENT,
  `seat_id` int NULL DEFAULT NULL,
  `sched_id` int NULL DEFAULT NULL,
  `ticket_price` decimal(10, 2) NULL DEFAULT NULL,
  `ticket_status` smallint NULL DEFAULT NULL COMMENT '含义如下：\r\n            0：待销售\r\n            1：锁定\r\n            9：卖出',
  `ticket_locktime` timestamp NULL DEFAULT NULL COMMENT '加锁时间(下单后加锁)',
  PRIMARY KEY (`ticket_id`) USING BTREE,
  INDEX `FK_sched_ticket`(`sched_id` ASC) USING BTREE,
  INDEX `FK_seat_ticket`(`seat_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1478 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = COMPACT;

SET FOREIGN_KEY_CHECKS = 1;
