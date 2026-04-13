<template>
  <article class="max-w-2xl mx-auto">

    <!-- Tag + Action Menu -->
    <div class="mb-6 flex items-center justify-between">
      <span class="text-xs px-2.5 py-1 bg-gray-100 text-gray-600 rounded-full">{{ post.tag }}</span>

      <div v-if="isAuthor(post.authorId)" class="relative" ref="menuRef">
        <button
          class="w-8 h-8 flex items-center justify-center rounded-full text-gray-400 hover:text-black hover:bg-gray-100 transition-colors"
          @click="menuOpen = !menuOpen"
        >
          <svg width="16" height="16" viewBox="0 0 16 16" fill="currentColor">
            <circle cx="2" cy="8" r="1.5" />
            <circle cx="8" cy="8" r="1.5" />
            <circle cx="14" cy="8" r="1.5" />
          </svg>
        </button>

        <div
          v-if="menuOpen"
          class="absolute right-0 mt-1 w-28 bg-white border border-gray-200 rounded shadow-lg z-20 py-1"
        >
          <button
            class="w-full text-left px-4 py-2 text-sm text-gray-700 hover:bg-gray-50 transition-colors"
            @click="handleEdit"
          >
            수정
          </button>
          <button
            class="w-full text-left px-4 py-2 text-sm text-red-500 hover:bg-gray-50 transition-colors"
            @click="handleDelete"
          >
            삭제
          </button>
        </div>
      </div>
    </div>

    <!-- Title -->
    <h1 class="text-2xl sm:text-3xl xl:text-4xl font-bold text-black leading-tight mb-4">
      {{ post.title }}
    </h1>

    <!-- Author / Meta -->
    <div class="flex items-center gap-3 mb-8 pb-8 border-b border-gray-200">
      <div class="w-9 h-9 rounded-full bg-black flex items-center justify-center text-white text-sm font-bold">J</div>
      <div>
        <span class="text-sm font-medium text-black">Jeremy</span>
        <span class="text-gray-400 text-sm"> · {{ post.date }} · {{ post.readTime }}분 읽기</span>
      </div>
    </div>

    <!-- Body -->
    <div
      class="prose prose-gray max-w-none text-gray-800 leading-relaxed space-y-6"
      v-html="post.body"
    />

  </article>
</template>

<script setup lang="ts">
import { onClickOutside } from '@vueuse/core'

const { isAuthor } = useAuth()

defineProps<{
  post: {
    authorId: number
    title: string
    body: string
    date: string
    tag: string
    readTime: number
  }
}>()

const emit = defineEmits<{
  edit: []
  delete: []
}>()

const menuOpen = ref(false)
const menuRef = ref<HTMLElement | null>(null)

onClickOutside(menuRef, () => { menuOpen.value = false })

function handleEdit() {
  menuOpen.value = false
  emit('edit')
}

function handleDelete() {
  menuOpen.value = false
  emit('delete')
}
</script>
