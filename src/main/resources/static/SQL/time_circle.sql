/*
 Navicat Premium Data Transfer

 Source Server         : MySQLBaidu
 Source Server Type    : MySQL
 Source Server Version : 50727
 Source Host           : 106.13.199.128:3306
 Source Schema         : time_circle

 Target Server Type    : MySQL
 Target Server Version : 50727
 File Encoding         : 65001

 Date: 18/10/2019 22:02:11
*/

DROP DATABASE IF EXISTS 'time_circle';
CREATE DATABASE 'time_circle';

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for future_tasks
-- ----------------------------
DROP TABLE IF EXISTS `future_tasks`;
CREATE TABLE `future_tasks`  (
  `id` bigint(255) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '未来任务id',
  `implement` datetime(6) NOT NULL COMMENT '任务执行时间',
  `type` tinyint(1) NULL DEFAULT NULL COMMENT '任务类型（0：记录 1：好友 3：外部地址）',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务执行发布地址（外部地址、好友id、本地记录）',
  `status` tinyint(1) NOT NULL COMMENT '任务执行状态（0为删除 1为未发布  2为已发布）',
  `rid` bigint(255) UNSIGNED NOT NULL COMMENT '记录id',
  `gmt_create` datetime(6) NOT NULL COMMENT '任务创建时间',
  `gmt_modified` datetime(6) NOT NULL ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '任务更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_future_tasks_rid`(`rid`) USING BTREE,
  CONSTRAINT `FK_future_tasks_rid` FOREIGN KEY (`rid`) REFERENCES `records` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for record_enclosure
-- ----------------------------
DROP TABLE IF EXISTS `record_enclosure`;
CREATE TABLE `record_enclosure`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '记录附件id',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '附件地址',
  `file_describe` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '附件描述',
  `rid` bigint(255) UNSIGNED NOT NULL COMMENT '记录id',
  `gmt_create` datetime(6) NOT NULL COMMENT '附件创建时间',
  `gmt_modified` datetime(6) NOT NULL ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '附件更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_record_enclosyre_rid`(`rid`) USING BTREE,
  CONSTRAINT `FK_record_enclosyre_rid` FOREIGN KEY (`rid`) REFERENCES `records` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 133 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for records
-- ----------------------------
DROP TABLE IF EXISTS `records`;
CREATE TABLE `records`  (
  `id` bigint(255) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '记录id',
  `title` char(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '记录标题',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '记录主要内容描述',
  `status` tinyint(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '记录状态(0为删除  1为发布中 2为隐藏 3为未来任务)',
  `uid` bigint(255) UNSIGNED NOT NULL COMMENT '发起人',
  `gmt_create` datetime(6) NOT NULL COMMENT '记录创建时间',
  `gmt_modified` datetime(6) NOT NULL ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '记录更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_records_uid`(`uid`) USING BTREE,
  CONSTRAINT `FK_records_uid` FOREIGN KEY (`uid`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 62 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_auths
-- ----------------------------
DROP TABLE IF EXISTS `user_auths`;
CREATE TABLE `user_auths`  (
  `id` bigint(255) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '第三方绑定id',
  `identity_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '第三方类型（微博、QQ）',
  `identifier` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '第三方唯一标识',
  `uid` bigint(255) UNSIGNED NOT NULL COMMENT '用户id（外键）',
  `gmt_create` datetime(6) NOT NULL COMMENT '用户创建时间',
  `gmt_modified` datetime(6) NOT NULL ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '用户更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_user_auths__uid`(`uid`) USING BTREE,
  CONSTRAINT `FK_user_auths__uid` FOREIGN KEY (`uid`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` bigint(255) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户头像',
  `nick_name` char(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `head_portrait` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户头像',
  `sex` tinyint(1) UNSIGNED NOT NULL DEFAULT 2 COMMENT '用户性别(0:男 1::女 2:未知)',
  `age` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '用户年龄(0~255)',
  `birthday` datetime(6) NULL DEFAULT NULL COMMENT '用户生日',
  `email` char(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户绑定邮箱(用作主要登录方式)',
  `phone` char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户绑定手机号',
  `is_real_name` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '用户是否实名（1为是 0为否）',
  `id_card` char(18) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户身份证',
  `password` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户密码（md5）',
  `jurisdiction` tinyint(1) UNSIGNED NOT NULL DEFAULT 2 COMMENT '用户权限\r\n0：超级管理员\r\n1：管理员\r\n:2：普通用户',
  `vip` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '会员等级，值表示等级',
  `status` tinyint(1) UNSIGNED NOT NULL DEFAULT 3 COMMENT '用户状态:\r\n0：删除\r\n1：黑名单\r\n2：禁用\r\n3：正常',
  `gmt_create` datetime(6) NOT NULL COMMENT '用户创建时间',
  `gmt_modified` datetime(6) NOT NULL ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '用户更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `id`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
