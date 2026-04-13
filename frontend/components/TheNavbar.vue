<template>
  <header class="border-b border-gray-200 sticky top-0 bg-white z-10">
    <div class="w-full max-w-screen-xl mx-auto px-4 sm:px-6 lg:px-10 xl:px-16 py-4 flex items-center justify-between">
      <NuxtLink to="/" class="text-xl sm:text-2xl font-bold tracking-tight text-black">Jeremy.dev</NuxtLink>
      <nav class="flex items-center gap-4 sm:gap-6 text-sm text-gray-600">
        <NuxtLink to="/" class="hover:text-black transition-colors">글</NuxtLink>
        <a href="#" class="hover:text-black transition-colors hidden sm:block">태그</a>
        <a href="#" class="hover:text-black transition-colors hidden sm:block">소개</a>
      </nav>
    </div>
  </header>
</template>
