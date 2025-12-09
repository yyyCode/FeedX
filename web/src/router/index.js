import { createRouter, createWebHistory } from 'vue-router';
import Dashboard from '../views/Dashboard.vue';
import Feed from '../views/Feed.vue';
import Items from '../views/Items.vue';
import Login from '../views/Login.vue';

const routes = [
  { path: '/', redirect: '/dashboard' },
  { path: '/dashboard', name: 'Dashboard', component: Dashboard, meta: { requiresAuth: false } },
  { path: '/feed', name: 'Feed', component: Feed, meta: { requiresAuth: false } },
  { path: '/items', name: 'Items', component: Items, meta: { requiresAuth: false } },
  { path: '/login', name: 'Login', component: Login, meta: { requiresAuth: false } }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

router.beforeEach((to, from, next) => {
  if (to.meta.requiresAuth && !localStorage.getItem('token')) {
    next({ name: 'Login', query: { redirect: to.fullPath } });
    return;
  }
  next();
});

export default router;

