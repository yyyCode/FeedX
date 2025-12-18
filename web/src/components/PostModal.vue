<template>
  <a-modal
    v-model:open="visible"
    title="发布内容"
    :confirm-loading="loading"
    @ok="handleSubmit"
    @cancel="handleCancel"
  >
    <a-form :model="formState" layout="vertical">
      <a-form-item label="标题" required>
        <a-input
          v-model:value="formState.title"
          placeholder="请输入标题"
          :maxlength="100"
          show-count
        />
      </a-form-item>
      <a-form-item label="上传视频" required>
        <a-upload
          v-model:file-list="fileList"
          :before-upload="beforeUpload"
          accept="video/*"
          :max-count="1"
        >
          <a-button>
            <UploadOutlined />
            选择视频
          </a-button>
        </a-upload>
        <div v-if="previewUrl" class="preview-container">
          <video :src="previewUrl" controls style="max-width: 100%; max-height: 300px" />
        </div>
      </a-form-item>
      <a-form-item label="可见性">
        <a-radio-group v-model:value="formState.status">
          <a-radio :value="1">公开</a-radio>
          <a-radio :value="0">私密</a-radio>
        </a-radio-group>
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script setup lang="ts">
import { ref, reactive, watch } from 'vue'
import { message } from 'ant-design-vue'
import { UploadOutlined } from '@ant-design/icons-vue'
import { postItem } from '../services/itemService'
import { getUserInfo } from '../utils/auth'
import type { UploadFile } from 'ant-design-vue'

const emit = defineEmits<{
  success: []
}>()

const visible = defineModel<boolean>('open', { default: false })
const loading = ref(false)
const fileList = ref<UploadFile[]>([])
const previewUrl = ref<string>('')
const formState = reactive({
  title: '',
  status: 1,
})

const beforeUpload = (file: File) => {
  const isVideo = file.type.startsWith('video/')
  if (!isVideo) {
    message.error('只能上传视频文件!')
    return false
  }
  const isLt100M = file.size / 1024 / 1024 < 100
  if (!isLt100M) {
    message.error('视频大小不能超过100MB!')
    return false
  }
  previewUrl.value = URL.createObjectURL(file)
  return false // 阻止自动上传
}

const handleSubmit = async () => {
  if (!formState.title.trim()) {
    message.error('请输入标题')
    return
  }
  if (fileList.value.length === 0) {
    message.error('请选择视频文件')
    return
  }

  const userInfo = getUserInfo()
  if (!userInfo?.id) {
    message.error('请先登录')
    return
  }

  loading.value = true
  try {
    const file = fileList.value[0].originFileObj
    if (!file) {
      throw new Error('文件不存在')
    }
    await postItem(file, {
      title: formState.title,
      userId: userInfo.id,
      status: formState.status,
    })
    message.success('发布成功')
    handleCancel()
    emit('success')
  } catch (error: any) {
    message.error(error.message || '发布失败')
  } finally {
    loading.value = false
  }
}

const handleCancel = () => {
  if (previewUrl.value) {
    URL.revokeObjectURL(previewUrl.value)
  }
  visible.value = false
  formState.title = ''
  formState.status = 1
  fileList.value = []
  previewUrl.value = ''
}

watch(visible, (newVal) => {
  if (!newVal) {
    handleCancel()
  }
})
</script>

<style scoped>
.preview-container {
  margin-top: 12px;
  text-align: center;
}
</style>

