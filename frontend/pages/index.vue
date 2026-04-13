<template>
  <div class="w-full max-w-screen-xl mx-auto px-4 sm:px-6 lg:px-10 xl:px-16 py-8 sm:py-12">

    <!-- Tag Filter -->
    <div class="flex gap-2 mb-8 sm:mb-10 overflow-x-auto pb-1">
      <button
        class="px-4 py-1.5 rounded-full text-sm whitespace-nowrap transition-colors"
        :class="selectedTag === null ? 'bg-black text-white' : 'border border-gray-200 text-gray-600 hover:border-black hover:text-black'"
        @click="selectedTag = null"
      >
        전체
      </button>
      <button
        v-for="tag in tags" :key="tag"
        class="px-4 py-1.5 rounded-full text-sm whitespace-nowrap transition-colors"
        :class="selectedTag === tag ? 'bg-black text-white' : 'border border-gray-200 text-gray-600 hover:border-black hover:text-black'"
        @click="selectedTag = tag"
      >
        {{ tag }}
      </button>
    </div>

    <!-- Post List -->
    <div class="divide-y divide-gray-100">
      <PostCard
        v-for="post in filteredPosts" :key="post.id"
        :post="post"
      />
    </div>

  </div>
</template>

<script setup lang="ts">
import { formatDate, calcReadTime } from '~/utils/post'

const base = useApiBase()

useSeoMeta({
  title: 'Jeremy.dev — 개발 블로그',
  description: '개발하면서 만난 문제와 해결 과정을 기록합니다.',
  ogTitle: 'Jeremy.dev',
  ogDescription: '개발하면서 만난 문제와 해결 과정을 기록합니다.',
  ogType: 'website',
  ogUrl: 'https://blog.nexacromancer.win',
  twitterCard: 'summary',
})

const { data: raw } = await useFetch<any[]>(`${base}/api/posts`)

const posts = computed(() =>
  (raw.value ?? []).map(p => ({
    id: p.id,
    title: p.title,
    summary: p.summary ?? '',
    date: formatDate(p.createdAt),
    tag: p.tag,
    readTime: calcReadTime(p.summary ?? ''),
  }))
)

const tags = computed(() => [...new Set(posts.value.map(p => p.tag))])

const selectedTag = ref<string | null>(null)

const filteredPosts = computed(() =>
  selectedTag.value ? posts.value.filter(p => p.tag === selectedTag.value) : posts.value
)
</script>
