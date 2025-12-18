export interface UserInfo {
  id: number
  username: string
  nickName?: string
  phoneNumber?: string
  email?: string
  gender?: number
}

export const setToken = (token: string) => {
  localStorage.setItem('token', token)
}

export const getToken = (): string | null => {
  return localStorage.getItem('token')
}

export const setUserInfo = (userInfo: UserInfo) => {
  localStorage.setItem('userInfo', JSON.stringify(userInfo))
}

export const getUserInfo = (): UserInfo | null => {
  const userInfoStr = localStorage.getItem('userInfo')
  if (userInfoStr) {
    try {
      return JSON.parse(userInfoStr)
    } catch {
      return null
    }
  }
  return null
}

export const clearAuth = () => {
  localStorage.removeItem('token')
  localStorage.removeItem('userInfo')
}

export const isAuthenticated = (): boolean => {
  return !!getToken()
}

