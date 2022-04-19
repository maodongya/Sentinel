CREATE TABLE `sentinel_metric`  (
                                   `id` int NOT NULL AUTO_INCREMENT COMMENT 'id，主键',
                                   `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
                                   `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
                                   `app` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '应用名称',
                                   `timestamp` datetime(0) NULL DEFAULT NULL COMMENT '统计时间',
                                   `resource` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资源名称',
                                   `pass_qps` int NULL DEFAULT NULL COMMENT '通过qps',
                                   `success_qps` int NULL DEFAULT NULL COMMENT '成功qps',
                                   `block_qps` int NULL DEFAULT NULL COMMENT '限流qps',
                                   `exception_qps` int NULL DEFAULT NULL COMMENT '发送异常的次数',
                                   `rt` double NULL DEFAULT NULL COMMENT '所有successQps的rt的和',
                                   `_count` int NULL DEFAULT NULL COMMENT '本次聚合的总条数',
                                   `resource_code` int NULL DEFAULT NULL COMMENT '资源的hashCode',
                                   PRIMARY KEY (`id`) USING BTREE,
                                   INDEX `app_idx`(`app`) USING BTREE,
                                   INDEX `resource_idx`(`resource`) USING BTREE,
                                   INDEX `timestamp_idx`(`timestamp`) USING BTREE
);
CREATE TABLE `sentinel_rule`  (
                                    `id` int NOT NULL AUTO_INCREMENT COMMENT 'id，主键',
                                    `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
                                    `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
                                    `rule_type` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规则类型',
                                    `app` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '应用名称',
                                    `ip` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ip',
                                    `port` int NOT NULL COMMENT 'port',
                                    `resource` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资源名称 ',
                                    `rule` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规则',
                                    PRIMARY KEY (`id`) USING BTREE,
                                    INDEX `app_ip_port_resource_idx`(`app`,`ip`,`port`,`resource`) USING BTREE,
                                    INDEX `ip_idx`(`ip`) USING BTREE
);