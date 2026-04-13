<template>
  <Transition name="fade">
    <div
      v-if="ui.loading"
      class="fixed inset-0 z-[100] flex items-center justify-center bg-black/30"
    >
      <RingLoader color="#ffffff" :size="60" />
    </div>
  </Transition>
</template>

<script setup lang="ts">
import RingLoader from 'vue-spinner/src/RingLoader.vue'

const ui = useUiStore()
</script>

<style scoped>
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.15s ease;
}
.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
