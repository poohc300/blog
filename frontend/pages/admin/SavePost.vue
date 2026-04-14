<template>
  <div class="w-full max-w-screen-lg mx-auto px-4 sm:px-6 lg:px-10 py-8 sm:py-12">

    <h1 class="text-2xl font-bold text-black mb-8">글 수정</h1>

    <div class="flex flex-col gap-5">

      <!-- 제목 -->
      <div class="flex flex-col gap-1.5">
        <label class="text-sm font-medium text-gray-700">제목</label>
        <input
          v-model="form.title"
          type="text"
          placeholder="제목을 입력하세요"
          class="w-full border border-gray-200 rounded px-3 py-2 text-sm text-black placeholder-gray-400 outline-none focus:border-black transition-colors"
        />
      </div>

      <!-- 태그 autocomplete -->
      <div class="flex flex-col gap-1.5 relative" ref="tagWrapRef">
        <label class="text-sm font-medium text-gray-700">태그</label>
        <input
          v-model="tagQuery"
          type="text"
          placeholder="태그를 검색하세요"
          autocomplete="off"
          class="w-full border border-gray-200 rounded px-3 py-2 text-sm text-black placeholder-gray-400 outline-none focus:border-black transition-colors"
          :class="{ 'border-black': form.topicId }"
          @focus="showDropdown = true"
          @input="form.topicId = null"
        />
        <span v-if="form.topicId" class="absolute right-3 top-[2.35rem] text-xs text-gray-400">✓</span>
        <ul
          v-if="showDropdown && filteredTopics.length"
          class="absolute top-full left-0 right-0 mt-1 bg-white border border-gray-200 rounded shadow-lg z-10 max-h-40 overflow-y-auto"
        >
          <li
            v-for="topic in filteredTopics" :key="topic.id"
            class="px-3 py-2 text-sm text-gray-700 hover:bg-gray-50 cursor-pointer"
            @mousedown.prevent="selectTopic(topic)"
          >
            {{ topic.name }}
          </li>
        </ul>
      </div>

      <!-- 내용 -->
      <div class="flex flex-col gap-1.5">
        <label class="text-sm font-medium text-gray-700">내용</label>
        <ClientOnly>
          <TiptapEditor v-model="form.body" />
        </ClientOnly>
      </div>

      <!-- 액션 버튼 -->
      <div class="flex justify-end gap-3 pt-2">
        <button
          :disabled="ui.loading"
          class="px-4 py-2 text-sm text-gray-600 hover:text-black transition-colors disabled:opacity-40"
          @click="navigateTo(`/posts/${postId}`)"
        >
          취소
        </button>
        <button
          :disabled="!isDirty || ui.loading"
          class="px-5 py-2 text-sm rounded font-medium transition-colors"
          :class="isDirty && !ui.loading
            ? 'bg-black text-white hover:bg-gray-800'
            : 'bg-gray-100 text-gray-400 cursor-not-allowed'"
          @click="handleSubmit"
        >
          저장
        </button>
      </div>

    </div>
  </div>
</template>

<script setup lang="ts">
import { cloneDeep } from 'lodash-es'
import { onClickOutside } from '@vueuse/core'

definePageMeta({ layout: 'admin', middleware: 'auth', ssr: false })

const ui = useUiStore()
const base = useApiBase()
const { authFetch } = useAuthFetch()
const route = useRoute()

const postId = Number(route.query.id)
if (!postId) await navigateTo('/admin/AddPost')

const [{ data: postRaw }, { data: topics }] = await Promise.all([
  useFetch<any>(`${base}/api/posts/${postId}`),
  useFetch<{ id: number; name: string }[]>(`${base}/api/topics`),
])

if (!postRaw.value) await navigateTo('/admin/AddPost')

// ── 초기 폼 ────────────────────────────────────────────────
const initialForm = {
  topicId: null as number | null,
  title: postRaw.value?.title ?? '',
  body: postRaw.value?.body ?? '',
}

const form = ref(cloneDeep(initialForm))

// 태그 초기값: 현재 태그 이름으로 토픽 찾기
const tagQuery = ref(postRaw.value?.tag ?? '')
const showDropdown = ref(false)
const tagWrapRef = ref<HTMLElement | null>(null)

// 초기 topicId 설정
const matchedTopic = (topics.value ?? []).find(t => t.name === postRaw.value?.tag)
form.value.topicId = matchedTopic?.id ?? null

const isDirty = computed(() =>
  form.value.title.trim() !== '' ||
  form.value.body.trim() !== '' ||
  form.value.topicId !== null
)

const filteredTopics = computed(() =>
  (topics.value ?? []).filter(t =>
    t.name.toLowerCase().includes(tagQuery.value.toLowerCase())
  )
)

function selectTopic(topic: { id: number; name: string }) {
  form.value.topicId = topic.id
  tagQuery.value = topic.name
  showDropdown.value = false
}

onClickOutside(tagWrapRef, () => { showDropdown.value = false })

async function handleSubmit() {
  if (!isDirty.value || ui.loading) return
  ui.showSpinner()
  try {
    await authFetch(`${base}/api/posts/${postId}`, {
      method: 'PUT',
      body: {
        topicId: form.value.topicId,
        title: form.value.title,
        body: form.value.body,
      },
    })
    await ui.alert({ icon: 'success', title: '저장 완료', text: '글이 성공적으로 수정되었습니다.', timer: 3000 })
    await navigateTo(`/posts/${postId}`)
  } catch {
    await ui.alert({ icon: 'error', title: '오류', text: '글 수정 중 오류가 발생했습니다.' })
  } finally {
    ui.hideSpinner()
  }
}
</script>
