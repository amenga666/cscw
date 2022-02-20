<template>
  <div>
    <el-menu default-active="2" class="menu">
      <el-sub-menu index="1">
        <template #title>
          <el-icon><user /></el-icon>
          <span>主页</span>
        </template>
          <el-menu-item index="1-1">item one</el-menu-item>
      </el-sub-menu>
      <el-sub-menu index="2">
        <template #title>
          <el-icon><user /></el-icon>
          <span>报名活动</span>
        </template>
        <el-menu-item index="2-1">item one</el-menu-item>
      </el-sub-menu>
      <el-sub-menu index="3">
        <template #title>
          <el-icon><user /></el-icon>
          <span>报名管理</span>
        </template>
        <el-menu-item index="3-1">item one</el-menu-item>
      </el-sub-menu>
      <el-sub-menu index="4">
        <template #title>
          <el-icon><user /></el-icon>
          <span>主页管理</span>
        </template>
        <el-menu-item index="4-1">item one</el-menu-item>
      </el-sub-menu>
      <el-sub-menu index="5">
        <template #title>
          <el-icon><user /></el-icon>
          <span>用户管理</span>
        </template>
        <el-menu-item index="5-1">item one</el-menu-item>
      </el-sub-menu>
      <el-sub-menu index="6">
        <template #title>
          <el-icon><user /></el-icon>
          <span>活动管理</span>
        </template>
        <el-menu-item index="6-1">item one</el-menu-item>
      </el-sub-menu>
      <el-sub-menu index="7">
        <template #title>
          <el-icon><user /></el-icon>
          <span>权限管理</span>
        </template>
        <el-menu-item index="7-1">item one</el-menu-item>
      </el-sub-menu>
      <el-sub-menu index="8">
        <template #title>
          <el-icon><user /></el-icon>
          <span>导航管理</span>
        </template>
        <el-menu-item index="8-1">item one</el-menu-item>
      </el-sub-menu>
    </el-menu>
  </div>
</template>

<script>
import {
  User,
} from '@element-plus/icons-vue'

export default {
  name: "Aside",
  components: {
    User
  }
}
</script>

<style scoped>
.menu {
  width: 200px;
  height: calc(100vh - 50px);
}
</style>