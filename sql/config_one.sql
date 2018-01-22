-- ----------------------------
-- Table structure for test_one
-- ----------------------------
DROP TABLE IF EXISTS `test_one`;
CREATE TABLE `test_one` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='测试';

-- ----------------------------
-- Records of test_one
-- ----------------------------
INSERT INTO `test_one` VALUES ('1', '测试', '这是config_one测试数据库');
