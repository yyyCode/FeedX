# FeedX Web（Vue）

## 开发

```bash
cd web
npm install
npm run dev
```

默认代理到 `http://localhost:8080` 的网关，可在 `vite.config.js` 调整。

## 页面
- 登录：`/login`，拿后端返回的 token 存储在 `localStorage`。
- 仪表盘：`/dashboard`，展示示例指标和趋势图。
- Feed 流：`/feed`，输入用户 ID 拉取 `/feed/{userId}`。
- 内容发布：`/items`，简单的上传表单调用 `/item`。

