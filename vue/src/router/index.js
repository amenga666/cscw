import { createRouter, createWebHistory } from 'vue-router'
import Layout from "../components/Layout";

const routes = [
  {
    path: '/',
    name: 'Layout',
    component: Layout,
    redirect: "/home",   // 路由重定向
    children: [   // 数组，存放嵌套路由
      {
        path: '/home',
        name: 'Home',
        component: () => import("@/views/Home")
      },
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
      },
      {
        path: '/userenroll',
        name: 'UserEnroll',
        component: () => import("@/views/UserEnroll")
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
  {
    path: '/test',
    name: 'Test',
    component: () => import("@/components/Test")
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
