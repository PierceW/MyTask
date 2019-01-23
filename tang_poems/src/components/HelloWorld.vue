<template>
  <div class="hello">
    <h1>{{msg}}</h1>
    <hr>
    <h2>{{data.title}}</h2>
    <h3 @click="getAuthorDesc(data.authors)">-- {{data.authors}} --</h3>
    <h4 v-for="(text, index) in data.content" :key="index">{{text}}</h4>
    <hr>
    <h4>{{desc}}</h4>
  </div>
</template>

<script>
export default {
  name: 'home',
  data () {
    return {
      msg: '每日一句',
      data: [],
      desc: ''
    }
  },
  methods: {
    getDailyRound () {
      this.$http.get('https://api.apiopen.top/recommendPoetry').then((res) => {
        this.data = res.data.result
        this.data.content = this.data.content.split('|')
      }).catch((res) => {
        console.log(res)
      })
    },
    getAuthorDesc (author) {
      this.$http.get('https://api.apiopen.top/searchAuthors?name=' + author).then((res) => {
        console.log(res)
        this.desc = res.data.result[0].desc
      }).catch((res) => {
        console.log(res)
      })
    }
  },
  mounted () {
    this.getDailyRound()
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
