export function formatDate(dateStr: string): string {
  const d = new Date(dateStr)
  return `${d.getFullYear()}년 ${d.getMonth() + 1}월 ${d.getDate()}일`
}

export function calcReadTime(text: string): number {
  const plain = text.replace(/<[^>]*>/g, '')
  return Math.max(1, Math.ceil(plain.length / 500))
}
