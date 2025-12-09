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
- 用cursor生成了一个潦草的前端，在web文件夹下面


待实现目标
- 冷热数据分离
- 突发流量处理



## 前端启动
使用方式（本地）：
1) cd web
2) npm install
3) npm run dev 后访问 http://localhost:5173
   如后端地址不同，修改 vite.config.js 代理；登录接口当前按表单参数提交。后续可补：路由守卫依赖真实 token 字段名、表格/分页、指标对接真实监控接口等。