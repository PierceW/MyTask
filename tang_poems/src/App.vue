<template>
  <div id="app">
    <div class="page-tabbar">
      <div class="page-wrap">
        <router-view/>
      </div>

      <mt-tabbar v-model="selected" fixed>
        <mt-tab-item id="daily">
          <img slot="icon" src="./assets/bianfu.png">
          每日一句
        </mt-tab-item>
        <mt-tab-item id="订单">
          <img slot="icon" src="./assets/daxiang.png">
          订单
        </mt-tab-item>
        <mt-tab-item id="发现">
          <img slot="icon" src="./assets/lv.png">
          发现
        </mt-tab-item>
        <mt-tab-item id="mine">
          <img slot="icon" src="./assets/shizi.png">
          我的
        </mt-tab-item>
      </mt-tabbar>
    </div>
  </div>
</template>

<script>
export default {
  name: 'App',
  data () {
    return {
      selected: '每日一句'
    }
  },
  methods: {
    goTo (data) {
      console.log(data)
    }
  },
  watch: {
    selected: function (val, oldVal) {
      switch (val) {
        case 'daily':
          this.$router.push('/')
          break
        case '订单':
          this.$router.push('/mineHome')
          break
        case '发现':
          this.$router.push('/')
          break
        case 'mine':
          this.$router.push('/mineHome')
          break
      }
    }
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
.page-tabbar {
  overflow: hidden;
  height: 100vh;
}
.page-wrap {
  overflow: auto;
  height: 90%;
  padding-bottom: 100px;
}
</style>
