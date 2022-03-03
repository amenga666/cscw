<template>
  <div>
    <!--    default-active="" 默认高亮，引号中写index值，使用$route.path控制高亮菜单-->
    <el-menu class="menu" router :default-active="$route.path">
      <el-menu-item index="/home">
        <el-icon>
          <house/>
        </el-icon>
        主页
      </el-menu-item>
      <el-menu-item index="/notice">
        <el-icon>
          <notification/>
        </el-icon>
        公告
      </el-menu-item>
      <el-menu-item index="/enroll">
        <el-icon>
          <edit/>
        </el-icon>
        报名管理
      </el-menu-item>
      <el-menu-item index="/user" v-if="user.role === 1">
        <el-icon>
          <user/>
        </el-icon>
        用户管理
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script>
import {
  User, House, Edit, Notification
} from '@element-plus/icons-vue'
import request from "../utils/request";

export default {
  name: "Aside",
  components: {
    User, House, Edit, Notification
  },
  data() {
    return {
      user: {}
    }
  },
  created() {   // 获取用户JSON
    let userStr = sessionStorage.getItem("user") || "{}"
    this.user = JSON.parse(userStr)   // 转换为user对象user: {}

    // 请求服务端，确认当前登录用户的合法信息，防止通过修改缓存来更改用户权限
    request.get("/user/" + this.user.userId).then(res => {
      if (res.code === 0) {
        this.user = res.data
      }
    })
  }
}
</script>

<style scoped>
.menu {
  width: 200px;
  height: calc(100vh - 50px);
}
</style>

<!--导航菜单存入数据库-->
<!--<div  v-for="m in user.permissions" :key="m.id">-->
<!--<el-menu-item :index="m.path" v-if="m.name !== 'Person' && m.name !== 'Password' ">-->
<!--  <i :class="m.icon"></i>  {{ m.comment }}-->
<!--</el-menu-item>-->
<!--</div>-->