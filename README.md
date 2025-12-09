# Feed流模型
一个基于关注关系与Timeline的Feed流分布式系统模型

六大模块
- count：统计用户的行为数据
- feed：投喂内容给用户
- interaction：点赞、评论、收藏
- item：对item（视频）的管理
- order：订单管理
- user：用户模块

已实现
- 按照时间以及关注关系进行内容的推送

待实现目标
- 冷热数据分离
- 突发流量处理