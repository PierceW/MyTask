<template>
    <div id="resource">
      <!--<h1>{{msg}}</h1>-->
      <!--<br>-->
      <!--<button @click="getData()">获取数据</button>-->
      <!--<br>-->
      <!--<hr>-->
      <!--{{data}}-->
      <!--<br>-->
      <!--<hr>-->
      <div v-if="data!==null">
        <h2>{{data.result.title}}</h2>
        <h3 @click="searchAuthors()">{{data.result.authors}}</h3>
        <h5 v-if="desc!==''">{{desc}}</h5>
        <h4 v-for="(item) in dataContent" :key="item">{{item}}</h4>
      </div>
    </div>
</template>
<script>
export default {
  data () {
    return {
      msg: '这是使用 vue-rsource 调用接口',
      data: null,
      dataContent: [],
      desc: ''
    }
  },
  methods: {
    getData () {
      var api = 'https://api.apiopen.top/recommendPoetry'
      // this.$http.get(api).then(({data}) => {
      //   console.log(data)
      //   this.data = data
      //   this.dataContent = this.data.result.content.split('|')
      // })
      this.$http.get(api).then(function (data) {
        this.data = data.data
        this.dataContent = this.data.result.content.split('|')
      }, function (err) {
        console.log(err)
      })
    },
    searchAuthors () {
      // var api = 'https://api.apiopen.top/searchAuthors?name=%E6%9D%8E%E7%99%BD'
      var api = 'https://api.apiopen.top/searchAuthors'
      this.$http.get(api, {emulateJSON: true, params: {name: this.data.result.authors}}).then(({data}) => {
        console.log(data)
        this.desc = data.result[0].desc
      })
    }
  },
  mounted: function () {
    this.getData()
  }
}
</script>

<style scoped>

</style>
