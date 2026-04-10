<template>
  <div class="min-h-screen bg-white font-sans">

    <!-- Header -->
    <header class="border-b border-gray-200 px-6 py-4">
      <div class="max-w-5xl mx-auto flex items-center justify-between">
        <span class="text-2xl font-bold tracking-tight text-black">Jeremy.dev</span>
        <nav class="flex items-center gap-6 text-sm text-gray-600">
          <a href="#" class="hover:text-black transition-colors">글</a>
          <a href="#" class="hover:text-black transition-colors">태그</a>
          <a href="#" class="hover:text-black transition-colors">소개</a>
        </nav>
      </div>
    </header>

    <!-- Featured Post -->
    <section class="border-b border-gray-200">
      <div class="max-w-5xl mx-auto px-6 py-16">
        <div class="flex flex-col md:flex-row gap-10 items-start">
          <div class="flex-1">
            <span class="text-xs font-semibold uppercase tracking-widest text-gray-400 mb-4 block">Featured</span>
            <h1 class="text-4xl font-bold text-black leading-tight mb-4">
              SSH 터널링으로 원격 서버에서 OAuth 인증하기
            </h1>
            <p class="text-gray-500 text-lg leading-relaxed mb-6">
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
          <div class="w-full md:w-64 h-44 bg-gray-100 rounded flex items-center justify-center shrink-0">
            <span class="text-gray-300 text-sm">썸네일</span>
          </div>
        </div>
      </div>
    </section>

    <!-- Post List -->
    <main class="max-w-5xl mx-auto px-6 py-12">

      <!-- Tag Filter -->
      <div class="flex gap-2 mb-10 overflow-x-auto pb-1">
        <button class="px-4 py-1.5 rounded-full bg-black text-white text-sm whitespace-nowrap">전체</button>
        <button class="px-4 py-1.5 rounded-full border border-gray-200 text-gray-600 text-sm whitespace-nowrap hover:border-black hover:text-black transition-colors">Spring Boot</button>
        <button class="px-4 py-1.5 rounded-full border border-gray-200 text-gray-600 text-sm whitespace-nowrap hover:border-black hover:text-black transition-colors">Nuxt</button>
        <button class="px-4 py-1.5 rounded-full border border-gray-200 text-gray-600 text-sm whitespace-nowrap hover:border-black hover:text-black transition-colors">DevOps</button>
        <button class="px-4 py-1.5 rounded-full border border-gray-200 text-gray-600 text-sm whitespace-nowrap hover:border-black hover:text-black transition-colors">Database</button>
      </div>

      <!-- Posts -->
      <div class="divide-y divide-gray-100">
        <article v-for="post in posts" :key="post.id" class="py-8 flex gap-8 items-start group cursor-pointer">
          <div class="flex-1">
            <div class="flex items-center gap-2 mb-3">
              <div class="w-6 h-6 rounded-full bg-black flex items-center justify-center text-white text-xs font-bold">J</div>
              <span class="text-sm text-gray-500">Jeremy · {{ post.date }}</span>
            </div>
            <h2 class="text-xl font-bold text-black mb-2 group-hover:underline leading-snug">
              {{ post.title }}
            </h2>
            <p class="text-gray-500 text-sm leading-relaxed mb-4 line-clamp-2">
              {{ post.summary }}
            </p>
            <div class="flex items-center gap-3">
              <span class="text-xs px-2.5 py-1 bg-gray-100 text-gray-600 rounded-full">{{ post.tag }}</span>
              <span class="text-xs text-gray-400">{{ post.readTime }}분 읽기</span>
            </div>
          </div>
          <div class="w-28 h-20 bg-gray-100 rounded shrink-0 flex items-center justify-center">
            <span class="text-gray-300 text-xs">이미지</span>
          </div>
        </article>
      </div>

    </main>

    <!-- Footer -->
    <footer class="border-t border-gray-100 py-10">
      <div class="max-w-5xl mx-auto px-6 flex flex-col md:flex-row items-center justify-between gap-4">
        <span class="text-sm text-gray-400">© 2026 Jeremy.dev</span>
      </div>
    </footer>

  </div>
</template>

<script setup lang="ts">
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
]
</script>
