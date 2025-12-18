<template>
  <a-layout class="home-layout">
    <Header />
    <a-layout-content class="home-content">
      <div class="feed-container">
        <a-spin v-if="loading" :spinning="loading" size="large" class="loading-container">
          <template #indicator>
            <div style="min-height: 400px"></div>
          </template>
        </a-spin>
        <a-empty v-else-if="feeds.length === 0" description="暂无内容，快去发布第一条动态吧！" />
        <FeedCard
          v-for="feed in feeds"
          :key="feed.item.id"
          :feed="feed"
          :current-user-id="getCurrentUserId()"
          @update="loadFeeds"
        />
      </div>
    </a-layout-content>
    <a-float-button
      v-if="userInfo"
      type="primary"
      :style="{ right: '24px', bottom: '24px' }"
      @click="showPostModal = true"
    >
      <template #icon>
        <PlusOutlined />
      </template>
    </a-float-button>
    <PostModal
      v-model:open="showPostModal"
      @success="handlePostSuccess"
    />
  </a-layout>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { PlusOutlined } from '@ant-design/icons-vue'
import Header from '../components/Header.vue'
import FeedCard from '../components/FeedCard.vue'
import PostModal from '../components/PostModal.vue'
import { getFeed } from '../services/feedService'
import { getUserInfo } from '../utils/auth'
import type { FeedVo } from '../services/feedService'

const feeds = ref<FeedVo[]>([])
const loading = ref(false)
const showPostModal = ref(false)
const userInfo = getUserInfo()

// 获取当前用户ID，如果没有登录则使用测试用户ID
const getCurrentUserId = (): number => {
  if (userInfo?.id) {
    return userInfo.id
  }
  // 使用测试用户ID（根据后端代码，默认是123456）
  return 123456
}

const loadFeeds = async () => {
  const userId = getCurrentUserId()
  if (!userId) return

  loading.value = true
  try {
    const data = await getFeed(userId, 10)
    feeds.value = data
  } catch (error) {
    console.error('加载Feed失败:', error)
  } finally {
    loading.value = false
  }
}

const handlePostSuccess = () => {
  showPostModal.value = false
  loadFeeds()
}

onMounted(() => {
  loadFeeds()
})
</script>

<style scoped>
.home-layout {
  min-height: 100vh;
  background-color: #f5f5f5;
}

.home-content {
  padding: 20px;
  max-width: 800px;
  margin: 0 auto;
}

.feed-container {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.loading-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 400px;
}
</style>

