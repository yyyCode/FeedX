<template>
  <div class="page">
    <h2>Feed 流查询</h2>
    <el-form :inline="true" :model="form" class="form">
      <el-form-item label="用户ID">
        <el-input v-model="form.userId" placeholder="输入用户ID" style="width: 200px" />
      </el-form-item>
      <el-form-item label="条数">
        <el-input-number v-model="form.size" :min="1" :max="50" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" :loading="loading" @click="loadFeed">拉取</el-button>
      </el-form-item>
    </el-form>

    <el-empty description="暂无数据" v-if="!loading && feeds.length === 0" />
    <div v-else>
      <FeedCard v-for="item in feeds" :key="item.item?.id" :feed="item" />
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue';
import { ElMessage } from 'element-plus';
import { fetchFeed } from '../api/feed';
import FeedCard from '../components/FeedCard.vue';

const form = reactive({
  userId: '',
  size: 6
});

const feeds = ref([]);
const loading = ref(false);

const loadFeed = async () => {
  if (!form.userId) {
    ElMessage.warning('请填写用户ID');
    return;
  }
  loading.value = true;
  try {
    const { data } = await fetchFeed(form.userId, form.size);
    feeds.value = data?.data || [];
  } catch (err) {
    ElMessage.error('拉取失败，请检查后端接口是否可用');
    console.error(err);
  } finally {
    loading.value = false;
  }
};
</script>

<style scoped>
.form {
  margin-bottom: 12px;
}
</style>

