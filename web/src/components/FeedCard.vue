<template>
  <a-card class="feed-card" :bordered="false">
    <template #cover>
      <div class="feed-media">
        <video
          v-if="feed.item.videoUrl"
          :src="feed.item.videoUrl"
          controls
          class="feed-video"
          @click.stop
        />
      </div>
    </template>
    <a-card-meta>
      <template #title>
        <div class="feed-header">
          <a-avatar :size="40" :icon="h(UserOutlined)" />
          <div class="feed-author">
            <span class="author-name">{{ feed.author.nickName || feed.author.username }}</span>
            <span class="author-time">{{ formatTime(feed.item.createdAt) }}</span>
          </div>
          <a-button
            v-if="feed.author.id !== currentUserId"
            type="link"
            size="small"
            @click="handleFollow"
          >
            {{ feed.isFollow ? '已关注' : '关注' }}
          </a-button>
        </div>
      </template>
      <template #description>
        <div class="feed-content">
          <p class="feed-title">{{ feed.item.title }}</p>
          <div class="feed-stats">
            <a-button type="text" :class="{ active: feed.isLike }" @click="handleLike">
              <LikeOutlined />
              {{ feed.statistics.likeCount || 0 }}
            </a-button>
            <a-button type="text" @click="showCommentModal = true">
              <CommentOutlined />
              {{ feed.statistics.commentCount || 0 }}
            </a-button>
            <a-button type="text" :class="{ active: feed.isCollect }" @click="handleCollect">
              <StarOutlined />
              {{ feed.statistics.collectCount || 0 }}
            </a-button>
            <a-button type="text">
              <EyeOutlined />
              {{ feed.statistics.viewCount || 0 }}
            </a-button>
          </div>
        </div>
      </template>
    </a-card-meta>
    <CommentModal
      v-model:open="showCommentModal"
      :item-id="feed.item.id"
      @update="handleUpdate"
    />
  </a-card>
</template>

<script setup lang="ts">
import { ref, h } from 'vue'
import { message } from 'ant-design-vue'
import {
  UserOutlined,
  LikeOutlined,
  CommentOutlined,
  StarOutlined,
  EyeOutlined,
} from '@ant-design/icons-vue'
import CommentModal from './CommentModal.vue'
import { follow, cancelFollow, collect, cancelCollect } from '../services/interactionService'
import dayjs from 'dayjs'
import type { FeedVo } from '../services/feedService'

const props = defineProps<{
  feed: FeedVo
  currentUserId?: number
}>()

const emit = defineEmits<{
  update: []
}>()

const showCommentModal = ref(false)

const formatTime = (time: string) => {
  return dayjs(time).format('YYYY-MM-DD HH:mm')
}

const handleFollow = async () => {
  if (!props.currentUserId) return

  try {
    if (props.feed.isFollow) {
      await cancelFollow(props.currentUserId, props.feed.author.id)
      message.success('取消关注成功')
    } else {
      await follow(props.currentUserId, props.feed.author.id)
      message.success('关注成功')
    }
    emit('update')
  } catch (error) {
    message.error('操作失败')
  }
}

const handleLike = async () => {
  // TODO: 实现点赞功能
  message.info('点赞功能待实现')
}

const handleCollect = async () => {
  if (!props.currentUserId) return

  try {
    if (props.feed.isCollect) {
      await cancelCollect(props.currentUserId, props.feed.item.id)
      message.success('取消收藏成功')
    } else {
      await collect(props.currentUserId, props.feed.item.id)
      message.success('收藏成功')
    }
    emit('update')
  } catch (error) {
    message.error('操作失败')
  }
}

const handleUpdate = () => {
  emit('update')
}
</script>

<style scoped>
.feed-card {
  margin-bottom: 20px;
  border-radius: 12px;
  overflow: hidden;
}

.feed-media {
  width: 100%;
  background: #000;
}

.feed-video {
  width: 100%;
  max-height: 500px;
  display: block;
}

.feed-header {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 12px;
}

.feed-author {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.author-name {
  font-weight: 500;
  font-size: 14px;
}

.author-time {
  font-size: 12px;
  color: #999;
}

.feed-content {
  padding-top: 12px;
}

.feed-title {
  margin: 0 0 12px 0;
  font-size: 15px;
  line-height: 1.6;
}

.feed-stats {
  display: flex;
  gap: 16px;
}

.feed-stats .ant-btn {
  padding: 0;
  height: auto;
}

.feed-stats .ant-btn.active {
  color: #1890ff;
}
</style>

