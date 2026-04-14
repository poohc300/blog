<template>
  <div class="w-full max-w-screen-xl mx-auto px-4 sm:px-6 lg:px-10 xl:px-16 py-8 sm:py-12">

    <template v-if="post">
      <!-- 본문 -->
      <ArticleBody
        :post="post"
        @edit="openEdit"
        @delete="handleDelete"
      />

      <!-- 구분선 -->
      <div class="max-w-2xl mx-auto mt-16 mb-10 border-t border-gray-200" />

      <!-- 댓글 섹션 -->
      <div class="max-w-2xl mx-auto mb-16">
        <h3 class="text-base font-bold text-black mb-6">댓글 {{ comments.length }}개</h3>

        <!-- 댓글 목록 -->
        <div v-if="comments.length > 0">
          <CommentItem
            v-for="c in comments" :key="c.id"
            :comment="c"
            @reply="setReply"
            @deleted="onCommentDeleted"
          />
        </div>

        <!-- 댓글 작성 폼 -->
        <div class="mt-8 pt-6 border-t border-gray-100">
          <p v-if="replyTarget" class="text-xs text-gray-400 mb-3">
            └ 답글 작성 중
            <button class="ml-2 underline hover:text-black" @click="replyTarget = null">취소</button>
          </p>

          <!-- 게스트 전용 입력 필드 -->
          <div v-if="!isLoggedIn" class="flex gap-2 mb-3">
            <input
              v-model="guestForm.name"
              type="text"
              placeholder="이름"
              maxlength="20"
              class="w-32 border border-gray-200 rounded px-3 py-2 text-sm outline-none focus:border-black transition-colors"
            />
            <input
              v-model="guestForm.password"
              type="password"
              placeholder="비밀번호"
              maxlength="20"
              class="w-32 border border-gray-200 rounded px-3 py-2 text-sm outline-none focus:border-black transition-colors"
            />
          </div>

          <div class="flex gap-2">
            <textarea
              v-model="commentBody"
              placeholder="댓글을 입력하세요"
              rows="3"
              class="flex-1 border border-gray-200 rounded px-3 py-2 text-sm outline-none focus:border-black transition-colors resize-none"
            />
            <button
              :disabled="!canSubmit"
              class="px-4 py-2 text-sm font-medium rounded self-end transition-colors"
              :class="canSubmit ? 'bg-black text-white hover:bg-gray-800' : 'bg-gray-100 text-gray-400 cursor-not-allowed'"
              @click="submitComment"
            >
              등록
            </button>
          </div>
        </div>
      </div>

      <!-- 다른 글 -->
      <div class="max-w-2xl mx-auto">
        <h3 class="text-base font-bold text-black mb-6">다른 글</h3>
        <div class="divide-y divide-gray-100">
          <PostCard
            v-for="related in relatedPosts" :key="related.id"
            :post="related"
            :compact="true"
          />
        </div>
      </div>
    </template>

  </div>
</template>

<script setup lang="ts">
import { formatDate, calcReadTime } from '~/utils/post'

const route = useRoute()
const base = useApiBase()
const ui = useUiStore()
const { isLoggedIn } = useAuth()
const { authFetch } = useAuthFetch()
const id = route.params.id

const { data: raw } = await useFetch<any>(`${base}/api/posts/${id}`)
const { data: allRaw } = await useFetch<any[]>(`${base}/api/posts`)
const { data: commentRaw, refresh: refreshComments } = await useFetch<any[]>(`${base}/api/posts/${id}/comments`)

const post = computed(() => {
  if (!raw.value) return null
  const p = raw.value
  return {
    id: p.id,
    authorId: p.authorId,
    title: p.title,
    body: p.body ?? '',
    summary: p.summary ?? '',
    date: formatDate(p.createdAt),
    createdAt: p.createdAt,
    tag: p.tag,
    readTime: calcReadTime(p.body ?? ''),
  }
})

