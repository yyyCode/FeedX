import axios from 'axios';

const http = axios.create({
  baseURL: '/',
  timeout: 15000
});

http.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = token;
  }
  return config;
});

http.interceptors.response.use(
  (resp) => resp,
  (error) => {
    console.error('请求失败', error);
    return Promise.reject(error);
  }
);

export default http;

