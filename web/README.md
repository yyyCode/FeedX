# FeedX 前端项目

基于 Vue 3 + Vite + TypeScript + Ant Design Vue 的社交内容平台前端

## 功能特性

- ✅ 用户登录认证
- ✅ Feed流主页（显示关注的人发布的内容）
- ✅ 发布内容（上传视频）
- ✅ 点赞、评论、收藏、关注等交互功能
- ✅ 用户主页和个人信息展示

## 技术栈

- Vue 3 (Composition API)
- TypeScript
- Vite
- Vue Router
- Ant Design Vue
- Axios

## 快速开始

### 安装依赖

```bash
cd web
npm install
```

### 启动开发服务器

```bash
npm run dev
```

访问 http://localhost:5173

### 跳过登录直接体验

现在你可以直接访问主页，无需登录！

1. **直接访问主页**：启动后直接访问 `http://localhost:5173/home`，系统会使用测试用户ID（123456）加载内容
2. **快速体验按钮**：在登录页面点击"快速体验（跳过登录）"按钮，可以直接进入主页
3. **体验模式**：未登录状态下，右上角会显示"体验模式"标签，可以浏览内容但无法发布

> 注意：体验模式下使用的是测试用户ID，如果需要真实登录，请填写正确的账号密码和验证码信息。

### 构建生产版本

```bash
npm run build
```

## 项目结构

```
web/
├── src/
│   ├── components/      # 组件
│   │   ├── Header.vue          # 头部导航
│   │   ├── FeedCard.vue       # Feed卡片
│   │   ├── CommentModal.vue    # 评论弹窗
│   │   └── PostModal.vue      # 发布内容弹窗
│   ├── views/           # 页面
│   │   ├── Login.vue          # 登录页
│   │   ├── Home.vue           # 主页（Feed流）
│   │   └── Profile.vue         # 用户主页
│   ├── services/        # API服务
│   │   ├── authService.ts      # 认证相关
│   │   ├── feedService.ts      # Feed流相关
│   │   ├── itemService.ts      # 内容相关
│   │   └── interactionService.ts # 交互相关
│   ├── utils/          # 工具函数
│   │   ├── api.ts             # Axios配置
│   │   └── auth.ts            # 认证工具
│   ├── router/         # 路由配置
│   ├── App.vue         # 根组件
│   └── main.ts         # 入口文件
├── package.json
├── vite.config.ts
└── tsconfig.json
```

## API 配置

默认后端地址：`http://localhost:8080`

如需修改，请编辑 `vite.config.ts` 中的 proxy 配置。

## 注意事项

1. 登录接口需要验证码key和验证码，请根据后端实际情况填写
2. 后端登录接口当前返回的userInfo可能是数字ID，前端已做兼容处理
3. 视频上传功能需要后端支持MinIO文件存储

