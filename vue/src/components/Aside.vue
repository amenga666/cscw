<template>
  <div>
    <el-menu
        default-active="2"
        style="width: 192px;height: calc(100vh - 50px)"
    >
      <el-sub-menu index="1">
        <template #title>
          <el-icon><user /></el-icon>
          <span>用户管理</span>
        </template>
          <el-menu-item index="1-1">item one</el-menu-item>
          <el-menu-item index="1-2">item one</el-menu-item>
          <el-menu-item index="1-3">item three</el-menu-item>
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

</style>