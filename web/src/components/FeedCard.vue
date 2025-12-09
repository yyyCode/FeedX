<template>
  <el-card class="feed-card" shadow="hover">
    <div class="feed-card__header">
      <div>
        <div class="feed-card__author">{{ feed.author?.name || '未知作者' }}</div>
        <div class="feed-card__meta">Item #{{ feed.item?.id }}</div>
      </div>
      <el-tag type="info" v-if="feed.item?.createdAt">创建于 {{ feed.item.createdAt }}</el-tag>
    </div>
    <div class="feed-card__body">
      <div class="feed-card__title">{{ feed.item?.title || feed.item?.description || '暂无标题' }}</div>
      <div class="feed-card__stats">
        <span>点赞 {{ feed.statistics?.likeCount ?? 0 }}</span>
        <span>评论 {{ feed.statistics?.commentCount ?? 0 }}</span>
        <span>收藏 {{ feed.statistics?.collectCount ?? 0 }}</span>
        <span>播放 {{ feed.statistics?.viewCount ?? 0 }}</span>
      </div>
    </div>
  </el-card>
</template>

<script setup>
defineProps({
  feed: { type: Object, required: true }
});
</script>

<style scoped>
.feed-card {
  margin-bottom: 12px;
}

.feed-card__header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 8px;
}

.feed-card__author {
  font-weight: 700;
  color: #111827;
}

.feed-card__meta {
  font-size: 12px;
  color: #9ca3af;
}

.feed-card__title {
  font-size: 14px;
  margin-bottom: 8px;
}

.feed-card__stats {
  display: flex;
  gap: 12px;
  font-size: 12px;
  color: #6b7280;
}
</style>

