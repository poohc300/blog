// sitemap 모듈이 동적 URL 수집 시 호출하는 엔드포인트
// 모든 포스트 URL을 반환해 sitemap.xml에 포함시킴
export default defineSitemapEventHandler(async () => {
  const config = useRuntimeConfig()
  const base = config.apiBaseUrl || 'http://localhost:8082'

  const posts = await $fetch<{ id: number; updatedAt?: string; createdAt?: string }[]>(
    `${base}/api/posts`
  ).catch(() => [])

  return posts.map(post => ({
    loc: `/posts/${post.id}`,
    lastmod: post.updatedAt ?? post.createdAt,
    changefreq: 'weekly' as const,
    priority: 0.8,
  }))
})
