-- ============================================
-- FeedX 数据库建表SQL
-- 基于Java实体类生成
-- ============================================

-- 创建数据库（如果不存在）
CREATE DATABASE IF NOT EXISTS `feedx` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE `feedx`;

-- ============================================
-- 1. 用户表 (user)
-- ============================================
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
    `id` BIGINT NOT NULL COMMENT '用户ID',
    `username` VARCHAR(50) NOT NULL COMMENT '用户名',
    `password` VARCHAR(255) NOT NULL COMMENT '密码',
    `nick_name` VARCHAR(50) DEFAULT NULL COMMENT '昵称',
    `phone_number` VARCHAR(20) DEFAULT NULL COMMENT '手机号',
    `email` VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
    `gender` TINYINT DEFAULT 0 COMMENT '性别 (0表示未知，1表示男，2表示女)',
    `birthday` DATE DEFAULT NULL COMMENT '生日',
    `user_status` TINYINT DEFAULT 1 COMMENT '用户状态: 1正常',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除 0:未删除 1:已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`),
    UNIQUE KEY `uk_phone_number` (`phone_number`),
    KEY `idx_deleted` (`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- ============================================
-- 2. 内容表 (item) - 用户发布的视频/内容
-- ============================================
DROP TABLE IF EXISTS `item`;
CREATE TABLE `item` (
    `id` BIGINT NOT NULL COMMENT '内容ID',
    `user_id` BIGINT NOT NULL COMMENT '用户id',
    `title` VARCHAR(255) DEFAULT NULL COMMENT '标题',
    `video_url` VARCHAR(500) DEFAULT NULL COMMENT '视频url',
    `like_num` BIGINT DEFAULT 0 COMMENT '点赞数',
    `status` TINYINT DEFAULT 1 COMMENT '0 私密 1公开',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除 0:未删除 1:已删除',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_created_at` (`created_at`),
    KEY `idx_deleted` (`deleted`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='内容表';

-- ============================================
-- 3. 关注表 (follow)
-- ============================================
DROP TABLE IF EXISTS `follow`;
CREATE TABLE `follow` (
    `id` BIGINT NOT NULL COMMENT '关注关系ID',
    `following_id` BIGINT NOT NULL COMMENT '被关注者id',
    `follower_id` BIGINT NOT NULL COMMENT '关注者id',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除 0:未删除 1:已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_follow` (`follower_id`, `following_id`, `deleted`),
    KEY `idx_following_id` (`following_id`),
    KEY `idx_follower_id` (`follower_id`),
    KEY `idx_deleted` (`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='关注表';

-- ============================================
-- 4. 评论表 (comment)
-- ============================================
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
    `id` BIGINT NOT NULL COMMENT '评论ID',
    `item_id` BIGINT NOT NULL COMMENT '内容ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `content` TEXT NOT NULL COMMENT '评论内容',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除 0:未删除 1:已删除',
    PRIMARY KEY (`id`),
    KEY `idx_item_id` (`item_id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_created_at` (`created_at`),
    KEY `idx_deleted` (`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='评论表';

-- ============================================
-- 5. 收藏表 (collect)
-- ============================================
DROP TABLE IF EXISTS `collect`;
CREATE TABLE `collect` (
    `id` BIGINT NOT NULL COMMENT '收藏ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `item_id` BIGINT NOT NULL COMMENT '内容ID',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除 0:未删除 1:已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_collect` (`user_id`, `item_id`, `deleted`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_item_id` (`item_id`),
    KEY `idx_deleted` (`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='收藏表';

-- ============================================
-- 6. 商品表 (goods)
-- ============================================
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
    `id` BIGINT NOT NULL COMMENT 'goods id',
    `category_id` BIGINT DEFAULT NULL COMMENT '分类ID',
    `user_id` BIGINT NOT NULL COMMENT '所属用户id',
    `name` VARCHAR(255) DEFAULT NULL COMMENT '商品名称',
    `title` VARCHAR(255) DEFAULT NULL COMMENT '商品标题',
    `detail` TEXT DEFAULT NULL COMMENT '商品详情描述',
    `main_img_url` VARCHAR(500) DEFAULT NULL COMMENT '商品介绍主图',
    `img_urls` TEXT DEFAULT NULL COMMENT '商品图片 多个图片逗号分隔',
    `video_url` VARCHAR(500) DEFAULT NULL COMMENT '商品视频',
    `price` BIGINT DEFAULT 0 COMMENT '售价，整数方式保存',
    `status` TINYINT DEFAULT 0 COMMENT '状态 0:下架, 1:上架',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除 0:未删除 1:已删除',
    PRIMARY KEY (`id`),
    KEY `idx_category_id` (`category_id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_status` (`status`),
    KEY `idx_deleted` (`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品表';

-- ============================================
-- 7. 分类表 (category)
-- ============================================
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
    `id` BIGINT NOT NULL COMMENT '分类ID',
    `category_name` VARCHAR(100) NOT NULL COMMENT '分类名称',
    `parent_category_id` INT DEFAULT NULL COMMENT '父分类ID',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除 0:未删除 1:已删除',
    PRIMARY KEY (`id`),
    KEY `idx_parent_category_id` (`parent_category_id`),
    KEY `idx_deleted` (`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='分类表';

-- ============================================
-- 8. 订单表 (t_order)
-- ============================================
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order` (
    `id` BIGINT NOT NULL COMMENT '订单ID',
    `customer_id` BIGINT NOT NULL COMMENT '买家id',
    `seller_id` BIGINT NOT NULL COMMENT '卖家id',
    `order_title` VARCHAR(255) DEFAULT NULL COMMENT '订单标题',
    `order_detail` TEXT DEFAULT NULL COMMENT '订单详情',
    `good_id` BIGINT NOT NULL COMMENT '商品id',
    `good_nums` INT DEFAULT 1 COMMENT '商品总数',
    `total_money` BIGINT DEFAULT 0 COMMENT '订单总价',
    `pay` TINYINT DEFAULT 0 COMMENT '状态 0:未支付, 1:已支付',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除 0:未删除 1:已删除',
    PRIMARY KEY (`id`),
    KEY `idx_customer_id` (`customer_id`),
    KEY `idx_seller_id` (`seller_id`),
    KEY `idx_good_id` (`good_id`),
    KEY `idx_pay` (`pay`),
    KEY `idx_created_at` (`created_at`),
    KEY `idx_deleted` (`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单表';

-- ============================================
-- 9. 优惠券表 (voucher)
-- ============================================
DROP TABLE IF EXISTS `voucher`;
CREATE TABLE `voucher` (
    `id` BIGINT NOT NULL COMMENT '优惠卷 id',
    `type` INT DEFAULT NULL COMMENT '优惠卷类型',
    `name` VARCHAR(255) DEFAULT NULL COMMENT '优惠卷名称',
    `title` VARCHAR(255) DEFAULT NULL COMMENT '优惠卷标题',
    `detail` TEXT DEFAULT NULL COMMENT '优惠卷详情描述',
    `pay_value` BIGINT DEFAULT 0 COMMENT '售价，整数方式保存',
    `actual_price` BIGINT DEFAULT 0 COMMENT '抵扣价，整数方式保存',
    `stock` INT DEFAULT 0 COMMENT '剩余库存',
    `voucher_status` INT DEFAULT 0 COMMENT '状态 0:下架, 1:上架, 2:过期',
    `deadline` DATETIME DEFAULT NULL COMMENT '过期时间',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除 0:未删除 1:已删除',
    PRIMARY KEY (`id`),
    KEY `idx_voucher_status` (`voucher_status`),
    KEY `idx_deadline` (`deadline`),
    KEY `idx_deleted` (`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='优惠券表';

-- ============================================
-- 初始化数据（可选）
-- ============================================

-- 插入测试用户（密码需要根据实际加密方式调整）
-- INSERT INTO `user` (`id`, `username`, `password`, `nick_name`, `user_status`, `created_at`, `updated_at`, `deleted`) 
-- VALUES (123456, 'test_user', 'test_password', '测试用户', 1, NOW(), NOW(), 0);