// ── SEO ────────────────────────────────────────────────────
useSeoMeta({
  title: () => post.value ? `${post.value.title} | Jeremy.dev` : 'Jeremy.dev',
  description: () => post.value?.summary || '',
  ogTitle: () => post.value?.title || '',
  ogDescription: () => post.value?.summary || '',
  ogType: 'article',
  ogUrl: () => `https://blog.nexacromancer.win/posts/${id}`,
  twitterCard: 'summary',
})

useHead({
  script: [{
    type: 'application/ld+json',
    innerHTML: computed(() => post.value ? JSON.stringify({
      '@context': 'https://schema.org',
      '@type': 'BlogPosting',
      headline: post.value.title,
      description: post.value.summary,
      datePublished: post.value.createdAt,
      author: { '@type': 'Person', name: 'Jeremy' },
      publisher: { '@type': 'Person', name: 'Jeremy' },
      url: `https://blog.nexacromancer.win/posts/${id}`,
    }) : ''),
  }],
})

// ── 다른 글 ────────────────────────────────────────────────
const relatedPosts = computed(() =>
  (allRaw.value ?? [])
    .filter(p => p.id !== Number(id))
    .slice(0, 3)
    .map(p => ({
      id: p.id,
      title: p.title,
      summary: p.summary ?? '',
      date: formatDate(p.createdAt),
      tag: p.tag,
      readTime: calcReadTime(p.summary ?? ''),
    }))
)

// ── 댓글 ───────────────────────────────────────────────────
const comments = computed(() =>
  (commentRaw.value ?? []).map((c: any) => ({
    id: c.id,
    parentCommentId: c.parentCommentId,
    depth: c.depth,
    body: c.body,
    authorName: c.authorName,
    authorType: c.authorType,
    deleted: c.deleted,
    date: formatDate(c.createdAt),
  }))
)

const replyTarget = ref<number | null>(null)
const commentBody = ref('')
const guestForm = ref({ name: '', password: '' })

const canSubmit = computed(() => {
  if (!commentBody.value.trim()) return false
  if (!isLoggedIn.value && (!guestForm.value.name.trim() || !guestForm.value.password.trim())) return false
  return true
})

function setReply(commentId: number) {
  replyTarget.value = commentId
}

async function submitComment() {
  if (!canSubmit.value) return
  try {
    await $fetch(`${base}/api/posts/${id}/comments`, {
      method: 'POST',
      body: {
        parentCommentId: replyTarget.value,
        body: commentBody.value,
        guestName: isLoggedIn.value ? undefined : guestForm.value.name,
        guestPassword: isLoggedIn.value ? undefined : guestForm.value.password,
      },
    })
    commentBody.value = ''
    replyTarget.value = null
    if (!isLoggedIn.value) guestForm.value = { name: '', password: '' }
    await refreshComments()
  } catch (e: any) {
    const msg = e?.data?.message ?? '댓글 등록 중 오류가 발생했습니다.'
    await ui.alert({ icon: 'error', title: '오류', text: msg })
  }
}

function onCommentDeleted(_id: number) {
  refreshComments()
}

// ── 글 수정/삭제 ────────────────────────────────────────────
function openEdit() {
  navigateTo(`/admin/SavePost?id=${id}`)
}

async function handleDelete() {
  const confirmed = await ui.confirm({
    title: '글을 삭제할까요?',
    text: '삭제하면 되돌릴 수 없습니다.',
    confirmText: '삭제',
    cancelText: '취소',
  })
  if (!confirmed) return
  ui.showSpinner()
  try {
    await authFetch(`${base}/api/posts/${id}`, { method: 'DELETE' })
    await navigateTo('/')
  } catch {
    await ui.alert({ icon: 'error', title: '오류', text: '삭제 중 오류가 발생했습니다.' })
  } finally {
    ui.hideSpinner()
  }
}
</script>
