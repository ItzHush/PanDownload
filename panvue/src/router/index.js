import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from "@/views/HomeView.vue";
import Welcome from "@/views/Welcome.vue";
import LoginPage from "@/components/LoginPage.vue";
import RegisterPage from "@/components/RegisterPage.vue";
import Albums from "@/views/Albums.vue";
import Collectes from "@/views/Collectes.vue";
import Files from "@/views/Files.vue";
import UserInfo from "@/components/UserInfo.vue";
import About from "@/components/About.vue";
Vue.use(VueRouter)

import { createRouter, createWebHashHistory } from 'vue-router'

const routes = [
  {
    path:'/',
    name:'welcome',
    component: Welcome,
    children:[{
      path: '/',
      name:'welcome-login',
      component:LoginPage
    },
      {
        path: '/register',
        name: 'welcome-register',
        component: RegisterPage
      }
    ]
  },
  {
    path: '/index',
    name:'indexView',
    component: Home,
    redirect: '/files',
    children: [
      { path: '/files', component: Files},
      { path: '/albums', component: Albums },
      { path: '/collects', component: Collectes },
      { path: '/userinfo', component: UserInfo},
      { path: '/about', component: About},
    ]
  }
]

const router = new VueRouter({
  mode:"history",
  base: '/',
  routes
})

router.beforeEach((to, from, next) => {
  if (to.path != '/' && to.path != '/register' && sessionStorage.getItem('username')==null
  && localStorage.getItem('User')==null)  {
    router.replace('/')
  }
  next()
})


export default router
