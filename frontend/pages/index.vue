<template>
  <div class="w-full max-w-screen-xl mx-auto px-4 sm:px-6 lg:px-10 xl:px-16 py-8 sm:py-12">

    <!-- Featured Post -->
    <section class="border-b border-gray-200 pb-10 sm:pb-14 mb-10 sm:mb-14">
      <div class="flex flex-col sm:flex-row gap-6 sm:gap-10 items-start">
        <div class="flex-1">
          <span class="text-xs font-semibold uppercase tracking-widest text-gray-400 mb-3 block">Featured</span>
          <h1 class="text-2xl sm:text-3xl xl:text-4xl font-bold text-black leading-tight mb-3 sm:mb-4">
            SSH 터널링으로 원격 서버에서 OAuth 인증하기
          </h1>
          <p class="text-gray-500 text-base sm:text-lg leading-relaxed mb-5 sm:mb-6">
            집 밖에서 라즈베리파이 서버에 접속 중일 때, rclone Google Drive 인증처럼 브라우저가 필요한 작업을 SSH 터널을 이용해 해결하는 방법을 공유합니다.
          </p>
          <div class="flex items-center gap-3">
            <div class="w-8 h-8 rounded-full bg-black flex items-center justify-center text-white text-xs font-bold">J</div>
            <div>
              <span class="text-sm font-medium text-black">Jeremy</span>
              <span class="text-gray-400 text-sm"> · 2026년 4월 10일 · 5분 읽기</span>
            </div>
          </div>
        </div>
        <div class="w-full sm:w-52 lg:w-64 xl:w-72 h-40 sm:h-44 bg-gray-100 rounded flex items-center justify-center shrink-0">
          <span class="text-gray-300 text-sm">썸네일</span>
        </div>
      </div>
    </section>

    <!-- Tag Filter -->
    <div class="flex gap-2 mb-8 sm:mb-10 overflow-x-auto pb-1 scrollbar-hide">
      <button class="px-4 py-1.5 rounded-full bg-black text-white text-sm whitespace-nowrap">전체</button>
      <button
        v-for="tag in tags" :key="tag"
        class="px-4 py-1.5 rounded-full border border-gray-200 text-gray-600 text-sm whitespace-nowrap hover:border-black hover:text-black transition-colors"
      >
        {{ tag }}
      </button>
    </div>

    <!-- Post Grid -->
    <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6 sm:gap-8">
      <article
        v-for="post in posts" :key="post.id"
        class="group cursor-pointer flex flex-col"
      >
        <div class="w-full aspect-video bg-gray-100 rounded mb-4 flex items-center justify-center">
          <span class="text-gray-300 text-sm">이미지</span>
        </div>
        <div class="flex-1 flex flex-col">
          <div class="flex items-center gap-2 mb-2">
            <span class="text-xs px-2.5 py-1 bg-gray-100 text-gray-600 rounded-full">{{ post.tag }}</span>
            <span class="text-xs text-gray-400">{{ post.readTime }}분 읽기</span>
          </div>
          <h2 class="text-base sm:text-lg font-bold text-black mb-2 group-hover:underline leading-snug">
            {{ post.title }}
          </h2>
          <p class="text-gray-500 text-sm leading-relaxed mb-4 line-clamp-2 flex-1">
            {{ post.summary }}
          </p>
          <div class="flex items-center gap-2">
            <div class="w-6 h-6 rounded-full bg-black flex items-center justify-center text-white text-xs font-bold">J</div>
            <span class="text-xs text-gray-400">Jeremy · {{ post.date }}</span>
          </div>
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
