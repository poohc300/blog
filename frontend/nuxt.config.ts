export default defineNuxtConfig({
  devtools: { enabled: true },
  typescript: {
    strict: true,
    typeCheck: true
  },
  modules: [
    '@vueuse/nuxt',
    '@pinia/nuxt',
    '@nuxtjs/sitemap',
  ],

  site: {
    url: 'https://blog.nexacromancer.win',
    name: 'Jeremy.dev',
  },

  sitemap: {
    // 동적 포스트 URL을 API에서 가져와서 sitemap에 포함
    sources: ['/api/__sitemap__/urls'],
  },
  css: ['~/assets/css/main.css'],
  postcss: {
    plugins: {
      tailwindcss: {},
      autoprefixer: {},
    },
  },

  runtimeConfig: {
    apiBaseUrl: process.env.API_BASE_URL || 'http://localhost:8082',
    public: {
      apiBaseUrl: process.env.NUXT_PUBLIC_API_BASE_URL || ''
    }
  },
  compatibilityDate: '2024-11-01'
})
