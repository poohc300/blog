export default defineEventHandler(async (event) => {
  const config = useRuntimeConfig()
  const base = config.apiBaseUrl || 'http://localhost:8082'
  const siteUrl = 'https://blog.nexacromancer.win'

  const posts = await $fetch<{ id: number; updatedAt?: string; createdAt?: string }[]>(
    `${base}/api/posts`
  ).catch(() => [])

  const postUrls = posts.map(post => `
  <url>
    <loc>${siteUrl}/posts/${post.id}</loc>
    <lastmod>${(post.updatedAt ?? post.createdAt ?? '').slice(0, 10)}</lastmod>
    <changefreq>weekly</changefreq>
    <priority>0.8</priority>
  </url>`).join('')

  const xml = `<?xml version="1.0" encoding="UTF-8"?>
<urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">
  <url>
    <loc>${siteUrl}/</loc>
    <changefreq>daily</changefreq>
    <priority>1.0</priority>
  </url>${postUrls}
</urlset>`

  setHeader(event, 'Content-Type', 'application/xml')
  return xml
})
