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

        <div class="flex flex-col gap-1.5">
          <label class="text-sm font-medium text-gray-700">제목</label>
          <input
            v-model="form.title"
            type="text"
            placeholder="제목을 입력하세요"
            class="w-full border border-gray-200 rounded px-3 py-2 text-sm text-black placeholder-gray-400 outline-none focus:border-black transition-colors"
          />
        </div>

        <div class="flex flex-col gap-1.5">
          <label class="text-sm font-medium text-gray-700">태그</label>
          <input
            v-model="form.tag"
            type="text"
            placeholder="태그를 입력하세요"
            class="w-full border border-gray-200 rounded px-3 py-2 text-sm text-black placeholder-gray-400 outline-none focus:border-black transition-colors"
          />
        </div>

        <div class="flex flex-col gap-1.5">
          <label class="text-sm font-medium text-gray-700">내용</label>
          <textarea
            v-model="form.body"
            placeholder="내용을 입력하세요"
            rows="8"
            class="w-full border border-gray-200 rounded px-3 py-2 text-sm text-black placeholder-gray-400 outline-none focus:border-black transition-colors resize-none"
          />
        </div>

      </div>

      <!-- Footer -->
      <div class="px-6 py-4 border-t border-gray-200 flex justify-end gap-3">
        <button
          class="px-4 py-2 text-sm text-gray-600 hover:text-black transition-colors"
          @click="handleCancel"
        >
          취소
        </button>
        <button
          :disabled="!isDirty"
          class="px-4 py-2 text-sm rounded font-medium transition-colors"
          :class="isDirty
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

const emit = defineEmits<{
  confirm: [data: typeof form.value]
  cancel: []
}>()

const initialForm = {
  title: '',
  tag: '',
  body: '',
}

const form = ref(cloneDeep(initialForm))

const isDirty = computed(() =>
  Object.keys(initialForm).some(
    key => form.value[key as keyof typeof initialForm].trim() !== ''
  )
)

function handleCancel() {
  form.value = cloneDeep(initialForm)
  emit('cancel')
}

function handleConfirm() {
  if (!isDirty.value) return
  emit('confirm', cloneDeep(form.value))
  form.value = cloneDeep(initialForm)
}
</script>
