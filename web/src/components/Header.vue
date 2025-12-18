<template>
  <a-layout-header class="app-header">
    <div class="header-content">
      <div class="header-logo" @click="goHome">FeedX</div>
      <a-dropdown v-if="userInfo">
        <a-button type="text" class="user-menu">
          <a-avatar :size="24" :icon="h(UserOutlined)" style="margin-right: 8px" />
          {{ userInfo?.nickName || userInfo?.username || '用户' }}
        </a-button>
        <template #overlay>
          <a-menu>
            <a-menu-item key="profile" @click="goProfile">
              <UserOutlined />
              个人主页
            </a-menu-item>
            <a-menu-item key="logout" @click="handleLogout">
              <LogoutOutlined />
              退出登录
            </a-menu-item>
          </a-menu>
        </template>
      </a-dropdown>
      <div v-else class="guest-info">
        <a-tag color="blue">体验模式</a-tag>
        <a-button type="link" @click="goLogin" style="margin-left: 8px">登录</a-button>
      </div>
    </div>
  </a-layout-header>
</template>

<script setup lang="ts">
import { h } from 'vue'
import { useRouter } from 'vue-router'
import { UserOutlined, LogoutOutlined } from '@ant-design/icons-vue'
import { getUserInfo, clearAuth } from '../utils/auth'

const router = useRouter()
const userInfo = getUserInfo()

const goHome = () => {
  router.push('/home')
}

const goProfile = () => {
  if (userInfo?.id) {
    router.push(`/profile/${userInfo.id}`)
  }
}

const handleLogout = () => {
  clearAuth()
  router.push('/login')
}

const goLogin = () => {
  router.push('/login')
}
</script>

<style scoped>
.app-header {
  background: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  padding: 0 24px;
  position: sticky;
  top: 0;
  z-index: 100;
}

.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 100%;
}

.header-logo {
  font-size: 24px;
  font-weight: bold;
  color: #1890ff;
  cursor: pointer;
  user-select: none;
}

.user-menu {
  display: flex;
  align-items: center;
}

.guest-info {
  display: flex;
  align-items: center;
}
</style>

