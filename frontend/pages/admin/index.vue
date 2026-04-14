<template>
  <div class="w-full max-w-screen-lg mx-auto px-4 sm:px-6 lg:px-10 py-8 sm:py-12">

    <div class="flex items-center justify-between mb-8">
      <h1 class="text-2xl font-bold text-black">글 관리</h1>
      <NuxtLink
        to="/admin/AddPost"
        class="px-4 py-2 text-sm font-medium bg-black text-white rounded hover:bg-gray-800 transition-colors"
      >
        + 새 글 쓰기
      </NuxtLink>
    </div>

    <div v-if="posts.length === 0" class="text-center py-20 text-gray-400 text-sm">
      작성된 글이 없습니다.
    </div>

    <div v-else class="divide-y divide-gray-100 border border-gray-100 rounded">
      <div
        v-for="post in posts" :key="post.id"
        class="flex items-center justify-between px-4 py-3.5 hover:bg-gray-50 transition-colors"
      >
        <div class="flex items-center gap-3 min-w-0">
          <span class="text-xs px-2 py-0.5 bg-gray-100 text-gray-500 rounded-full whitespace-nowrap">{{ post.tag }}</span>
          <NuxtLink
            :to="`/posts/${post.id}`"
            class="text-sm text-black hover:underline truncate"
          >
            {{ post.title }}
          </NuxtLink>
        </div>
        <div class="flex items-center gap-1 ml-4 shrink-0">
          <span class="text-xs text-gray-400 mr-2 hidden sm:block">{{ post.date }}</span>
          <NuxtLink
            :to="`/admin/SavePost?id=${post.id}`"
            class="px-3 py-1 text-xs rounded border border-gray-200 text-gray-600 hover:border-black hover:text-black transition-colors"
          >
            수정
          </NuxtLink>
          <button
            class="px-3 py-1 text-xs rounded border border-gray-200 text-red-400 hover:border-red-400 hover:text-red-500 transition-colors"
            @click="handleDelete(post.id, post.title)"
          >
            삭제
          </button>
        </div>
      </div>
    </div>

  </div>
</template>

<script setup lang="ts">
import { formatDate } from '~/utils/post'

definePageMeta({ layout: 'admin', middleware: 'auth', ssr: false })

const ui = useUiStore()
const base = useApiBase()
const { authFetch } = useAuthFetch()

const { data: raw, refresh } = await useFetch<any[]>(`${base}/api/posts`)

const posts = computed(() =>
  (raw.value ?? []).map(p => ({
    id: p.id,
    title: p.title,
    tag: p.tag,
    date: formatDate(p.createdAt),
  }))
)

async function handleDelete(id: number, title: string) {
  const confirmed = await ui.confirm({
    title: '글을 삭제할까요?',
    text: `"${title}"`,
    confirmText: '삭제',
    cancelText: '취소',
  })
  if (!confirmed) return
  ui.showSpinner()
  try {
    await authFetch(`${base}/api/posts/${id}`, { method: 'DELETE' })
    await refresh()
  } catch {
    await ui.alert({ icon: 'error', title: '오류', text: '삭제 중 오류가 발생했습니다.' })
  } finally {
    ui.hideSpinner()
  }
}
</script>
