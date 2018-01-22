-- ----------------------------
-- Table structure for test_two
-- ----------------------------
DROP TABLE IF EXISTS `test_two`;
CREATE TABLE `test_two` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='测试';

-- ----------------------------
-- Records of test_two
-- ----------------------------
INSERT INTO `test_two` VALUES ('1', '测试', '这是config_two测试数据库');
