<template>
  <div class="border border-gray-200 rounded focus-within:border-black transition-colors">

    <!-- 툴바 -->
    <div class="flex flex-wrap gap-0.5 px-2 py-1.5 border-b border-gray-200 bg-gray-50 rounded-t">
      <TiptapToolBtn :active="editor?.isActive('bold')" title="Bold" @click="editor?.chain().focus().toggleBold().run()">
        <strong>B</strong>
      </TiptapToolBtn>
      <TiptapToolBtn :active="editor?.isActive('italic')" title="Italic" @click="editor?.chain().focus().toggleItalic().run()">
        <em>I</em>
      </TiptapToolBtn>
      <TiptapToolBtn :active="editor?.isActive('strike')" title="취소선" @click="editor?.chain().focus().toggleStrike().run()">
        <s>S</s>
      </TiptapToolBtn>

      <div class="w-px h-5 bg-gray-200 mx-1 self-center" />

      <TiptapToolBtn :active="editor?.isActive('heading', { level: 2 })" title="제목 2" @click="editor?.chain().focus().toggleHeading({ level: 2 }).run()">H2</TiptapToolBtn>
      <TiptapToolBtn :active="editor?.isActive('heading', { level: 3 })" title="제목 3" @click="editor?.chain().focus().toggleHeading({ level: 3 }).run()">H3</TiptapToolBtn>

      <div class="w-px h-5 bg-gray-200 mx-1 self-center" />

      <TiptapToolBtn :active="editor?.isActive('bulletList')" title="목록" @click="editor?.chain().focus().toggleBulletList().run()">≡</TiptapToolBtn>
      <TiptapToolBtn :active="editor?.isActive('orderedList')" title="번호 목록" @click="editor?.chain().focus().toggleOrderedList().run()">1.</TiptapToolBtn>
      <TiptapToolBtn :active="editor?.isActive('blockquote')" title="인용" @click="editor?.chain().focus().toggleBlockquote().run()">"</TiptapToolBtn>

      <div class="w-px h-5 bg-gray-200 mx-1 self-center" />

      <TiptapToolBtn :active="editor?.isActive('code')" title="인라인 코드" @click="editor?.chain().focus().toggleCode().run()">`</TiptapToolBtn>
      <TiptapToolBtn :active="editor?.isActive('codeBlock')" title="코드 블록" @click="editor?.chain().focus().toggleCodeBlock().run()">{ }</TiptapToolBtn>

      <div class="w-px h-5 bg-gray-200 mx-1 self-center" />

      <TiptapToolBtn title="링크" :active="editor?.isActive('link')" @click="setLink">🔗</TiptapToolBtn>
      <TiptapToolBtn title="이미지 업로드" @click="triggerImageUpload">🖼</TiptapToolBtn>

      <div class="w-px h-5 bg-gray-200 mx-1 self-center" />

      <TiptapToolBtn title="실행 취소" @click="editor?.chain().focus().undo().run()">↩</TiptapToolBtn>
      <TiptapToolBtn title="다시 실행" @click="editor?.chain().focus().redo().run()">↪</TiptapToolBtn>
    </div>

    <!-- 에디터 영역 -->
    <EditorContent :editor="editor" class="prose prose-gray max-w-none px-4 py-3 min-h-[240px] text-sm focus:outline-none" />

    <!-- 이미지 업로드 hidden input -->
    <input ref="imageInputRef" type="file" accept="image/*" class="hidden" @change="onImageSelected" />
  </div>
</template>

<script setup lang="ts">
import { useEditor, EditorContent } from '@tiptap/vue-3'
import StarterKit from '@tiptap/starter-kit'
import Image from '@tiptap/extension-image'
import Link from '@tiptap/extension-link'
import Placeholder from '@tiptap/extension-placeholder'
import CodeBlockLowlight from '@tiptap/extension-code-block-lowlight'
import { common, createLowlight } from 'lowlight'

const props = defineProps<{ modelValue: string }>()
const emit = defineEmits<{ 'update:modelValue': [value: string] }>()

const ui = useUiStore()
const base = useApiBase()
const imageInputRef = ref<HTMLInputElement | null>(null)
const lowlight = createLowlight(common)

const editor = useEditor({
  extensions: [
    StarterKit.configure({ codeBlock: false }),
    CodeBlockLowlight.configure({ lowlight }),
    Image.configure({ inline: false, allowBase64: false }),
    Link.configure({ openOnClick: false }),
    Placeholder.configure({ placeholder: '내용을 입력하세요...' }),
  ],
  content: props.modelValue,
  onUpdate: ({ editor }) => {
    emit('update:modelValue', editor.getHTML())
  },
})

// 부모에서 v-model 값이 바뀌면 동기화 (초기화 등)
watch(() => props.modelValue, (val) => {
  if (editor.value && editor.value.getHTML() !== val) {
    editor.value.commands.setContent(val, { emitUpdate: false })
  }
})

onBeforeUnmount(() => editor.value?.destroy())

// ── 링크 ──────────────────────────────────────────────
function setLink() {
  const prev = editor.value?.getAttributes('link').href ?? ''
  const url = window.prompt('링크 URL', prev)
  if (url === null) return
  if (url === '') {
    editor.value?.chain().focus().extendMarkRange('link').unsetLink().run()
  } else {
    editor.value?.chain().focus().extendMarkRange('link').setLink({ href: url }).run()
  }
}

// ── 이미지 업로드 ──────────────────────────────────────
function triggerImageUpload() {
  imageInputRef.value?.click()
}

async function onImageSelected(e: Event) {
  const file = (e.target as HTMLInputElement).files?.[0]
  if (!file) return
  ;(e.target as HTMLInputElement).value = ''

  ui.showSpinner()
  try {
    const form = new FormData()
    form.append('file', file)
    const res = await $fetch<{ url: string }>(`${base}/api/upload/image`, {
      method: 'POST',
      body: form,
    })
    editor.value?.chain().focus().setImage({ src: res.url }).run()
  } catch {
    await ui.alert({ icon: 'error', title: '업로드 실패', text: '이미지 업로드 중 오류가 발생했습니다.' })
  } finally {
    ui.hideSpinner()
  }
}
</script>

<style>
/* Placeholder */
.tiptap p.is-editor-empty:first-child::before {
  color: #9ca3af;
  content: attr(data-placeholder);
  float: left;
  height: 0;
  pointer-events: none;
}
/* 에디터 포커스 아웃라인 제거 */
.tiptap:focus {
  outline: none;
}
</style>
