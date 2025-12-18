<template>
  <div class="login-container">
    <a-card class="login-card" title="FeedX">
      <a-form
        :model="formState"
        @finish="onFinish"
        layout="vertical"
      >
        <a-form-item
          label="账号"
          name="account"
          :rules="[{ required: true, message: '请输入账号!' }]"
        >
          <a-input
            v-model:value="formState.account"
            placeholder="请输入账号"
            size="large"
          >
            <template #prefix>
              <UserOutlined />
            </template>
          </a-input>
        </a-form-item>

        <a-form-item
          label="密码"
          name="password"
          :rules="[{ required: true, message: '请输入密码!' }]"
        >
          <a-input-password
            v-model:value="formState.password"
            placeholder="请输入密码"
            size="large"
          >
            <template #prefix>
              <LockOutlined />
            </template>
          </a-input-password>
        </a-form-item>

        <a-form-item
          label="验证码Key"
          name="key"
          :rules="[{ required: true, message: '请输入验证码key!' }]"
        >
          <a-input
            v-model:value="formState.key"
            placeholder="请输入验证码key"
            size="large"
          />
        </a-form-item>

        <a-form-item
          label="验证码"
          name="verityCode"
          :rules="[{ required: true, message: '请输入验证码!' }]"
        >
          <a-input
            v-model:value="formState.verityCode"
            placeholder="请输入验证码"
            size="large"
          />
        </a-form-item>

        <a-form-item>
          <a-button
            type="primary"
            html-type="submit"
            block
            size="large"
            :loading="loading"
          >
            登录
          </a-button>
        </a-form-item>
        <a-form-item>
          <a-button
            type="link"
            block
            @click="quickLogin"
            :loading="loading"
          >
            快速体验（跳过登录）
          </a-button>
        </a-form-item>
      </a-form>
    </a-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { message } from 'ant-design-vue'
import { UserOutlined, LockOutlined } from '@ant-design/icons-vue'
import { login } from '../services/authService'

const router = useRouter()
const loading = ref(false)
const formState = reactive({
  account: '',
  password: '',
  key: '',
  verityCode: '',
})

const onFinish = async () => {
  loading.value = true
  try {
    await login(formState)
    message.success('登录成功')
    router.push('/home')
  } catch (error: any) {
    message.error(error.message || '登录失败，请检查账号密码')
  } finally {
    loading.value = false
  }
}

// 快速体验，跳过登录直接进入主页
const quickLogin = () => {
  // 设置一个测试用户信息（不调用后端接口）
  const testUserInfo = {
    id: 123456,
    username: 'test_user',
    nickName: '测试用户',
  }
  // 设置一个假的token
  localStorage.setItem('token', 'test_token_' + Date.now())
  localStorage.setItem('userInfo', JSON.stringify(testUserInfo))
  message.success('已进入体验模式')
  router.push('/home')
}
</script>

<style scoped>
.login-container {
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.login-card {
  width: 400px;
  box-shadow: 0 10px 40px rgba(0, 0, 0, 0.1);
}

.login-card :deep(.ant-card-head-title) {
  font-size: 24px;
  font-weight: bold;
  text-align: center;
}
</style>

