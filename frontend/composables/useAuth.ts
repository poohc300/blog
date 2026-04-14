interface AuthUser {
  id: number
  username: string
}

export function useAuth() {
  // useState: 서버에서는 요청마다 독립적, 클라이언트에서는 전역 공유
  const user = useState<AuthUser | null>('auth:user', () => null)

  // 클라이언트에서만 localStorage에서 복원
  if (import.meta.client && user.value === null) {
    const stored = localStorage.getItem('auth_user')
    user.value = stored ? JSON.parse(stored) : null
  }

  const isLoggedIn = computed(() => user.value !== null)
  const isAuthor = (authorId: number) => user.value?.id === authorId

  function setUser(data: AuthUser, token: string) {
    user.value = data
    localStorage.setItem('auth_user', JSON.stringify(data))
    localStorage.setItem('auth_token', token)
  }

  function clearUser() {
    user.value = null
    localStorage.removeItem('auth_user')
    localStorage.removeItem('auth_token')
  }

  return { user: readonly(user), isLoggedIn, isAuthor, setUser, clearUser }
}
