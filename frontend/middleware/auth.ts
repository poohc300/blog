export default defineNuxtRouteMiddleware(() => {
  if (import.meta.server) return

  const token = localStorage.getItem('auth_token')
  if (!token) {
    return navigateTo('/login')
  }

  // JWT payload 디코딩 후 만료 시각 확인
  try {
    const payload = JSON.parse(atob(token.split('.')[1]))
    const isExpired = payload.exp * 1000 < Date.now()
    if (isExpired) {
      localStorage.removeItem('auth_token')
      localStorage.removeItem('auth_user')
      return navigateTo('/login')
    }
  } catch {
    // 디코딩 실패 = 유효하지 않은 토큰
    localStorage.removeItem('auth_token')
    localStorage.removeItem('auth_user')
    return navigateTo('/login')
  }
})
