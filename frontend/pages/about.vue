<template>
  <div class="w-full max-w-screen-xl mx-auto px-4 sm:px-6 lg:px-10 xl:px-16 py-12 sm:py-16">

    <template v-if="about">

      <!-- 프로필 -->
      <div class="flex flex-col sm:flex-row items-center sm:items-start gap-8 mb-12">
        <div class="w-28 h-28 sm:w-32 sm:h-32 rounded-full bg-gray-100 border border-gray-200 flex items-center justify-center flex-shrink-0 overflow-hidden">
          <img v-if="about.profileImage" :src="about.profileImage" alt="프로필" class="w-full h-full object-cover" />
          <span v-else class="text-gray-400 text-sm">사진</span>
        </div>
        <div class="text-center sm:text-left">
          <h1 class="text-2xl sm:text-3xl font-bold text-black mb-1">{{ about.name }}</h1>
          <p class="text-gray-500 text-base mb-4">{{ about.headline }}</p>
          <div class="flex items-center justify-center sm:justify-start gap-4 text-sm text-gray-500">
            <a :href="about.github" target="_blank" class="hover:text-black transition-colors">GitHub</a>
            <span class="text-gray-200">|</span>
            <a :href="`mailto:${about.email}`" class="hover:text-black transition-colors">{{ about.email }}</a>
          </div>
        </div>
      </div>

      <div class="border-t border-gray-100 mb-12" />

      <!-- About -->
      <section class="mb-12">
        <h2 class="text-base font-bold text-black mb-4">About</h2>
        <p class="text-sm text-gray-600 leading-relaxed max-w-2xl">{{ about.summary }}</p>
      </section>

      <!-- Experience + Skills -->
      <div class="grid grid-cols-1 sm:grid-cols-2 gap-12 mb-12">

        <!-- Experience -->
        <section>
          <h2 class="text-base font-bold text-black mb-6">Experience</h2>
          <div class="space-y-6">
            <div v-for="(exp, i) in experience" :key="i">
              <div class="flex items-start justify-between gap-4 mb-1">
                <span class="text-sm font-semibold text-black">{{ exp.company }}</span>
                <span class="text-xs text-gray-400 whitespace-nowrap">{{ exp.period }}</span>
              </div>
              <p class="text-xs text-gray-500 mb-1">{{ exp.role }}</p>
              <ul class="text-sm text-gray-600 space-y-1 list-disc list-inside">
                <li v-for="(task, j) in exp.tasks" :key="j">{{ task }}</li>
              </ul>
            </div>
          </div>
        </section>

        <!-- Skills -->
        <section>
          <h2 class="text-base font-bold text-black mb-6">Skills</h2>
          <div class="space-y-4">
            <div v-for="(items, category) in skills" :key="category">
              <p class="text-xs text-gray-400 mb-2 capitalize">{{ category }}</p>
              <div class="flex flex-wrap gap-2">
                <span v-for="s in items" :key="s"
                  class="text-xs px-2 py-1 bg-gray-100 text-gray-700 rounded">{{ s }}</span>
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
          <div v-for="(project, i) in projects" :key="i"
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

    </template>

  </div>
</template>

<script setup lang="ts">
const base = useApiBase()

const { data: about } = await useFetch<any>(`${base}/api/about`)

const skills = computed(() => {
  if (!about.value?.skills) return {}
  try { return JSON.parse(about.value.skills) } catch { return {} }
})

const experience = computed(() => {
  if (!about.value?.experience) return []
  try { return JSON.parse(about.value.experience) } catch { return [] }
})

const projects = computed(() => {
  if (!about.value?.projects) return []
  try { return JSON.parse(about.value.projects) } catch { return [] }
})

useSeoMeta({
  title: 'About | Jeremy.dev',
  description: () => about.value?.headline || '',
})
</script>
