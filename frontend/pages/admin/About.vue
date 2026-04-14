<template>
  <div class="w-full max-w-2xl mx-auto px-4 sm:px-6 py-10">
    <h2 class="text-lg font-bold text-black mb-8">소개 관리</h2>

    <!-- 프로필 -->
    <section class="mb-8">
      <h3 class="text-sm font-semibold text-black mb-4 pb-2 border-b border-gray-100">프로필</h3>
      <div class="space-y-3">
        <div>
          <label class="block text-xs text-gray-400 mb-1">이름</label>
          <input v-model="form.name" type="text"
            class="w-full border border-gray-200 rounded px-3 py-2 text-sm outline-none focus:border-black transition-colors" />
        </div>
        <div>
          <label class="block text-xs text-gray-400 mb-1">한 줄 소개</label>
          <input v-model="form.headline" type="text"
            class="w-full border border-gray-200 rounded px-3 py-2 text-sm outline-none focus:border-black transition-colors" />
        </div>
        <div>
          <label class="block text-xs text-gray-400 mb-1">GitHub URL</label>
          <input v-model="form.github" type="text"
            class="w-full border border-gray-200 rounded px-3 py-2 text-sm outline-none focus:border-black transition-colors" />
        </div>
        <div>
          <label class="block text-xs text-gray-400 mb-1">이메일</label>
          <input v-model="form.email" type="text"
            class="w-full border border-gray-200 rounded px-3 py-2 text-sm outline-none focus:border-black transition-colors" />
        </div>
      </div>
    </section>

    <!-- About -->
    <section class="mb-8">
      <h3 class="text-sm font-semibold text-black mb-4 pb-2 border-b border-gray-100">About</h3>
      <textarea v-model="form.summary" rows="4"
        class="w-full border border-gray-200 rounded px-3 py-2 text-sm outline-none focus:border-black transition-colors resize-none" />
    </section>

    <!-- Experience -->
    <section class="mb-8">
      <div class="flex items-center justify-between mb-4 pb-2 border-b border-gray-100">
        <h3 class="text-sm font-semibold text-black">Experience</h3>
        <button @click="addExperience"
          class="text-xs px-3 py-1 border border-gray-200 rounded hover:border-black transition-colors">+ 추가</button>
      </div>
      <div class="space-y-6">
        <div v-for="(exp, i) in form.experience" :key="i" class="p-4 border border-gray-100 rounded-lg">
          <div class="flex justify-end mb-3">
            <button @click="removeExperience(i)" class="text-xs text-gray-400 hover:text-red-500 transition-colors">삭제</button>
          </div>
          <div class="space-y-2">
            <input v-model="exp.company" type="text" placeholder="회사명"
              class="w-full border border-gray-200 rounded px-3 py-2 text-sm outline-none focus:border-black transition-colors" />
            <input v-model="exp.period" type="text" placeholder="기간 (예: 2023.01 – 현재)"
              class="w-full border border-gray-200 rounded px-3 py-2 text-sm outline-none focus:border-black transition-colors" />
            <input v-model="exp.role" type="text" placeholder="직책 / 팀명"
              class="w-full border border-gray-200 rounded px-3 py-2 text-sm outline-none focus:border-black transition-colors" />
            <div>
              <div class="flex items-center justify-between mb-1">
                <span class="text-xs text-gray-400">담당 업무</span>
                <button @click="addTask(i)" class="text-xs text-gray-400 hover:text-black transition-colors">+ 추가</button>
              </div>
              <div class="space-y-1">
                <div v-for="(task, j) in exp.tasks" :key="j" class="flex gap-2">
                  <input v-model="exp.tasks[j]" type="text" placeholder="업무 내용"
                    class="flex-1 border border-gray-200 rounded px-3 py-2 text-sm outline-none focus:border-black transition-colors" />
                  <button @click="removeTask(i, j)" class="text-xs text-gray-400 hover:text-red-500 transition-colors px-2">×</button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- Skills -->
    <section class="mb-8">
      <h3 class="text-sm font-semibold text-black mb-4 pb-2 border-b border-gray-100">Skills</h3>
      <div class="space-y-4">
        <div v-for="category in skillCategories" :key="category">
          <div class="flex items-center justify-between mb-1">
            <label class="text-xs text-gray-400 capitalize">{{ category }}</label>
            <button @click="addSkill(category)" class="text-xs text-gray-400 hover:text-black transition-colors">+ 추가</button>
          </div>
          <div class="flex flex-wrap gap-2">
            <div v-for="(skill, i) in form.skills[category]" :key="i" class="flex items-center gap-1 bg-gray-100 rounded px-2 py-1">
              <input v-model="form.skills[category][i]" type="text"
                class="text-xs bg-transparent outline-none w-20" />
              <button @click="removeSkill(category, i)" class="text-gray-400 hover:text-red-500 text-xs leading-none">×</button>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- Projects -->
    <section class="mb-8">
      <div class="flex items-center justify-between mb-4 pb-2 border-b border-gray-100">
        <h3 class="text-sm font-semibold text-black">Projects</h3>
        <button @click="addProject"
          class="text-xs px-3 py-1 border border-gray-200 rounded hover:border-black transition-colors">+ 추가</button>
      </div>
      <div class="space-y-6">
        <div v-for="(project, i) in form.projects" :key="i" class="p-4 border border-gray-100 rounded-lg">
          <div class="flex justify-end mb-3">
            <button @click="removeProject(i)" class="text-xs text-gray-400 hover:text-red-500 transition-colors">삭제</button>
          </div>
          <div class="space-y-2">
            <input v-model="project.name" type="text" placeholder="프로젝트명"
              class="w-full border border-gray-200 rounded px-3 py-2 text-sm outline-none focus:border-black transition-colors" />
            <textarea v-model="project.description" rows="2" placeholder="프로젝트 설명"
              class="w-full border border-gray-200 rounded px-3 py-2 text-sm outline-none focus:border-black transition-colors resize-none" />
            <input v-model="project.link" type="text" placeholder="링크 URL"
              class="w-full border border-gray-200 rounded px-3 py-2 text-sm outline-none focus:border-black transition-colors" />
            <div>
              <div class="flex items-center justify-between mb-1">
                <span class="text-xs text-gray-400">태그</span>
                <button @click="addProjectTag(i)" class="text-xs text-gray-400 hover:text-black transition-colors">+ 추가</button>
              </div>
              <div class="flex flex-wrap gap-2">
                <div v-for="(tag, j) in project.tags" :key="j" class="flex items-center gap-1 bg-gray-100 rounded px-2 py-1">
                  <input v-model="project.tags[j]" type="text"
                    class="text-xs bg-transparent outline-none w-16" />
                  <button @click="removeProjectTag(i, j)" class="text-gray-400 hover:text-red-500 text-xs leading-none">×</button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- 저장 -->
    <div class="flex justify-end pt-4 border-t border-gray-100">
      <button type="button" @click="save"
        class="px-6 py-2 text-sm font-medium bg-black text-white rounded hover:bg-gray-800 transition-colors">
        저장
      </button>
    </div>

  </div>
