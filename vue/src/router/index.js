import { createRouter, createWebHistory } from 'vue-router'
import Layout from "../layout/Layout";

const routes = [
  {
    path: '/',
    name: 'Layout',
    component: Layout,
    redirect: "/notice", // 路由重定向
    children: [ // 数组，存放嵌套路由
      {
        path: '/notice',
        name: 'Notice',
        component: () => import("@/views/Notice")
      },
      {
        path: '/enroll',
        name: 'Enroll',
        component: () => import("@/views/Enroll")
      },
      {
        path: '/user',
        name: 'User',
        component: () => import("@/views/User")
      },
      {
        path: '/person',
        name: 'Person',
        component: () => import("@/views/Person")
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
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
