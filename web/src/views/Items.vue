<template>
  <div class="page">
    <h2>内容发布</h2>
    <el-form label-width="120px" :model="form" class="form" enctype="multipart/form-data">
      <el-form-item label="标题">
        <el-input v-model="form.title" placeholder="标题" />
      </el-form-item>
      <el-form-item label="描述">
        <el-input type="textarea" v-model="form.description" placeholder="描述" />
      </el-form-item>
      <el-form-item label="所属用户ID">
        <el-input v-model="form.userId" placeholder="作者 userId" />
      </el-form-item>
      <el-form-item label="视频文件">
        <input type="file" @change="onFileChange" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" :loading="loading" @click="submit">上传</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue';
import { ElMessage } from 'element-plus';
import { uploadItem } from '../api/item';

const form = reactive({
  title: '',
  description: '',
  userId: ''
});

const fileRef = ref(null);
const loading = ref(false);

const onFileChange = (evt) => {
  const [file] = evt.target.files || [];
  fileRef.value = file;
};

const submit = async () => {
  if (!fileRef.value || !form.userId) {
    ElMessage.warning('请填入用户ID并选择文件');
    return;
  }
  const data = new FormData();
  data.append('file', fileRef.value);
  data.append('title', form.title);
  data.append('description', form.description);
  data.append('userId', form.userId);

  loading.value = true;
  try {
    await uploadItem(data);
    ElMessage.success('上传成功');
  } catch (err) {
    console.error(err);
    ElMessage.error('上传失败，请检查网关或后端接口');
  } finally {
    loading.value = false;
  }
};
</script>

<style scoped>
.form {
  max-width: 520px;
}
</style>

