<template>
  <div style="display:flex;" class="center-x">
    <div class="grid-content bg-1 header" style="width: 199px; color: #409EFF">系统管理</div>
    <div class="grid-content bg-2 header" style="flex: 1">大学生竞赛网站</div>
    <div class="grid-content bg-3" style="width: 200px;">
      <el-avatar :src="user.avatar" class="avatar"></el-avatar>
      <el-dropdown class="header">
        <span>
          {{ user.nickname }}
          <el-icon class="el-icon--right">
            <arrow-down/>
          </el-icon>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="$router.push('/person')">个人信息</el-dropdown-item>
            <el-dropdown-item @click="$router.push('/password')">修改密码</el-dropdown-item>
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
  props: ['user'],
  components: {
    ArrowDown
  },
  data() {
    return {
      user: {}
    }
  },
  created() {
    let userStr = sessionStorage.getItem("user") || "{}"
    this.user = JSON.parse(userStr)
  }
}
</script>

<style scoped>
.bg-1 {
  background: #545c64;
}

.bg-2 {
  background: #d3dce6;
}

.bg-3 {
  background: #e5e9f2;
}

.grid-content {
  /*border-radius: 6px;*/
  height: 50px;
}

.header {
  font-size: 20px;
  padding: 11.8px 0;
}

.avatar {
  --el-avatar-size: 50px;
  margin-right: 20px;
}

</style>