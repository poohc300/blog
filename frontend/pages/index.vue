<template>
  <div class="w-full max-w-screen-xl mx-auto px-4 sm:px-6 lg:px-10 xl:px-16 py-8 sm:py-12">

    <div class="flex flex-col lg:flex-row gap-12 lg:gap-16">

      <!-- Post List -->
      <div class="flex-1 min-w-0">
        <div class="divide-y divide-gray-100">
          <PostCard
            v-for="post in pagedPosts" :key="post.id"
            :post="post"
          />
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

      <!-- About Sidebar -->
      <aside class="w-full lg:w-64 xl:w-72 flex-shrink-0">
        <NuxtLink to="/about" class="block group">
          <div class="border border-gray-200 rounded-lg p-6 hover:border-black transition-colors">
            <div class="flex items-center gap-4 mb-4">
              <div class="w-14 h-14 rounded-full bg-gray-100 border border-gray-200 overflow-hidden flex-shrink-0">
                <img v-if="about?.profileImage" :src="about.profileImage" alt="프로필" class="w-full h-full object-cover" />
                <div v-else class="w-full h-full flex items-center justify-center">
                  <span class="text-gray-400 text-xs">사진</span>
                </div>
              </div>
              <div class="min-w-0">
                <p class="text-sm font-semibold text-black truncate">{{ about?.name ?? '' }}</p>
                <p class="text-xs text-gray-500 truncate">{{ about?.headline ?? '' }}</p>
              </div>
            </div>
            <p class="text-xs text-gray-400 group-hover:text-black transition-colors">소개 보기 →</p>
          </div>
        </NuxtLink>
      </aside>

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
const { data: about } = await useFetch<any>(`${base}/api/about`)

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
