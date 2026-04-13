<template>
  <article
    class="flex items-start group cursor-pointer"
    :class="compact ? 'py-5 gap-4' : 'py-6 sm:py-8 gap-4 sm:gap-8'"
    @click="$router.push(`/posts/${post.id}`)"
  >
    <div class="flex-1">
      <div class="flex items-center gap-2 mb-2" :class="{ 'mb-3': !compact }">
        <div
          class="rounded-full bg-black flex items-center justify-center text-white font-bold"
          :class="compact ? 'w-5 h-5 text-xs' : 'w-6 h-6 text-xs'"
        >J</div>
        <span :class="compact ? 'text-xs text-gray-500' : 'text-sm text-gray-500'">
          Jeremy · {{ post.date }}
        </span>
      </div>

      <h2
        class="font-bold text-black group-hover:underline leading-snug"
        :class="compact ? 'text-sm sm:text-base mb-1' : 'text-base sm:text-xl mb-2'"
      >
        {{ post.title }}
      </h2>

      <p
        v-if="!compact && post.summary"
        class="text-gray-500 text-sm leading-relaxed mb-4 line-clamp-2 hidden sm:block"
      >
        {{ post.summary }}
      </p>

      <div class="flex items-center gap-2 mt-2" :class="{ 'gap-3': !compact }">
        <span class="text-xs bg-gray-100 text-gray-600 rounded-full" :class="compact ? 'px-2 py-0.5' : 'px-2.5 py-1'">
          {{ post.tag }}
        </span>
        <span class="text-xs text-gray-400">{{ post.readTime }}분 읽기</span>
      </div>
    </div>

    <div
      class="bg-gray-100 rounded shrink-0 flex items-center justify-center"
      :class="compact ? 'w-16 h-12 sm:w-24 sm:h-16' : 'w-20 h-14 sm:w-28 sm:h-20'"
    >
      <span class="text-gray-300 text-xs">이미지</span>
    </div>
  </article>
</template>

<script setup lang="ts">
defineProps<{
  post: {
    id: number
    title: string
    summary?: string
    date: string
    tag: string
    readTime: number
  }
  compact?: boolean
}>()
</script>
