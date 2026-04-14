export function useAuthFetch() {
  function getHeaders(): Record<string, string> {
    const token = import.meta.client ? localStorage.getItem('auth_token') : null
    return token ? { Authorization: `Bearer ${token}` } : {}
  }

  function authFetch<T = any>(url: string, options: Record<string, any> = {}): Promise<T> {
    return $fetch<T>(url, {
      ...options,
      headers: {
        ...getHeaders(),
        ...(options.headers ?? {}),
      },
    })
  }

  return { authFetch }
}
