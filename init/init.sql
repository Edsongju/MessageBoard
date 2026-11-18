-- =============================================
-- 留言板项目 MySQL 初始化脚本
-- =============================================

-- 1. 创建数据库
DROP DATABASE IF EXISTS message_board;
CREATE DATABASE message_board DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE message_board;

-- 2. 用户表
DROP TABLE IF EXISTS sys_user;
CREATE TABLE sys_user (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
                          username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
                          password VARCHAR(100) NOT NULL COMMENT '密码',
                          phone VARCHAR(20) DEFAULT NULL COMMENT '手机号',
                          role TINYINT DEFAULT 0 COMMENT '角色：0-普通用户，1-管理员',
                          create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                          status TINYINT DEFAULT 1 COMMENT '逻辑删除：1-正常，0-删除'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 3. 留言表
DROP TABLE IF EXISTS message;
CREATE TABLE message (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '留言ID',
                         user_id BIGINT NOT NULL COMMENT '发布用户ID',
                         topic VARCHAR(50) DEFAULT '其他' COMMENT '分类：日常/求助/分享/其他',
                         content TEXT NOT NULL COMMENT '留言内容',
                         is_top TINYINT DEFAULT 0 COMMENT '是否置顶：0-否，1-是',
                         create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
                         status TINYINT DEFAULT 1 COMMENT '逻辑删除：1-正常，0-删除',
                         INDEX idx_user_id (user_id),
                         INDEX idx_create_time (create_time DESC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='留言表';

-- 4. 回复表
DROP TABLE IF EXISTS reply;
CREATE TABLE reply (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '回复ID',
                       message_id BIGINT NOT NULL COMMENT '所属留言ID',
                       user_id BIGINT NOT NULL COMMENT '回复用户ID',
                       content TEXT NOT NULL COMMENT '回复内容',
                       create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '回复时间',
                       status TINYINT DEFAULT 1 COMMENT '逻辑删除：1-正常，0-删除',
                       INDEX idx_message_id (message_id),
                       INDEX idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='回复表';

-- =============================================
-- 插入测试数据
-- =============================================

-- 测试用户（密码均为 123456）
INSERT INTO sys_user (username, password, phone, role) VALUES
                                                           ('admin', 'admin', NULL, 1),
                                                           ('user01', '123456', '13800000001', 0),
                                                           ('user02', '123456', '13800000002', 0),
                                                           ('user03', '123456', '13800000003', 0),
                                                           ('user04', '123456', '13800000004', 0),
                                                           ('user05', '123456', '13800000005', 0),
                                                           ('user06', '123456', '13800000006', 0),
                                                           ('user07', '123456', '13800000007', 0),
                                                           ('user08', '123456', '13800000008', 0),
                                                           ('user09', '123456', '13800000009', 0),
                                                           ('user10', '123456', '13800000010', 0);

-- 测试置顶留言
INSERT INTO message (user_id, topic, content, is_top) VALUES
                                                          (10, '其他', '每周学习打卡贴，大家可以在这里记录每日学习进度~', 1),
                                                          (10, '其他', '【公告】社区规范与发帖须知，请大家遵守~', 1);

-- 测试普通留言
INSERT INTO message (user_id, topic, content) VALUES
                                                  (1, '日常', '今天把 Redis 缓存接入项目了，踩了几个坑，终于跑通了！'),
                                                  (2, '求助', '请问 Spring Cache 的 @CacheEvict 怎么同时清多个缓存组？'),
                                                  (3, '分享', '分享一个 Windows 版 Redis 配置避坑指南，maxheap 不能写进配置文件'),
                                                  (4, '日常', '打卡：今天学习了 Redis 持久化 RDB 和 AOF 的区别'),
                                                  (5, '求助', '本地 Redis 启动闪退一般是什么原因？'),
                                                  (6, '分享', '推荐 Redis Insight 可视化工具，官方出品很好用'),
                                                  (7, '日常', '今天完成了留言板的毛玻璃页面美化，效果还不错'),
                                                  (8, '求助', 'Jackson 序列化 LocalDateTime 报错怎么解决？'),
                                                  (9, '分享', '整理了 Spring Boot + Redis 缓存完整接入步骤');

-- 测试回复
INSERT INTO reply (message_id, user_id, content) VALUES
                                                     (3, 1, '很实用，收藏了！'),
                                                     (3, 2, '之前也踩过这个坑，确实要注意'),
                                                     (8, 3, '加 JavaTimeModule 就可以了');
