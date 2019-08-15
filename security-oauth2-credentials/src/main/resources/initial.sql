DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `role`;
DROP TABLE IF EXISTS `user_role`;
DROP TABLE IF EXISTS `role_permission`;
DROP TABLE IF EXISTS `permission`;

CREATE TABLE `user` (
`id` bigint(11) NOT NULL AUTO_INCREMENT,
`username` varchar(255) NOT NULL,
`password` varchar(255) NOT NULL,
PRIMARY KEY (`id`) 
);
CREATE TABLE `role` (
`id` bigint(11) NOT NULL AUTO_INCREMENT,
`name` varchar(255) NOT NULL,
PRIMARY KEY (`id`) 
);
CREATE TABLE `user_role` (
`id` bigint(11) NOT NULL AUTO_INCREMENT,
`user_id` bigint(11) NOT NULL,
`role_id` bigint(11) NOT NULL,
PRIMARY KEY (`id`)
);
CREATE TABLE `role_permission` (
`id` bigint(11) NOT NULL AUTO_INCREMENT,
`role_id` bigint(11) NOT NULL,
`permission_id` bigint(11) NOT NULL,
PRIMARY KEY (`id`)
);
CREATE TABLE `permission` (
`id` bigint(11) NOT NULL AUTO_INCREMENT,
`url` varchar(255) NOT NULL,
`name` varchar(255) NOT NULL,
`description` varchar(255) NULL,
`pid` bigint(11) NOT NULL,
PRIMARY KEY (`id`) 
);

INSERT INTO user (id, username, password) VALUES (1,'user','{bcrypt}$2a$10$Tme77eHtXzcB8ghQUepYguJr7P7ESg0Y7XHMnk60s.kf2A.BWBD9m');
INSERT INTO user (id, username , password) VALUES (2,'admin','{bcrypt}$2a$10$Tme77eHtXzcB8ghQUepYguJr7P7ESg0Y7XHMnk60s.kf2A.BWBD9m');
INSERT INTO role (id, name) VALUES (1,'USER');
INSERT INTO role (id, name) VALUES (2,'ADMIN');
INSERT INTO permission (id, url, name, pid) VALUES (1,'/user/common','common',0);
INSERT INTO permission (id, url, name, pid) VALUES (2,'/user/admin','admin',0);
INSERT INTO user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO user_role (user_id, role_id) VALUES (2, 1);
INSERT INTO user_role (user_id, role_id) VALUES (2, 2);
INSERT INTO role_permission (role_id, permission_id) VALUES (1, 1);
INSERT INTO role_permission (role_id, permission_id) VALUES (2, 1);
INSERT INTO role_permission (role_id, permission_id) VALUES (2, 2);