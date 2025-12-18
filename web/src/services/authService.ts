import api from '../utils/api'
import { setToken, setUserInfo, UserInfo } from '../utils/auth'

export interface LoginParams {
  account: string
  password: string
  key: string
  verityCode: string
}

export interface LoginResponse {
  userInfo: UserInfo | number
  tokenInfo: {
    tokenName: string
    tokenValue: string
  }
}

export const login = async (params: LoginParams) => {
  const formData = new URLSearchParams()
  formData.append('account', params.account)
  formData.append('password', params.password)
  formData.append('key', params.key)
  formData.append('verityCode', params.verityCode)

  const response = await api.post<LoginResponse>('/auth/login', formData, {
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded',
    },
  })

  if (response.data) {
    const { userInfo, tokenInfo } = response.data
    setToken(tokenInfo.tokenValue)
    // 处理userInfo可能是数字的情况（后端bug）
    if (typeof userInfo === 'number') {
      // 如果后端只返回ID，创建一个临时用户对象
      setUserInfo({
        id: userInfo,
        username: params.account,
      })
    } else {
      setUserInfo(userInfo)
    }
  }

  return response.data
}

