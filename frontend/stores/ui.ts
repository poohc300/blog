import Swal, { type SweetAlertIcon } from 'sweetalert2'

export const useUiStore = defineStore('ui', () => {
  // ── Spinner ─────────────────────────────────────────
  const loading = ref(false)

  function showSpinner() {
    loading.value = true
  }

  function hideSpinner() {
    loading.value = false
  }

  // ── SweetAlert2 ──────────────────────────────────────
  async function alert({
    icon = 'info' as SweetAlertIcon,
    title,
    text,
    timer,
  }: {
    icon?: SweetAlertIcon
    title: string
    text?: string
    timer?: number
  }) {
    await Swal.fire({
      icon,
      title,
      text,
      confirmButtonColor: '#000',
      confirmButtonText: '확인',
      ...(timer ? { timer, timerProgressBar: true } : {}),
    })
  }

  async function confirm({
    title,
    text,
    confirmText = '확인',
    cancelText = '취소',
  }: {
    title: string
    text?: string
    confirmText?: string
    cancelText?: string
  }) {
    const result = await Swal.fire({
      icon: 'warning',
      title,
      text,
      showCancelButton: true,
      confirmButtonColor: '#000',
      cancelButtonColor: '#9ca3af',
      confirmButtonText: confirmText,
      cancelButtonText: cancelText,
    })
    return result.isConfirmed
  }

  return { loading, showSpinner, hideSpinner, alert, confirm }
})
