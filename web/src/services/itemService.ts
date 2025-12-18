import api from '../utils/api'

export interface PostItemParams {
  title: string
  userId: number
  status: number
}

export const postItem = async (file: File, params: PostItemParams) => {
  const formData = new FormData()
  formData.append('file', file)
  formData.append(
    'itemBo',
    JSON.stringify({
      title: params.title,
      userId: params.userId,
      status: params.status,
    })
  )

  const response = await api.post('/api/item/post', formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  })

  return response.data
}

export const getUserItems = async (userId: string) => {
  const response = await api.get(`/api/item/list/${userId}`)
  return response.data || []
}

