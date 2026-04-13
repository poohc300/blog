<template>
  <div class="min-h-screen bg-gray-50 font-sans flex flex-col">

    <!-- Admin Header -->
    <header class="border-b border-gray-200 bg-white sticky top-0 z-10">
      <div class="w-full max-w-screen-xl mx-auto px-4 sm:px-6 lg:px-10 xl:px-16 py-3 flex items-center justify-between">
        <div class="flex items-center gap-4">
          <NuxtLink to="/" class="text-sm text-gray-400 hover:text-black transition-colors">
            ← 블로그로
          </NuxtLink>
          <span class="text-gray-200">|</span>
          <span class="text-sm font-semibold text-black">관리자</span>
        </div>
      </div>
    </header>

    <main class="flex-1">
      <slot />
    </main>

  </div>
</template>
