<template>
  <div class="w-full max-w-screen-xl mx-auto px-4 sm:px-6 lg:px-10 xl:px-16 py-12 sm:py-16">

    <!-- 프로필 -->
    <div class="flex flex-col sm:flex-row items-center sm:items-start gap-8 mb-12">
      <div class="w-28 h-28 sm:w-32 sm:h-32 rounded-full bg-gray-100 border border-gray-200 flex items-center justify-center flex-shrink-0 overflow-hidden">
        <span class="text-gray-400 text-sm">사진</span>
      </div>
      <div class="text-center sm:text-left">
        <h1 class="text-2xl sm:text-3xl font-bold text-black mb-1">이름</h1>
        <p class="text-gray-500 text-base mb-4">백엔드 개발자 | 한 줄 소개를 여기에 작성하세요</p>
        <div class="flex items-center justify-center sm:justify-start gap-4 text-sm text-gray-500">
          <a href="https://github.com/" target="_blank" class="hover:text-black transition-colors">GitHub</a>
          <span class="text-gray-200">|</span>
          <a href="mailto:example@email.com" class="hover:text-black transition-colors">example@email.com</a>
        </div>
      </div>
    </div>

    <div class="border-t border-gray-100 mb-12" />

    <!-- About -->
    <section class="mb-12">
      <h2 class="text-base font-bold text-black mb-4">About</h2>
      <p class="text-sm text-gray-600 leading-relaxed max-w-2xl">
        자기소개를 여기에 작성하세요. 어떤 개발자인지, 어떤 것에 관심이 있는지,
        어떤 방식으로 일하는지 등 2~3줄로 자유롭게 작성하시면 됩니다.
      </p>
    </section>

    <!-- Experience + Skills -->
    <div class="grid grid-cols-1 sm:grid-cols-2 gap-12 mb-12">

      <!-- Experience -->
      <section>
        <h2 class="text-base font-bold text-black mb-6">Experience</h2>
        <div class="space-y-6">

          <div>
            <div class="flex items-start justify-between gap-4 mb-1">
              <span class="text-sm font-semibold text-black">회사명</span>
              <span class="text-xs text-gray-400 whitespace-nowrap">2023.01 – 현재</span>
            </div>
            <p class="text-xs text-gray-500 mb-1">직책 / 팀명</p>
            <ul class="text-sm text-gray-600 space-y-1 list-disc list-inside">
              <li>담당 업무 1</li>
              <li>담당 업무 2</li>
            </ul>
          </div>

          <div>
            <div class="flex items-start justify-between gap-4 mb-1">
              <span class="text-sm font-semibold text-black">이전 회사명</span>
              <span class="text-xs text-gray-400 whitespace-nowrap">2021.03 – 2022.12</span>
            </div>
            <p class="text-xs text-gray-500 mb-1">직책 / 팀명</p>
            <ul class="text-sm text-gray-600 space-y-1 list-disc list-inside">
              <li>담당 업무 1</li>
              <li>담당 업무 2</li>
            </ul>
          </div>

        </div>
      </section>

      <!-- Skills -->
      <section>
        <h2 class="text-base font-bold text-black mb-6">Skills</h2>
        <div class="space-y-4">

          <div>
            <p class="text-xs text-gray-400 mb-2">Language</p>
            <div class="flex flex-wrap gap-2">
              <span v-for="s in skills.language" :key="s" class="text-xs px-2 py-1 bg-gray-100 text-gray-700 rounded">{{ s }}</span>
            </div>
          </div>

          <div>
            <p class="text-xs text-gray-400 mb-2">Framework</p>
            <div class="flex flex-wrap gap-2">
              <span v-for="s in skills.framework" :key="s" class="text-xs px-2 py-1 bg-gray-100 text-gray-700 rounded">{{ s }}</span>
            </div>
          </div>

          <div>
            <p class="text-xs text-gray-400 mb-2">Database</p>
            <div class="flex flex-wrap gap-2">
              <span v-for="s in skills.database" :key="s" class="text-xs px-2 py-1 bg-gray-100 text-gray-700 rounded">{{ s }}</span>
            </div>
          </div>

          <div>
            <p class="text-xs text-gray-400 mb-2">DevOps</p>
            <div class="flex flex-wrap gap-2">
              <span v-for="s in skills.devops" :key="s" class="text-xs px-2 py-1 bg-gray-100 text-gray-700 rounded">{{ s }}</span>
            </div>
          </div>

        </div>
      </section>

    </div>

    <div class="border-t border-gray-100 mb-12" />

    <!-- Projects -->
    <section>
      <h2 class="text-base font-bold text-black mb-6">Projects</h2>
      <div class="grid grid-cols-1 sm:grid-cols-2 gap-4">

        <div v-for="project in projects" :key="project.name"
          class="border border-gray-200 rounded-lg p-5 hover:border-gray-400 transition-colors">
          <div class="flex items-start justify-between gap-4 mb-2">
            <span class="text-sm font-semibold text-black">{{ project.name }}</span>
            <a v-if="project.link" :href="project.link" target="_blank"
              class="text-xs text-gray-400 hover:text-black transition-colors whitespace-nowrap">링크 →</a>
          </div>
          <p class="text-sm text-gray-500 mb-3">{{ project.description }}</p>
          <div class="flex flex-wrap gap-1">
            <span v-for="t in project.tags" :key="t"
              class="text-xs px-2 py-0.5 bg-gray-100 text-gray-600 rounded">{{ t }}</span>
          </div>
        </div>

      </div>
    </section>

  </div>
</template>

<script setup lang="ts">
useSeoMeta({
  title: 'About | Jeremy.dev',
  description: '안녕하세요, Jeremy입니다.',
})

// ── 수정할 내용들 ───────────────────────────────────────────

const skills = {
  language:  ['Java', 'TypeScript', 'Python'],
  framework: ['Spring Boot', 'Nuxt 3', 'Vue 3'],
  database:  ['PostgreSQL', 'MyBatis'],
  devops:    ['GitHub Actions', 'nginx', 'Docker'],
}

const projects = [
  {
    name: 'Jeremy.dev 블로그',
    description: '라즈베리파이 위에서 직접 운영하는 개인 기술 블로그. SSR, 댓글 시스템, 관리자 페이지 포함.',
    tags: ['Nuxt 3', 'Spring Boot', 'PostgreSQL', 'nginx'],
    link: 'https://blog.nexacromancer.win',
  },
  {
    name: '프로젝트명',
    description: '프로젝트 설명을 여기에 작성하세요.',
    tags: ['태그1', '태그2'],
    link: '',
  },
]
</script>
