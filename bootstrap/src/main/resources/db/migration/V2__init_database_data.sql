INSERT INTO `department` VALUES (2, '研发部', 7, b'1', '2019-03-25 09:15:32','2019-03-25 09:14:05');
INSERT INTO `department` VALUES (5, '运维部', 7, b'1', '2019-03-25 09:20:44','2019-03-25 09:14:05');
INSERT INTO `department` VALUES (6, '测试部', 8, b'1', '2019-03-25 09:52:18','2019-03-25 09:14:05');

INSERT INTO `role` VALUES (1, '管理员', 'admin', '系统管理员', '2018-11-23 11:04:37','2018-11-23 11:04:37');
INSERT INTO `role` VALUES (2, '普通用户', 'user', '普通用户',  '2018-11-23 13:09:06', '2018-11-23 11:04:37');

INSERT INTO `user_role` VALUES (1, 1);
INSERT INTO `user_role` VALUES (1, 2);

INSERT INTO `user` VALUES (1, NULL, 'admin@printf.cn', 1, '$2a$10$Vk35bGfGKdA7kdTIaGo8bea.jZtZg6s5tFs3lENe3gaDH8HTJ6C/2', 'admin', 2, '18888888888','2018-08-23 09:11:56', '2019-05-18 17:34:21');
INSERT INTO `user` VALUES (3, NULL, 'test@printf.cn', 1, '$2a$10$Vk35bGfGKdA7kdTIaGo8bea.jZtZg6s5tFs3lENe3gaDH8HTJ6C/2', 'test', 2, '17777777777','2018-12-27 20:05:26', '2019-04-01 09:15:24');
