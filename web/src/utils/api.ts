import axios from 'axios'

const api = axios.create({
  baseURL: '/',
  timeout: 10000,
})

// 请求拦截器
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token')
    if (token) {
      config.headers['satoken'] = token
    }
    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)

// 响应拦截器
api.interceptors.response.use(
  (response) => {
    const res = response.data
    if (res.code === 200 || res.code === 0) {
      return res
    } else {
      console.error('API Error:', res.msg || res.message)
      return Promise.reject(new Error(res.msg || res.message || '请求失败'))
    }
  },
  (error) => {
    console.error('Request Error:', error)
    if (error.response?.status === 401) {
      localStorage.removeItem('token')
      localStorage.removeItem('userInfo')
      window.location.href = '/login'
    }
    return Promise.reject(error)
  }
)

export default api

