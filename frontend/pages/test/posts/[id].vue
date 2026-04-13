<template>
  <div class="w-full max-w-screen-xl mx-auto px-4 sm:px-6 lg:px-10 xl:px-16 py-8 sm:py-12">

    <!-- Article -->
    <article class="max-w-2xl mx-auto">

      <!-- Meta -->
      <div class="mb-6">
        <span class="text-xs px-2.5 py-1 bg-gray-100 text-gray-600 rounded-full">DevOps</span>
      </div>

      <!-- Title -->
      <h1 class="text-2xl sm:text-3xl xl:text-4xl font-bold text-black leading-tight mb-4">
        SSH 터널링으로 원격 서버에서 OAuth 인증하기
      </h1>

      <!-- Author -->
      <div class="flex items-center gap-3 mb-8 pb-8 border-b border-gray-200">
        <div class="w-9 h-9 rounded-full bg-black flex items-center justify-center text-white text-sm font-bold">J</div>
        <div>
          <span class="text-sm font-medium text-black">Jeremy</span>
          <span class="text-gray-400 text-sm"> · 2026년 4월 10일 · 5분 읽기</span>
        </div>
      </div>

      <!-- Body -->
      <div class="prose prose-gray max-w-none text-gray-800 leading-relaxed space-y-6">

        <p>
          라즈베리파이 서버에서 rclone으로 Google Drive 백업을 설정하던 중 문제가 생겼다.
          rclone 인증은 브라우저를 열어야 하는데, 서버는 GUI가 없는 헤드리스 환경이었다.
          게다가 당시 나는 집 밖에서 SSH로 접속 중이었다.
        </p>

        <h2 class="text-xl font-bold text-black mt-8 mb-3">문제 상황</h2>
        <p>
          <code class="bg-gray-100 text-gray-800 px-1.5 py-0.5 rounded text-sm">rclone config</code>로 Google Drive 리모트를 설정하면
          마지막 단계에서 브라우저 인증 URL이 뜬다. 로컬 환경이라면 자동으로 브라우저가 열리지만,
          SSH 세션에서는 그럴 수 없다.
        </p>
        <p>
          rclone이 기본적으로 <code class="bg-gray-100 text-gray-800 px-1.5 py-0.5 rounded text-sm">http://127.0.0.1:53682</code>에
          임시 로컬 서버를 띄우고 거기서 OAuth 콜백을 받는 구조다.
          즉, 이 포트를 내 로컬 PC에서 접근할 수 있으면 된다.
        </p>

        <h2 class="text-xl font-bold text-black mt-8 mb-3">해결 방법 — SSH 로컬 포트 포워딩</h2>
        <p>SSH의 <code class="bg-gray-100 text-gray-800 px-1.5 py-0.5 rounded text-sm">-L</code> 옵션을 사용하면
          원격 서버의 특정 포트를 내 로컬 PC의 포트로 포워딩할 수 있다.</p>

        <pre class="bg-gray-950 text-gray-100 rounded p-4 text-sm overflow-x-auto"><code>ssh -L 53682:localhost:53682 user@your-server</code></pre>

        <p>이 명령으로 접속하면, 내 로컬 브라우저에서
          <code class="bg-gray-100 text-gray-800 px-1.5 py-0.5 rounded text-sm">http://127.0.0.1:53682</code>에 접속했을 때
          실제로는 서버의 53682 포트로 연결된다.</p>

        <h2 class="text-xl font-bold text-black mt-8 mb-3">전체 절차</h2>
        <p>1. 포트 포워딩을 포함해 SSH 접속한다.</p>
        <pre class="bg-gray-950 text-gray-100 rounded p-4 text-sm overflow-x-auto"><code>ssh -L 53682:localhost:53682 user@your-server</code></pre>

        <p>2. 서버에서 rclone 인증을 백그라운드로 실행하고 URL을 파일로 저장한다.</p>
        <pre class="bg-gray-950 text-gray-100 rounded p-4 text-sm overflow-x-auto"><code>rclone authorize "drive" 2>&1 | tee /tmp/rclone_auth.txt &</code></pre>

        <p>3. URL을 확인한다.</p>
        <pre class="bg-gray-950 text-gray-100 rounded p-4 text-sm overflow-x-auto"><code>cat /tmp/rclone_auth.txt</code></pre>

        <p>4. 출력된 <code class="bg-gray-100 text-gray-800 px-1.5 py-0.5 rounded text-sm">http://127.0.0.1:53682/auth?state=...</code> URL을
          로컬 브라우저에서 열어 Google 계정으로 인증한다.</p>

        <p>5. 인증이 완료되면 터미널에 토큰이 출력된다. 이 토큰을
          <code class="bg-gray-100 text-gray-800 px-1.5 py-0.5 rounded text-sm">rclone config</code>의 token 항목에 붙여넣으면 설정이 완료된다.</p>

        <h2 class="text-xl font-bold text-black mt-8 mb-3">왜 서비스 계정은 안 되나</h2>
        <p>
          처음에는 Google Cloud 서비스 계정 JSON 키를 사용하려 했다.
          서비스 계정은 서버 간 인증에 적합해 보이지만, 개인 Google Drive에는 접근 권한이 없다.
          서비스 계정은 독립된 Google 계정처럼 동작하기 때문에, 내 드라이브 폴더를 공유해줘도
          rclone이 해당 폴더를 인식하지 못하는 문제가 있었다.
        </p>
        <p>
          결국 개인 Google Drive에 백업하려면 OAuth2 사용자 인증이 필수다.
          SSH 터널링은 그 인증을 헤드리스 서버에서 처리하는 가장 깔끔한 방법이었다.
        </p>

      </div>
    </article>

    <!-- Divider -->
    <div class="max-w-2xl mx-auto mt-16 mb-10 border-t border-gray-200"></div>

    <!-- Related Posts -->
    <div class="max-w-2xl mx-auto">
      <h3 class="text-base font-bold text-black mb-6">다른 글</h3>
      <div class="divide-y divide-gray-100">
        <article
          v-for="post in relatedPosts" :key="post.id"
          class="py-5 flex gap-4 items-start group cursor-pointer"
          @click="$router.push(`/test/posts/${post.id}`)"
        >
          <div class="flex-1">
            <div class="flex items-center gap-2 mb-2">
              <div class="w-5 h-5 rounded-full bg-black flex items-center justify-center text-white text-xs font-bold">J</div>
              <span class="text-xs text-gray-500">Jeremy · {{ post.date }}</span>
            </div>
            <h4 class="text-sm sm:text-base font-bold text-black mb-1 group-hover:underline leading-snug">
              {{ post.title }}
            </h4>
            <div class="flex items-center gap-2 mt-2">
              <span class="text-xs px-2 py-0.5 bg-gray-100 text-gray-600 rounded-full">{{ post.tag }}</span>
              <span class="text-xs text-gray-400">{{ post.readTime }}분 읽기</span>
            </div>
          </div>
          <div class="w-16 h-12 sm:w-24 sm:h-16 bg-gray-100 rounded shrink-0 flex items-center justify-center">
            <span class="text-gray-300 text-xs">이미지</span>
          </div>
        </article>
      </div>
    </div>

  </div>
</template>

<script setup lang="ts">
const relatedPosts = [
  {
    id: 2,
    title: 'SSR vs 정적 배포 — Nuxt로 블로그 만들며 배운 것들',
    date: '2026년 4월 10일',
    tag: 'Nuxt',
    readTime: 6,
  },
  {
    id: 3,
    title: 'PostgreSQL 스키마 설계 — 블로그 댓글 재귀 구조 만들기',
    date: '2026년 4월 10일',
    tag: 'Database',
    readTime: 4,
  },
  {
    id: 4,
    title: 'GitHub Actions Self-hosted Runner on Raspberry Pi',
    date: '2026년 4월 10일',
    tag: 'DevOps',
    readTime: 7,
  },
]
</script>
