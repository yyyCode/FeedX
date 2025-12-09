<template>
  <el-container class="layout">
    <el-header class="layout__header">
      <div class="brand">FeedX 控制台</div>
      <div class="actions">
        <el-button type="primary" link @click="goto('Dashboard')">仪表盘</el-button>
        <el-button type="primary" link @click="goto('Feed')">Feed 流</el-button>
        <el-button type="primary" link @click="goto('Items')">内容管理</el-button>
        <el-button type="primary" link @click="goto('Login')">登录</el-button>
      </div>
    </el-header>
    <el-main class="layout__main">
      <router-view />
    </el-main>
  </el-container>
</template>

<script setup>
import { useRouter } from 'vue-router';

const router = useRouter();

const goto = (name) => {
  router.push({ name });
};
</script>

<style scoped>
.layout {
  min-height: 100vh;
  background: #f5f7fb;
}

.layout__header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 12px 24px;
  background: #111827;
  color: #fff;
}

.brand {
  font-weight: 700;
  letter-spacing: 0.5px;
}

.actions :deep(.el-button) {
  color: #e5e7eb;
}

.layout__main {
  padding: 16px 24px 32px;
}
</style>

