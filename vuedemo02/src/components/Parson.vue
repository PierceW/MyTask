<template>
    <div id="parent">
      <v-son :msg="msg" :parent="this"></v-son>
    </div>
</template>

<script>
import Son from './Son'

export default {
  name: 'Parson',
  data () {
    return {
      msg: '这是一个父组件的msg数据'
    }
  },
  methods: {
    run () {
      alert('父组件的run方法')
    }
  },
  components: {
    'v-son': Son
  }
}
</script>

<style scoped>

</style>
