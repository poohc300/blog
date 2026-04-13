declare module 'vue-spinner' {
  import { DefineComponent } from 'vue'
  const RingLoader: DefineComponent<{
    loading?: boolean
    color?: string
    size?: number | string
    margin?: string
    radius?: string
  }>
  export { RingLoader }
}
