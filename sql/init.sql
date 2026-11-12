-- 创建数据库（如果不存在）
CREATE DATABASE IF NOT EXISTS message_board DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE message_board;

-- 1. 用户表
DROP TABLE IF EXISTS sys_user;
CREATE TABLE sys_user (
                          id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                          username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
                          password VARCHAR(100) NOT NULL COMMENT '密码',
                          phone VARCHAR(20) DEFAULT NULL COMMENT '手机号',
                          role TINYINT DEFAULT 0 COMMENT '角色 0普通用户 1管理员',
                          create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 默认管理员账号 admin / admin
INSERT INTO sys_user (username, password, role) VALUES ('admin', 'admin', 1);

-- 2. 留言表
DROP TABLE IF EXISTS message;
CREATE TABLE message (
                         id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                         user_id BIGINT NOT NULL COMMENT '发布用户ID',
                         content TEXT NOT NULL COMMENT '留言内容',
                         status TINYINT DEFAULT 1 COMMENT '状态 1正常 0删除（逻辑删除）',
                         is_top TINYINT DEFAULT 0 COMMENT '是否置顶 0否 1是',
                         create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                         update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='留言表';

-- 3. 回复表（如果加了回复功能就保留，没加可以删掉这段）
DROP TABLE IF EXISTS reply;
CREATE TABLE reply (
                       id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                       message_id BIGINT NOT NULL COMMENT '关联留言ID',
                       user_id BIGINT NOT NULL COMMENT '回复用户ID',
                       content TEXT NOT NULL COMMENT '回复内容',
                       status TINYINT DEFAULT 1 COMMENT '状态 1正常 0删除',
                       create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='留言回复表';
