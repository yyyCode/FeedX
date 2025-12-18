import api from '../utils/api'

// 关注
export const follow = async (followerId: number, followingId: number) => {
  return api.post(`/api/follow/${followerId}/${followingId}`)
}

// 取消关注
export const cancelFollow = async (followerId: number, followingId: number) => {
  return api.delete(`/api/follow/${followerId}/${followingId}`)
}

// 收藏
export const collect = async (userId: number, itemId: number) => {
  return api.post(`/api/collect/${userId}/${itemId}`)
}

// 取消收藏
export const cancelCollect = async (userId: number, itemId: number) => {
  return api.delete(`/api/collect/${userId}/${itemId}`)
}

// 评论
export interface CommentParams {
  itemId: number
  userId: number
  content: string
  parentId?: number
}

export const addComment = async (params: CommentParams) => {
  return api.post('/api/comment/comment', params)
}

// 获取评论列表
export const getComments = async (itemId: number, current: number = 1) => {
  const response = await api.get('/api/comment/comment', {
    params: { itemId, current },
  })
  return response.data || []
}

