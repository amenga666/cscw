<template>
  <div style="display:flex;" class="center-x">
    <div class="grid-content bg-purple header" style="width: 200px; color: #409EFF">系统管理</div>
    <div class="grid-content bg-purple-light header" style="flex: 1">大学生竞赛网站</div>
    <div class="grid-content bg-purple header" style="width: 150px;">
      <el-dropdown>
        <span class="header">
          用户名
          <el-icon class="el-icon--right">
            <arrow-down />
          </el-icon>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item>个人中心</el-dropdown-item>
            <el-dropdown-item>账户管理</el-dropdown-item>
            <el-dropdown-item divided @click="$router.push('/login')">退出系统</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import {ArrowDown} from "@element-plus/icons-vue";

export default {
  name: "Header",
  components: {
    ArrowDown
  }
}
</script>

<style scoped>
.bg-purple {
  background: #d3dce6;
}
.bg-purple-light {
  background: #e5e9f2;
}
.grid-content {
  /*border-radius: 6px;*/
  min-height: 50px;
}
.header {
  font-size: 20px;
  padding: 11.8px 0;
}
</style>