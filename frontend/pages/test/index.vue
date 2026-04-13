<template>
  <div class="w-full max-w-screen-xl mx-auto px-4 sm:px-6 lg:px-10 xl:px-16 py-8 sm:py-12">

    <!-- Tag Filter -->
    <div class="flex gap-2 mb-8 sm:mb-10 overflow-x-auto pb-1">
      <button class="px-4 py-1.5 rounded-full bg-black text-white text-sm whitespace-nowrap">전체</button>
      <button
        v-for="tag in tags" :key="tag"
        class="px-4 py-1.5 rounded-full border border-gray-200 text-gray-600 text-sm whitespace-nowrap hover:border-black hover:text-black transition-colors"
      >
        {{ tag }}
      </button>
    </div>

    <!-- Post List -->
    <div class="divide-y divide-gray-100">
      <article
        v-for="post in posts" :key="post.id"
        class="py-6 sm:py-8 flex gap-4 sm:gap-8 items-start group cursor-pointer"
        @click="$router.push(`/test/posts/${post.id}`)"
      >
        <div class="flex-1">
          <div class="flex items-center gap-2 mb-3">
            <div class="w-6 h-6 rounded-full bg-black flex items-center justify-center text-white text-xs font-bold">J</div>
            <span class="text-sm text-gray-500">Jeremy · {{ post.date }}</span>
          </div>
          <h2 class="text-base sm:text-xl font-bold text-black mb-2 group-hover:underline leading-snug">
            {{ post.title }}
          </h2>
          <p class="text-gray-500 text-sm leading-relaxed mb-4 line-clamp-2 hidden sm:block">
            {{ post.summary }}
          </p>
          <div class="flex items-center gap-3">
            <span class="text-xs px-2.5 py-1 bg-gray-100 text-gray-600 rounded-full">{{ post.tag }}</span>
            <span class="text-xs text-gray-400">{{ post.readTime }}분 읽기</span>
          </div>
        </div>
        <div class="w-20 h-14 sm:w-28 sm:h-20 bg-gray-100 rounded shrink-0 flex items-center justify-center">
          <span class="text-gray-300 text-xs">이미지</span>
        </div>
      </article>
    </div>

  </div>
</template>

<script setup lang="ts">
const tags = ['Spring Boot', 'Nuxt', 'DevOps', 'Database']

const posts = [
  {
    id: 1,
    title: 'SSR vs 정적 배포 — Nuxt로 블로그 만들며 배운 것들',
    summary: 'Nuxt로 블로그를 만들면서 SSR과 정적 배포가 실제로 어떻게 다른지 systemd 서비스 관리부터 nginx 설정까지 직접 겪은 차이를 정리했습니다.',
    date: '2026년 4월 10일',
    tag: 'Nuxt',
    readTime: 6,
  },
  {
    id: 2,
    title: 'PostgreSQL 스키마 설계 — 블로그 댓글 재귀 구조 만들기',
    summary: '댓글의 댓글을 무한히 달 수 있는 구조를 Adjacency List 방식으로 설계하고, WITH RECURSIVE 쿼리로 트리를 한 번에 가져오는 방법을 소개합니다.',
    date: '2026년 4월 10일',
    tag: 'Database',
    readTime: 4,
  },
  {
    id: 3,
    title: 'GitHub Actions Self-hosted Runner on Raspberry Pi',
    summary: '라즈베리파이에 GitHub Actions self-hosted runner를 설치하고 Spring Boot + Nuxt 프로젝트를 자동 배포하는 CI/CD 파이프라인을 구성했습니다.',
    date: '2026년 4월 10일',
    tag: 'DevOps',
    readTime: 7,
  },
  {
    id: 4,
    title: 'Google Drive 백업 자동화 — rclone + cron 설정',
    summary: '라즈베리파이 서버의 DB와 업로드 파일을 rclone으로 Google Drive에 매일 자동 백업하는 방법을 정리했습니다.',
    date: '2026년 4월 10일',
    tag: 'DevOps',
    readTime: 5,
  },
  {
    id: 5,
    title: 'MyBatis vs JPA — 블로그 프로젝트에서 선택한 이유',
    summary: '화면 중심 설계에서 MyBatis가 유리한 이유와 WITH RECURSIVE 같은 복잡한 쿼리를 직접 작성할 때 얻는 장점을 정리했습니다.',
    date: '2026년 4월 10일',
    tag: 'Spring Boot',
    readTime: 4,
  },
  {
    id: 6,
    title: 'Tiptap 에디터 Nuxt 3에서 설정하기',
    summary: 'Nuxt 3 SSR 환경에서 Tiptap 위지윅 에디터를 ClientOnly로 감싸서 hydration 에러 없이 동작하게 만드는 설정을 공유합니다.',
    date: '2026년 4월 10일',
    tag: 'Nuxt',
    readTime: 3,
  },
]
</script>
