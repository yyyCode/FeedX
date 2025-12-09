<template>
  <div class="page">
    <h2>核心指标</h2>
    <el-row :gutter="12" class="grid">
      <el-col :span="6"><MetricCard title="新增用户" :value="numbers.users" desc="近24小时" /></el-col>
      <el-col :span="6"><MetricCard title="新增内容" :value="numbers.items" desc="近24小时" /></el-col>
      <el-col :span="6"><MetricCard title="互动总量" :value="numbers.interactions" desc="点赞/收藏/评论" /></el-col>
      <el-col :span="6"><MetricCard title="平均下发延迟" :value="numbers.latency + ' ms'" desc="Kafka/Redis链路" /></el-col>
    </el-row>

    <el-card shadow="never" class="chart-card">
      <template #header>流量趋势</template>
      <div ref="chartRef" class="chart"></div>
    </el-card>
  </div>
</template>

<script setup>
import { onMounted, onUnmounted, ref } from 'vue';
import * as echarts from 'echarts';
import MetricCard from '../components/MetricCard.vue';

const chartRef = ref(null);
let chartInstance = null;

const numbers = {
  users: 128,
  items: 342,
  interactions: 9821,
  latency: 38
};

const renderChart = () => {
  if (!chartRef.value) return;
  chartInstance = echarts.init(chartRef.value);
  chartInstance.setOption({
    tooltip: { trigger: 'axis' },
    xAxis: { type: 'category', data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'] },
    yAxis: { type: 'value' },
    series: [
      { name: 'PV', type: 'line', smooth: true, data: [120, 132, 101, 134, 90, 230, 210] },
      { name: '互动', type: 'bar', data: [220, 182, 191, 234, 290, 330, 310] }
    ]
  });
};

onMounted(() => {
  renderChart();
  window.addEventListener('resize', () => chartInstance?.resize());
});

onUnmounted(() => {
  chartInstance?.dispose();
});
</script>

<style scoped>
.page h2 {
  margin: 0 0 12px;
}

.grid {
  margin-bottom: 16px;
}

.chart-card {
  margin-top: 8px;
}

.chart {
  width: 100%;
  height: 320px;
}
</style>

