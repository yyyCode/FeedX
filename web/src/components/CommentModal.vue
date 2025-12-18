<template>
  <a-modal
    v-model:open="visible"
    title="评论"
    :footer="null"
    :width="600"
    @cancel="handleCancel"
  >
    <div class="comment-list">
      <a-spin :spinning="loading">
        <a-empty v-if="!loading && comments.length === 0" description="暂无评论" />
        <div v-else class="comments">
          <div v-for="comment in comments" :key="comment.id" class="comment-item">
            <a-avatar :size="32" :icon="h(UserOutlined)" />
            <div class="comment-content">
              <div class="comment-header">
                <span class="comment-author">{{ comment.userId }}</span>
                <span class="comment-time">{{ formatTime(comment.createdAt) }}</span>
              </div>
              <p class="comment-text">{{ comment.content }}</p>
            </div>
          </div>
        </div>
      </a-spin>
    </div>
    <a-divider />
    <a-form @finish="handleSubmit" layout="inline">
      <a-form-item style="flex: 1">
        <a-input
          v-model:value="commentText"
          placeholder="写下你的评论..."
          @pressEnter="handleSubmit"
        />
      </a-form-item>
      <a-form-item>
        <a-button type="primary" html-type="submit" :loading="submitting">
          发送
        </a-button>
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script setup lang="ts">
import { ref, watch, h } from 'vue'
import { message } from 'ant-design-vue'
import { UserOutlined } from '@ant-design/icons-vue'
import { addComment, getComments } from '../services/interactionService'
import { getUserInfo } from '../utils/auth'
import dayjs from 'dayjs'

const props = defineProps<{
  itemId: number
}>()

const emit = defineEmits<{
  'update:open': [value: boolean]
  update: []
}>()

const visible = defineModel<boolean>('open', { default: false })
const comments = ref<any[]>([])
const loading = ref(false)
const commentText = ref('')
const submitting = ref(false)
const userInfo = getUserInfo()

const loadComments = async () => {
  loading.value = true
  try {
    const data = await getComments(props.itemId, 1)
    comments.value = data
  } catch (error) {
    console.error('加载评论失败:', error)
  } finally {
    loading.value = false
  }
}

const handleSubmit = async () => {
  if (!commentText.value.trim() || !userInfo?.id) return

  submitting.value = true
  try {
    await addComment({
      itemId: props.itemId,
      userId: userInfo.id,
      content: commentText.value,
    })
    message.success('评论成功')
    commentText.value = ''
    loadComments()
    emit('update')
  } catch (error) {
    message.error('评论失败')
  } finally {
    submitting.value = false
  }
}

const handleCancel = () => {
  visible.value = false
}

const formatTime = (time: string) => {
  return dayjs(time).format('YYYY-MM-DD HH:mm')
}

watch(visible, (newVal) => {
  if (newVal) {
    loadComments()
  }
})
</script>

<style scoped>
.comment-list {
  max-height: 400px;
  overflow-y: auto;
  margin-bottom: 16px;
}

.comments {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.comment-item {
  display: flex;
  gap: 12px;
}

.comment-content {
  flex: 1;
}

.comment-header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 4px;
}

.comment-author {
  font-weight: 500;
  font-size: 14px;
}

.comment-time {
  font-size: 12px;
  color: #999;
}

.comment-text {
  margin: 0;
  font-size: 14px;
  line-height: 1.6;
}
</style>

