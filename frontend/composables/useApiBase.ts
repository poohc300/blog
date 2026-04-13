export function useApiBase() {
  const config = useRuntimeConfig()
  return import.meta.server ? config.apiBaseUrl : (config.public.apiBaseUrl || '')
}
