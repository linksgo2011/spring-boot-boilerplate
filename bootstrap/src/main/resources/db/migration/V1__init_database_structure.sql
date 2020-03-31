CREATE TABLE `department`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) NOT NULL,
  `pid` bigint(20) NOT NULL COMMENT 'parent department',
  `enabled` tinyint(1) NOT NULL,
  `createAt` datetime NULL DEFAULT NULL,
  `updateAt` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `readable_name` varchar(255)  NULL DEFAULT NULL COMMENT '可读名称',
  `name` varchar(255)  NOT NULL COMMENT '名称',
  `remark` varchar(255)  NULL DEFAULT NULL COMMENT '备注',
  `createAt` datetime NULL DEFAULT NULL,
  `updateAt` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `avatar_id` bigint(20) NULL DEFAULT NULL COMMENT '头像资源',
  `email` varchar(255)  NULL DEFAULT NULL COMMENT '邮箱',
  `enabled` tinyint(1) NULL DEFAULT NULL COMMENT '状态',
  `password` varchar(255)  NULL DEFAULT NULL COMMENT '密码',
  `username` varchar(255)  NULL DEFAULT NULL COMMENT '用户名',
  `department_id` bigint(20) NULL DEFAULT NULL,
  `phone` varchar(255)  NULL DEFAULT NULL,
  `createAt` datetime NULL DEFAULT NULL,
  `updateAt` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `unique_email`(`email`) ,
  UNIQUE INDEX `unique_username`(`username`)
);

CREATE TABLE `user_role`  (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role` varchar(255) NOT NULL COMMENT '角色名称',
  PRIMARY KEY (`user_id`, `role`)
);
