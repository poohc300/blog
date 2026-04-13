<template>
  <div class="w-full max-w-screen-xl mx-auto px-4 sm:px-6 lg:px-10 xl:px-16 py-8 sm:py-12">

    <template v-if="post">
      <!-- 본문 -->
      <ArticleBody
        :post="post"
        @edit="openEdit"
        @delete="handleDelete"
      />

      <!-- 구분선 -->
      <div class="max-w-2xl mx-auto mt-16 mb-10 border-t border-gray-200" />

      <!-- 다른 글 -->
      <div class="max-w-2xl mx-auto">
        <h3 class="text-base font-bold text-black mb-6">다른 글</h3>
        <div class="divide-y divide-gray-100">
          <PostCard
            v-for="related in relatedPosts" :key="related.id"
            :post="related"
            :compact="true"
          />
        </div>
      </div>
    </template>

  </div>
</template>

<script setup lang="ts">
import { formatDate, calcReadTime } from '~/utils/post'

const route = useRoute()
const base = useApiBase()
const id = route.params.id

const { data: raw } = await useFetch<any>(`${base}/api/posts/${id}`)
const { data: allRaw } = await useFetch<any[]>(`${base}/api/posts`)

const post = computed(() => {
  if (!raw.value) return null
  const p = raw.value
  return {
    id: p.id,
    authorId: p.authorId,
    title: p.title,
    body: p.body ?? '',
    summary: p.summary ?? '',
    date: formatDate(p.createdAt),
    createdAt: p.createdAt,
    tag: p.tag,
    readTime: calcReadTime(p.body ?? ''),
  }
})

// ── SEO ────────────────────────────────────────────────────
useSeoMeta({
  title: () => post.value ? `${post.value.title} | Jeremy.dev` : 'Jeremy.dev',
  description: () => post.value?.summary || '',
  ogTitle: () => post.value?.title || '',
  ogDescription: () => post.value?.summary || '',
  ogType: 'article',
  ogUrl: () => `https://blog.nexacromancer.win/posts/${id}`,
  twitterCard: 'summary',
})

// JSON-LD 구조화 데이터 (Google 리치 결과용)
useHead({
  script: [
    {
      type: 'application/ld+json',
      innerHTML: computed(() => post.value ? JSON.stringify({
        '@context': 'https://schema.org',
        '@type': 'BlogPosting',
        headline: post.value.title,
        description: post.value.summary,
        datePublished: post.value.createdAt,
        author: { '@type': 'Person', name: 'Jeremy' },
        publisher: { '@type': 'Person', name: 'Jeremy' },
        url: `https://blog.nexacromancer.win/posts/${id}`,
      }) : ''),
    },
  ],
})

const relatedPosts = computed(() =>
  (allRaw.value ?? [])
    .filter(p => p.id !== Number(id))
    .slice(0, 3)
    .map(p => ({
      id: p.id,
      title: p.title,
      summary: p.summary ?? '',
      date: formatDate(p.createdAt),
      tag: p.tag,
      readTime: calcReadTime(p.summary ?? ''),
    }))
)

function openEdit() {
  // TODO: EditPost 모달 연결
}

async function handleDelete() {
  // TODO: DELETE API 연결
}
</script>
