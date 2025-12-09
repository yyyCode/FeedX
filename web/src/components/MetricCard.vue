<template>
  <el-card shadow="hover" class="metric">
    <div class="metric__title">{{ title }}</div>
    <div class="metric__value">{{ value }}</div>
    <div class="metric__desc" v-if="desc">{{ desc }}</div>
  </el-card>
</template>

<script setup>
defineProps({
  title: { type: String, required: true },
  value: { type: [String, Number], required: true },
  desc: { type: String, default: '' }
});
</script>

<style scoped>
.metric {
  width: 100%;
}

.metric__title {
  color: #6b7280;
  font-size: 13px;
}

.metric__value {
  font-size: 28px;
  font-weight: 700;
  margin: 6px 0;
}

.metric__desc {
  color: #9ca3af;
  font-size: 12px;
}
</style>

