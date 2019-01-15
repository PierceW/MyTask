<template>
    <div id="son">
      <h1>我是一个子组件，我要获取父组件的数据</h1>
      <hr>
      <h1>{{msg}}</h1>
      <button @click="getParent()">执行父组件的run方法</button>
    </div>
</template>

<script>
export default {
  methods: {
    getParent () {
      this.parent.run()
    }
  },
  props: ['msg', 'parent']
}
</script>

<style scoped>

</style>
