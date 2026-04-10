<template>
  <div class="min-h-screen bg-white font-sans flex flex-col">

    <!-- Header -->
    <header class="border-b border-gray-200 sticky top-0 bg-white z-10">
      <div class="w-full max-w-screen-xl mx-auto px-4 sm:px-6 lg:px-10 xl:px-16 py-4 flex items-center justify-between">
        <span class="text-xl sm:text-2xl font-bold tracking-tight text-black">Jeremy.dev</span>
        <nav class="flex items-center gap-4 sm:gap-6 text-sm text-gray-600">
          <a href="#" class="hover:text-black transition-colors">글</a>
          <a href="#" class="hover:text-black transition-colors hidden sm:block">태그</a>
          <a href="#" class="hover:text-black transition-colors hidden sm:block">소개</a>
        </nav>
      </div>
    </header>

    <!-- Page Content -->
    <main class="flex-1">
      <slot />
    </main>

    <!-- Footer -->
    <footer class="border-t border-gray-100 py-8">
      <div class="w-full max-w-screen-xl mx-auto px-4 sm:px-6 lg:px-10 xl:px-16">
        <span class="text-sm text-gray-400">© 2026 Jeremy.dev</span>
      </div>
    </footer>

  </div>
</template>
