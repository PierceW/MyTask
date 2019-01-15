<template>
    <div id="home">
      <v-header></v-header>
      <br>
      <br>
      <br>
      <br>
      <br>
      <h1>生命周期组件</h1>

      <button @click="run()">执行run方法</button>
    </div>
</template>

<script>
import Header from './Header'

export default {
  data () {
    return {
      msg: '这是生命周期'
    }
  },
  methods: {
    run () {
      alert(this.msg)
      this.msg = '修改了数据咯'
    }
  },
  components: {
    'v-header': Header
  },
  beforeCreate: function () {
    console.log('1.生命周期创建前')
  },
  created: function () {
    console.log('2.生命周期创建完成')
  },
  beforeMount: function () {
    console.log('3.模板编译前')
  },
  mounted: function () {
    console.log('4.模板编译后')
  },
  beforeUpdate: function () {
    console.log('5.数据更新前')
  },
  updated: function () {
    console.log('6.数据更新后')
  },
  beforeDestroy: function () {
    console.log('7.实例销毁前')
  },
  destroyed: function () {
    console.log('8.实例销毁后')
  }
}
</script>

<style scoped>

</style>
