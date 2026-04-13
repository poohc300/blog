interface AuthUser {
  id: number
  username: string
}

const user = ref<AuthUser | null>(null)

function loadFromStorage() {
  const stored = localStorage.getItem('auth_user')
  user.value = stored ? JSON.parse(stored) : null
}

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

export function useAuth() {
  if (import.meta.client && user.value === null) {
    loadFromStorage()
  }

  const isAuthor = (authorId: number) => user.value?.id === authorId

  return { user: readonly(user), isAuthor, setUser, clearUser }
}
