import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import life from '@/components/Life'
import Resource from '@/components/Resource'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'HelloWorld',
      component: HelloWorld
    },
    {
      path: '/life',
      name: 'life',
      component: life
    },
    {
      path: '/res',
      name: 'resource',
      component: Resource
    }

  ]
})
