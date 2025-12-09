<template>
  <div class="login">
    <el-card class="login__card" shadow="hover">
      <h3>登录 FeedX</h3>
      <el-form :model="form" label-position="top">
        <el-form-item label="账号">
          <el-input v-model="form.account" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password" type="password" show-password />
        </el-form-item>
        <el-button type="primary" :loading="loading" @click="doLogin" style="width: 100%">登录</el-button>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';
import { login } from '../api/auth';

const form = reactive({
  account: '',
  password: ''
});

const loading = ref(false);
const router = useRouter();
const route = useRoute();

const doLogin = async () => {
  if (!form.account || !form.password) {
    ElMessage.warning('请输入账号和密码');
    return;
  }
  loading.value = true;
  try {
    const { data } = await login(form.account, form.password);
    const token = data?.data?.tokenInfo?.tokenValue || data?.data?.tokenInfo?.token || '';
    if (token) {
      localStorage.setItem('token', token);
      ElMessage.success('登录成功');
      router.push(route.query.redirect || '/dashboard');
    } else {
      ElMessage.error('未获取到 token，请检查后端返回');
    }
  } catch (err) {
    console.error(err);
    ElMessage.error('登录失败，请检查网关接口');
  } finally {
    loading.value = false;
  }
};
</script>

<style scoped>
.login {
  display: flex;
  align-items: center;
  justify-content: center;
  height: calc(100vh - 64px);
}

.login__card {
  width: 360px;
}
</style>

