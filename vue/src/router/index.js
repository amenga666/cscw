import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Layout from "../layout/Layout";
import home from "../views/Home";

const routes = [
  {
    path: '/',
    name: 'Layout',
    component: Layout,
    redirect: "/Home",
    children: [
      {
        path: 'home',
        name: 'Home',
        component: () => import("@.views/Home")
      }
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import("@/views/Login")
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