</template>

<script setup lang="ts">
definePageMeta({ layout: 'admin', middleware: 'auth', ssr: false })

const base = useApiBase()
const ui = useUiStore()
const { authFetch } = useAuthFetch()

const skillCategories = ['language', 'framework', 'database', 'devops']

const form = reactive({
  name:       '',
  headline:   '',
  github:     '',
  email:      '',
  summary:    '',
  skills:     { language: [], framework: [], database: [], devops: [] } as Record<string, string[]>,
  experience: [] as any[],
  projects:   [] as any[],
})

function parseJson(val: string | null, fallback: any) {
  if (!val) return fallback
  try { return JSON.parse(val) } catch { return fallback }
}

const { data: raw } = await useFetch<any>(`${base}/api/about`)

watch(raw, (val) => {
  if (!val) return
  form.name       = val.name       ?? ''
  form.headline   = val.headline   ?? ''
  form.github     = val.github     ?? ''
  form.email      = val.email      ?? ''
  form.summary    = val.summary    ?? ''
  form.skills     = parseJson(val.skills,     { language: [], framework: [], database: [], devops: [] })
  form.experience = parseJson(val.experience, [])
  form.projects   = parseJson(val.projects,   [])
}, { immediate: true })

// ── Experience ────────────────────────────────────────────
function addExperience() {
  form.experience.push({ company: '', period: '', role: '', tasks: [''] })
}
function removeExperience(i: number) {
  form.experience.splice(i, 1)
}
function addTask(expIndex: number) {
  form.experience[expIndex].tasks.push('')
}
function removeTask(expIndex: number, taskIndex: number) {
  form.experience[expIndex].tasks.splice(taskIndex, 1)
}

// ── Skills ────────────────────────────────────────────────
function addSkill(category: string) {
  if (!form.skills[category]) form.skills[category] = []
  form.skills[category].push('')
}
function removeSkill(category: string, i: number) {
  form.skills[category].splice(i, 1)
}

// ── Projects ──────────────────────────────────────────────
function addProject() {
  form.projects.push({ name: '', description: '', link: '', tags: [] })
}
function removeProject(i: number) {
  form.projects.splice(i, 1)
}
function addProjectTag(projectIndex: number) {
  form.projects[projectIndex].tags.push('')
}
function removeProjectTag(projectIndex: number, tagIndex: number) {
  form.projects[projectIndex].tags.splice(tagIndex, 1)
}

// ── 저장 ──────────────────────────────────────────────────
async function save() {
  ui.showSpinner()
  try {
    await authFetch(`${base}/api/about`, {
      method: 'PUT',
      body: {
        ...form,
        skills:     JSON.stringify(form.skills),
        experience: JSON.stringify(form.experience),
        projects:   JSON.stringify(form.projects),
      },
    })
    await ui.alert({ icon: 'success', title: '저장됐습니다.', text: '' })
  } catch {
    await ui.alert({ icon: 'error', title: '오류', text: '저장 중 오류가 발생했습니다.' })
  } finally {
    ui.hideSpinner()
  }
}
</script>
