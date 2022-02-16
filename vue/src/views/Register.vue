<template>
  <div class="register-box">
    <div class="center-x register-font" style="padding: 30px;">欢迎注册</div>
    <el-form ref="form" :model="form">
      <el-form-item>
        <el-input v-model="username" placeholder="请输入用户名">
          <template #prefix>
            <el-icon class="el-input__icon"><user /></el-icon>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="password" placeholder="请输入密码" show-password>
          <template #prefix>
            <el-icon class="el-input__icon"><lock /></el-icon>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="confirm" placeholder="请确认密码" show-password>
          <template #prefix>
            <el-icon class="el-input__icon"><lock /></el-icon>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item>
        <el-button style="width: 100%" type="primary" @click="register">注 册</el-button>
      </el-form-item>
      <el-form-item><el-button type="text" @click="$router.push('/login')">&lt;&lt; 返回登录</el-button></el-form-item>
    </el-form>
  </div>
</template>

<script>
import {User, Lock} from "@element-plus/icons-vue";
export default {
  name: "Register",
  components: {User, Lock},
  data() {
    return {
      form: {},
      username: [],
      password: [],
      confirm: [],
    }
  },
  methods: {
    register() {
      print()
    }
  }
}
</script>