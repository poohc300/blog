export function useAuthFetch() {
  function getHeaders(): Record<string, string> {
    const token = import.meta.client ? localStorage.getItem('auth_token') : null
    return token ? { Authorization: `Bearer ${token}` } : {}
  }

  function authFetch(url: string, options: Record<string, any> = {}): Promise<any> {
    return $fetch(url, {
      ...options,
      headers: {
        ...getHeaders(),
        ...(options.headers ?? {}),
      },
    }) as Promise<any>
  }

  return { authFetch }
}
