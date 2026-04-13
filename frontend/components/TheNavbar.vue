<template>
  <header class="border-b border-gray-200 sticky top-0 bg-white z-10">
    <div class="w-full max-w-screen-xl mx-auto px-4 sm:px-6 lg:px-10 xl:px-16 py-4 flex items-center justify-between">
      <span class="text-xl sm:text-2xl font-bold tracking-tight text-black">Jeremy.dev</span>
      <nav class="flex items-center gap-4 sm:gap-6 text-sm text-gray-600">
        <a href="#" class="hover:text-black transition-colors">글</a>
        <a href="#" class="hover:text-black transition-colors hidden sm:block">태그</a>
        <a href="#" class="hover:text-black transition-colors hidden sm:block">소개</a>
        <button
          class="px-3 py-1.5 bg-black text-white text-sm rounded hover:bg-gray-800 transition-colors"
          @click="showAddPost = true"
        >
          글쓰기
        </button>
      </nav>
    </div>
  </header>

  <AddPost
    v-if="showAddPost"
    @confirm="handleConfirm"
    @cancel="showAddPost = false"
  />
</template>

<script setup lang="ts">
const showAddPost = ref(false)

function handleConfirm(data: { title: string; tag: string; body: string }) {
  showAddPost.value = false
  // TODO: API 연결
  console.log('submit:', data)
}
</script>
