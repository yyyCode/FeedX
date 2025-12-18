# FeedX 数据库设计文档

## 数据库说明

本数据库设计基于Java实体类自动生成，使用MySQL 8.0+。

## 表结构说明

### 1. 用户表 (user)
- **主键**: `id` (BIGINT) - 雪花算法生成的ID
- **唯一索引**: 
  - `username` - 用户名唯一
  - `phone_number` - 手机号唯一
- **字段说明**:
  - `gender`: 0-未知, 1-男, 2-女
  - `user_status`: 1-正常
  - `deleted`: 逻辑删除标记

### 2. 内容表 (item)
- **主键**: `id` (BIGINT)
- **索引**: 
  - `user_id` - 用户ID索引
  - `created_at` - 创建时间索引（用于Feed流排序）
  - `status` - 状态索引
- **字段说明**:
  - `status`: 0-私密, 1-公开
  - `like_num`: 点赞数（冗余字段，实际统计从count服务获取）

### 3. 关注表 (follow)
- **主键**: `id` (BIGINT)
- **唯一索引**: `(follower_id, following_id, deleted)` - 防止重复关注
- **字段说明**:
  - `following_id`: 被关注者ID
  - `follower_id`: 关注者ID

### 4. 评论表 (comment)
- **主键**: `id` (BIGINT)
- **索引**: 
  - `item_id` - 内容ID索引
  - `user_id` - 用户ID索引
  - `created_at` - 创建时间索引（用于评论排序）

### 5. 收藏表 (collect)
- **主键**: `id` (BIGINT)
- **唯一索引**: `(user_id, item_id, deleted)` - 防止重复收藏
- **字段说明**: 用户收藏的内容

### 6. 商品表 (goods)
- **主键**: `id` (BIGINT)
- **索引**: 
  - `category_id` - 分类ID索引
  - `user_id` - 用户ID索引
  - `status` - 状态索引
- **字段说明**:
  - `status`: 0-下架, 1-上架
  - `img_urls`: 多个图片URL，逗号分隔

### 7. 分类表 (category)
- **主键**: `id` (BIGINT)
- **索引**: `parent_category_id` - 父分类ID索引（支持多级分类）
- **字段说明**: 商品分类，支持树形结构

### 8. 订单表 (t_order)
- **主键**: `id` (BIGINT)
- **索引**: 
  - `customer_id` - 买家ID索引
  - `seller_id` - 卖家ID索引
  - `good_id` - 商品ID索引
  - `pay` - 支付状态索引
- **字段说明**:
  - `pay`: 0-未支付, 1-已支付

### 9. 优惠券表 (voucher)
- **主键**: `id` (BIGINT)
- **索引**: 
  - `voucher_status` - 状态索引
  - `deadline` - 过期时间索引
- **字段说明**:
  - `voucher_status`: 0-下架, 1-上架, 2-过期

## 使用说明

### 执行建表SQL

```bash
# 方式1: 使用mysql命令行
mysql -u root -p < database/schema.sql

# 方式2: 使用MySQL客户端工具导入
# 直接执行 schema.sql 文件内容
```

### 注意事项

1. **ID生成**: 所有表的主键ID使用MyBatis Plus的`IdType.ASSIGN_ID`（雪花算法），不需要设置AUTO_INCREMENT
2. **逻辑删除**: 所有表都使用`deleted`字段实现逻辑删除，默认值为0
3. **时间字段**: `created_at`和`updated_at`使用MySQL的自动时间戳功能
4. **字符集**: 使用utf8mb4字符集，支持emoji等特殊字符
5. **索引优化**: 
   - 为常用查询字段建立了索引
   - 关注表和收藏表建立了唯一索引防止重复数据
6. **外键约束**: 未设置外键约束，依赖应用层保证数据一致性（适合分布式系统）

## 扩展说明

如果需要添加新表或修改表结构，请：
1. 修改对应的Java实体类
2. 更新本SQL文件
3. 使用数据库迁移工具（如Flyway）管理版本变更

