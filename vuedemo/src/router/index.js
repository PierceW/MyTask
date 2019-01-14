import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import Interaction from '@/components/Interaction'
import TodoList from '@/components/TodoList'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'HelloWorld',
      component: HelloWorld
    },
    {
      path: '/interaction',
      name: 'Interaction',
      component: Interaction
    },
    {
      path: '/todoList',
      name: 'todoList',
      component: TodoList
    }
  ]
})
