<template>
  <div class="min-h-screen bg-white font-sans flex flex-col">

    <TheNavbar />

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
