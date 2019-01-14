<template>
  <div id="app">
    <input v-model="todo" v-on:keyup.enter="addTodo"/>
    <button @click="addTodo()" >添加todo</button>

    <hr>

    <h1>正在进行中。。。。。。</h1>
    <ul>
      <li v-for="(item,key) in list" :key="item.key" v-if="!item.checked">
        <input type="checkbox" v-on:change="changeData(item, key)" v-model="item.checked"/> {{item.msg}} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button @click="removeTodo(key)">删除</button>
      </li>
    </ul>

    <h1>已完成！！！！！！</h1>
    <ul>
      <li v-for="(item,key) in list" :key="item.key" v-if="item.checked">
        <input type="checkbox" v-on:change="changeData(item, key)" v-model="item.checked"/> {{item.msg}} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button @click="removeTodo(key)">删除</button>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'TodoList',
  data () {
    return {
      todo: '',
      list: window.localStorage.LIST.length > 0 ? JSON.parse(window.localStorage.LIST) : []
    }
  },
  methods: {
    addTodo () {
      this.list.push({
        msg: this.todo,
        checked: false
      })
      this.todo = ''
    },
    removeTodo (key) {
      this.list.splice(key, 1)
    },
    changeData (val, key) {
      this.list.splice(key, 1)
      this.list.push(val)
    }
  },
  watch: {
    list: function () {
      window.localStorage.LIST = JSON.stringify(this.list)
    }
  }
}
</script>

<style scoped>

</style>
