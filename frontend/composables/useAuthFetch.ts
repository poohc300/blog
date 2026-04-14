export function useAuthFetch() {
  const { clearUser } = useAuth()

  function getHeaders(): Record<string, string> {
    if (typeof window === 'undefined') return {}
    const token = localStorage.getItem('auth_token')
    return token ? { Authorization: `Bearer ${token}` } : {}
  }

  async function authFetch(url: string, options: Record<string, any> = {}): Promise<any> {
    try {
      return await $fetch(url, {
        ...options,
        headers: {
          ...getHeaders(),
          ...(options.headers ?? {}),
        },
      })
    } catch (e: any) {
      if (e?.response?.status === 401 || e?.response?.status === 403) {
        clearUser()
        await navigateTo('/login')
        return
      }
      throw e
    }
  }

  return { authFetch }
}
