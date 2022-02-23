import { createRouter, createWebHistory } from 'vue-router'
import Layout from "../layout/Layout";

const routes = [
  {
    path: '/',
    name: 'Layout',
    component: Layout,
    redirect: "/home", // 路由重定向，访问/根节点会重定向到/home
    children: [ // 数组，存放嵌套路由
      {
        path: '/home',
        name: 'Home',
        component: () => import("@/views/Home")
      }
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import("@/views/Login")
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import("@/views/Register")
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
