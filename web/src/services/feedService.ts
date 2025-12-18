import api from '../utils/api'

export interface ItemDTO {
  id: number
  userId: number
  title: string
  videoUrl: string
  likeNum: number
  createdAt: string
  updatedAt: string
  status: number
}

export interface UserDTO {
  id: number
  username: string
  nickName?: string
  phoneNumber?: string
  email?: string
  gender?: number
}

export interface StatisticsVo {
  collectCount: number
  commentCount: number
  likeCount: number
  viewCount: number
}

export interface FeedVo {
  item: ItemDTO
  author: UserDTO
  statistics: StatisticsVo
  isLike?: number
  isCollect?: number
  isFollow?: number
}

export const getFeed = async (userId: number, size: number = 6): Promise<FeedVo[]> => {
  const response = await api.get<FeedVo[]>(`/feed/${userId}`, {
    params: { size },
  })
  return response.data || []
}

