<template>
  <div class="min-h-screen bg-gray-50 flex items-center justify-center px-4">
    <div class="w-full max-w-sm bg-white rounded-lg shadow-sm border border-gray-200 px-8 py-10">

      <h1 class="text-xl font-bold text-black mb-8 text-center">Jeremy.dev</h1>

      <form class="flex flex-col gap-4" @submit.prevent="handleLogin">

        <div class="flex flex-col gap-1.5">
          <label class="text-sm font-medium text-gray-700">아이디</label>
          <input
            v-model="form.username"
            type="text"
            placeholder="아이디"
            autocomplete="username"
            class="w-full border border-gray-200 rounded px-3 py-2 text-sm text-black placeholder-gray-400 outline-none focus:border-black transition-colors"
          />
        </div>

        <div class="flex flex-col gap-1.5">
          <label class="text-sm font-medium text-gray-700">비밀번호</label>
          <input
            v-model="form.password"
            type="password"
            placeholder="비밀번호"
            autocomplete="current-password"
            class="w-full border border-gray-200 rounded px-3 py-2 text-sm text-black placeholder-gray-400 outline-none focus:border-black transition-colors"
          />
        </div>

        <p v-if="errorMsg" class="text-xs text-red-500">{{ errorMsg }}</p>

        <button
          type="submit"
          :disabled="ui.loading"
          class="mt-2 w-full py-2 text-sm font-medium rounded transition-colors"
          :class="ui.loading
            ? 'bg-gray-100 text-gray-400 cursor-not-allowed'
            : 'bg-black text-white hover:bg-gray-800'"
        >
          로그인
        </button>

      </form>
    </div>
  </div>
</template>

<script setup lang="ts">
definePageMeta({ layout: false })

const ui = useUiStore()
const base = useApiBase()
const { setUser } = useAuth()

const form = ref({ username: '', password: '' })
const errorMsg = ref('')

async function handleLogin() {
  if (!form.value.username || !form.value.password) return
  errorMsg.value = ''
  ui.showSpinner()
  try {
    const res = await $fetch<{ userId: number; username: string; accessToken: string }>(
      `${base}/api/auth/login`,
      { method: 'POST', body: form.value }
    )
    setUser({ id: res.userId, username: res.username }, res.accessToken)
    await navigateTo('/admin')
  } catch {
    errorMsg.value = '아이디 또는 비밀번호가 올바르지 않습니다.'
  } finally {
    ui.hideSpinner()
  }
}
</script>
