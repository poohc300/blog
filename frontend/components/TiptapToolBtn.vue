<template>
  <button
    type="button"
    :title="title"
    class="px-2 py-1 rounded text-xs text-gray-600 hover:bg-gray-200 transition-colors min-w-[28px] flex items-center justify-center"
    :class="{ 'bg-gray-200 text-black font-semibold': active }"
    @click="$emit('click')"
  >
    <slot />
  </button>
</template>

<script setup lang="ts">
defineProps<{ active?: boolean; title?: string }>()
defineEmits(['click'])
</script>
