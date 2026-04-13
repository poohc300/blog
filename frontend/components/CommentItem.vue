<template>
  <div :style="{ paddingLeft: `${comment.depth * 2}rem` }">
    <div class="py-5 border-b border-gray-100">

      <!-- Author / Meta -->
      <div class="flex items-center gap-2 mb-3">
        <div class="w-7 h-7 rounded-full bg-black flex items-center justify-center text-white text-xs font-bold shrink-0">
          {{ comment.authorInitial }}
        </div>
        <span class="text-sm font-medium text-black">{{ comment.authorName }}</span>
        <span class="text-xs text-gray-400">· {{ comment.date }}</span>
      </div>

      <!-- Body -->
      <p class="text-sm text-gray-700 leading-relaxed ml-9">
        {{ comment.body }}
      </p>

      <!-- Reply -->
      <button
        class="mt-2 ml-9 text-xs text-gray-400 hover:text-black transition-colors"
        @click="$emit('reply', comment.id)"
      >
        답글
      </button>

    </div>
  </div>
</template>

<script setup lang="ts">
defineProps<{
  comment: {
    id: number
    authorInitial: string
    authorName: string
    date: string
    body: string
    depth: number
  }
}>()

defineEmits<{
  reply: [id: number]
}>()
</script>
