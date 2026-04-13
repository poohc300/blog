<template>
  <div class="fixed inset-0 z-50 flex items-center justify-center">

    <!-- Backdrop -->
    <div class="absolute inset-0 bg-black/40" @click="handleCancel" />

    <!-- Modal -->
    <div class="relative bg-white w-full max-w-xl mx-4 rounded-lg shadow-xl flex flex-col">

      <!-- Header -->
      <div class="px-6 py-5 border-b border-gray-200">
        <h2 class="text-lg font-bold text-black">글쓰기</h2>
      </div>

      <!-- Body -->
      <div class="px-6 py-5 flex flex-col gap-4">

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
          <!-- 선택 완료 표시 -->
          <span
            v-if="form.topicId"
            class="absolute right-3 top-[2.35rem] text-xs text-gray-400"
          >✓</span>
          <!-- 드롭다운 -->
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
          <textarea
            v-model="form.body"
            placeholder="내용을 입력하세요"
            rows="8"
            class="w-full border border-gray-200 rounded px-3 py-2 text-sm text-black placeholder-gray-400 outline-none focus:border-black transition-colors resize-none"
          />
        </div>

        <!-- 로딩바 -->
        <div v-if="loading" class="w-full h-1 bg-gray-100 rounded overflow-hidden">
          <div class="h-full bg-black rounded animate-loading-bar" />
        </div>

      </div>

      <!-- Footer -->
      <div class="px-6 py-4 border-t border-gray-200 flex justify-end gap-3">
        <button
          :disabled="loading"
          class="px-4 py-2 text-sm text-gray-600 hover:text-black transition-colors disabled:opacity-40"
          @click="handleCancel"
        >
          취소
        </button>
        <button
          :disabled="!isDirty || loading"
          class="px-4 py-2 text-sm rounded font-medium transition-colors"
          :class="isDirty && !loading
            ? 'bg-black text-white hover:bg-gray-800'
            : 'bg-gray-100 text-gray-400 cursor-not-allowed'"
          @click="handleConfirm"
        >
          확인
        </button>
      </div>

    </div>
  </div>
</template>

<script setup lang="ts">
import { cloneDeep } from 'lodash-es'
import { onClickOutside } from '@vueuse/core'

const { $swal } = useNuxtApp()
const base = useApiBase()

const emit = defineEmits<{
  done: []
  cancel: []
}>()

// ── 토픽 목록 ──────────────────────────────────────────
const { data: topics } = await useFetch<{ id: number; name: string }[]>(
  `${base}/api/topics`
)

// ── 폼 상태 ────────────────────────────────────────────
const initialForm = { topicId: null as number | null, title: '', body: '' }
const form = ref(cloneDeep(initialForm))

const isDirty = computed(() =>
  form.value.title.trim() !== '' ||
  form.value.body.trim() !== '' ||
  form.value.topicId !== null
)

// ── 태그 autocomplete ──────────────────────────────────
const tagQuery = ref('')
const showDropdown = ref(false)
const tagWrapRef = ref<HTMLElement | null>(null)

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

// ── 액션 ──────────────────────────────────────────────
const loading = ref(false)

function resetForm() {
  form.value = cloneDeep(initialForm)
  tagQuery.value = ''
}

function handleCancel() {
  if (loading.value) return
  resetForm()
  emit('cancel')
}

async function handleConfirm() {
  if (!isDirty.value || loading.value) return
  loading.value = true
  try {
    await $fetch(`${base}/api/posts`, {
      method: 'POST',
      body: {
        topicId: form.value.topicId,
        title: form.value.title,
        body: form.value.body,
        authorId: 1, // TODO: 로그인 유저 ID 연결
      },
    })
    resetForm()
    emit('done')
    await $swal.fire({
      icon: 'success',
      title: '등록 완료',
      text: '글이 성공적으로 등록되었습니다.',
      confirmButtonColor: '#000',
      confirmButtonText: '확인',
      timer: 3000,
      timerProgressBar: true,
    })
  } catch {
    await $swal.fire({
      icon: 'error',
      title: '오류',
      text: '글 등록 중 오류가 발생했습니다.',
      confirmButtonColor: '#000',
      confirmButtonText: '확인',
    })
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
@keyframes loading-bar {
  0%   { width: 0%; margin-left: 0; }
  50%  { width: 60%; margin-left: 20%; }
  100% { width: 0%; margin-left: 100%; }
}
.animate-loading-bar {
  animation: loading-bar 1.2s ease-in-out infinite;
}
</style>
