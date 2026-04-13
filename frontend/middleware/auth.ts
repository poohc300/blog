// /admin 하위 경로 접근 시 로그인 여부 확인
export default defineNuxtRouteMiddleware(() => {
  if (import.meta.server) return

  const token = localStorage.getItem('auth_token')
  if (!token) {
    return navigateTo('/login')
  }
})
