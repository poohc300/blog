<template>
  <div class="w-full max-w-screen-xl mx-auto px-4 sm:px-6 lg:px-10 xl:px-16 py-8 sm:py-12">

    <!-- 검색 -->
    <div class="mb-8 relative">
      <input
        v-model="query"
        type="text"
        placeholder="검색"
        class="w-full sm:w-80 border border-gray-200 rounded px-4 py-2 text-sm outline-none focus:border-black transition-colors pr-8"
      />
      <button
        v-if="query"
        type="button"
        class="absolute right-2 top-1/2 -translate-y-1/2 text-gray-400 hover:text-black transition-colors sm:right-[calc(100%-78px)]"
        @click="query = ''"
      >
        ×
      </button>
    </div>

    <!-- Post List -->
    <div class="divide-y divide-gray-100">
      <PostCard
        v-for="post in pagedPosts" :key="post.id"
        :post="post"
      />
      <p v-if="posts.length === 0 && !pending" class="py-16 text-center text-sm text-gray-400">
        검색 결과가 없습니다.
      </p>
    </div>

    <!-- Pagination -->
    <div v-if="totalPages > 1" class="flex items-center justify-center gap-1 mt-12">
      <button
        :disabled="currentPage === 1"
        class="px-3 py-1.5 text-sm rounded transition-colors disabled:opacity-30"
        :class="currentPage === 1 ? 'text-gray-300' : 'text-gray-500 hover:text-black'"
        @click="currentPage--"
      >
        ←
      </button>

      <button
        v-for="page in pageNumbers" :key="page"
        class="w-8 h-8 text-sm rounded transition-colors"
        :class="page === currentPage
          ? 'bg-black text-white font-medium'
          : 'text-gray-500 hover:text-black'"
        @click="currentPage = page"
      >
        {{ page }}
      </button>

      <button
        :disabled="currentPage === totalPages"
        class="px-3 py-1.5 text-sm rounded transition-colors disabled:opacity-30"
        :class="currentPage === totalPages ? 'text-gray-300' : 'text-gray-500 hover:text-black'"
        @click="currentPage++"
      >
        →
      </button>
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

const query = ref('')
const debouncedQuery = ref('')
let debounceTimer: ReturnType<typeof setTimeout>

watch(query, (val) => {
  clearTimeout(debounceTimer)
  debounceTimer = setTimeout(() => {
    debouncedQuery.value = val
    currentPage.value = 1
  }, 300)
})

const { data: raw, pending } = await useFetch<any[]>(() =>
  debouncedQuery.value
    ? `${base}/api/posts?q=${encodeURIComponent(debouncedQuery.value)}`
    : `${base}/api/posts`,
  { watch: [debouncedQuery] }
)

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

const currentPage = ref(1)
const PAGE_SIZE = 5

const totalPages = computed(() => Math.ceil(posts.value.length / PAGE_SIZE))

const pagedPosts = computed(() => {
  const start = (currentPage.value - 1) * PAGE_SIZE
  return posts.value.slice(start, start + PAGE_SIZE)
})

const pageNumbers = computed(() => {
  const total = totalPages.value
  const cur = currentPage.value
  let start = Math.max(1, cur - 2)
  const end = Math.min(total, start + 4)
  start = Math.max(1, end - 4)
  return Array.from({ length: end - start + 1 }, (_, i) => start + i)
})
</script>
