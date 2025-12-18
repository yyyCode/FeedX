<template>
  <a-layout class="profile-layout">
    <Header />
    <a-layout-content class="profile-content">
      <a-card v-if="userInfo" class="profile-card">
        <div class="profile-header">
          <a-avatar :size="80" :icon="h(UserOutlined)" />
          <div class="profile-info">
            <h2>{{ userInfo.nickName || userInfo.username }}</h2>
            <p>@{{ userInfo.username }}</p>
          </div>
        </div>
      </a-card>
      <a-card title="发布的内容" class="items-card">
        <a-spin :spinning="loading">
          <a-empty v-if="!loading && items.length === 0" description="暂无发布内容" />
          <div v-else class="items-grid">
            <div
              v-for="item in items"
              :key="item.id"
              class="item-card"
              @click="viewItem(item)"
            >
              <video
                v-if="item.videoUrl"
                :src="item.videoUrl"
                class="item-media"
                preload="metadata"
              />
              <div class="item-info">
                <p class="item-title">{{ item.title }}</p>
                <span class="item-time">{{ formatTime(item.createdAt) }}</span>
              </div>
            </div>
          </div>
        </a-spin>
      </a-card>
    </a-layout-content>
  </a-layout>
</template>

<script setup lang="ts">
import { ref, onMounted, h } from 'vue'
import { useRoute } from 'vue-router'
import { UserOutlined } from '@ant-design/icons-vue'
import Header from '../components/Header.vue'
import { getUserItems } from '../services/itemService'
import dayjs from 'dayjs'

const route = useRoute()
const userId = route.params.userId as string
const items = ref<any[]>([])
const loading = ref(false)
const userInfo = ref<any>(null)

const loadItems = async () => {
  loading.value = true
  try {
    const data = await getUserItems(userId)
    items.value = data
  } catch (error) {
    console.error('加载内容失败:', error)
  } finally {
    loading.value = false
  }
}

const formatTime = (time: string) => {
  return dayjs(time).format('YYYY-MM-DD HH:mm')
}

const viewItem = (item: any) => {
  // TODO: 实现查看详情
  console.log('查看内容:', item)
}

onMounted(() => {
  loadItems()
})
</script>

<style scoped>
.profile-layout {
  min-height: 100vh;
  background-color: #f5f5f5;
}

.profile-content {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

.profile-card {
  margin-bottom: 20px;
}

.profile-header {
  display: flex;
  align-items: center;
  gap: 20px;
}

.profile-info h2 {
  margin: 0;
  font-size: 24px;
}

.profile-info p {
  margin: 5px 0 0 0;
  color: #999;
}

.items-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  gap: 16px;
}

.item-card {
  background: #fff;
  border-radius: 8px;
  overflow: hidden;
  cursor: pointer;
  transition: transform 0.2s;
}

.item-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.item-media {
  width: 100%;
  height: 200px;
  object-fit: cover;
}

.item-info {
  padding: 12px;
}

.item-title {
  margin: 0 0 8px 0;
  font-size: 14px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.item-time {
  font-size: 12px;
  color: #999;
}
</style>

