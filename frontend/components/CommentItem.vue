<template>
  <div :style="{ paddingLeft: `${comment.depth * 1.5}rem` }">
    <div class="py-4 border-b border-gray-100">

      <template v-if="comment.deleted">
        <p class="text-sm text-gray-400 italic ml-9">삭제된 댓글입니다.</p>
      </template>

      <template v-else>
        <!-- Author / Meta -->
        <div class="flex items-center justify-between mb-2">
          <div class="flex items-center gap-2">
            <div class="w-7 h-7 rounded-full bg-gray-700 flex items-center justify-center text-white text-xs font-bold shrink-0">
              {{ comment.authorName?.charAt(0).toUpperCase() }}
            </div>
            <span class="text-sm font-medium text-black">{{ comment.authorName }}</span>
            <span class="text-xs text-gray-400">· {{ comment.date }}</span>
          </div>
          <button
            class="text-xs text-gray-300 hover:text-red-400 transition-colors px-1"
            @click="handleDelete"
          >
            삭제
          </button>
        </div>

        <!-- Body -->
        <p class="text-sm text-gray-700 leading-relaxed ml-9">{{ comment.body }}</p>

        <!-- Reply -->
        <button
          class="mt-2 ml-9 text-xs text-gray-400 hover:text-black transition-colors"
          @click="$emit('reply', comment.id)"
        >
          답글
        </button>
      </template>

    </div>
  </div>
</template>

<script setup lang="ts">
const props = defineProps<{
  comment: {
    id: number
    parentCommentId: number | null
    depth: number
    body: string | null
    authorName: string | null
    authorType: string
    deleted: boolean
    date: string
  }
}>()

const emit = defineEmits<{
  reply: [id: number]
  deleted: [id: number]
}>()

const ui = useUiStore()
const base = useApiBase()
const { isLoggedIn } = useAuth()

async function handleDelete() {
  let guestPassword: string | null = null

  if (!isLoggedIn.value) {
    // 게스트: 비밀번호 입력받기
    const result = await ui.prompt({
      title: '댓글 삭제',
      text: '작성 시 입력한 비밀번호를 입력하세요.',
      inputType: 'password',
      confirmText: '삭제',
      cancelText: '취소',
    })
    if (result === null) return
    guestPassword = result
  } else {
    const confirmed = await ui.confirm({
      title: '댓글을 삭제할까요?',
      confirmText: '삭제',
      cancelText: '취소',
    })
    if (!confirmed) return
  }

  try {
    await $fetch(`${base}/api/comments/${props.comment.id}`, {
      method: 'DELETE',
      body: guestPassword !== null ? { guestPassword } : {},
    })
    emit('deleted', props.comment.id)
  } catch (e: any) {
    const msg = e?.data?.message ?? '삭제 중 오류가 발생했습니다.'
    await ui.alert({ icon: 'error', title: '오류', text: msg })
  }
}
</script>
