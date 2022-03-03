<template>
  <div class="login image" @keyup.enter="login">
    <!--    <el-header class="center-x">大学生竞赛网站</el-header>-->
    <div class="login-box">
      <div class="center-x login-font" style="padding: 30px;">欢迎登录</div>
      <el-form ref="form" :model="form" :rules="rules">
        <el-form-item prop="userName">
          <el-input v-model="form.userName" placeholder="请输入用户名">
            <template #prefix>
              <el-icon class="el-input__icon">
                <user/>
              </el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="form.password" placeholder="请输入密码" show-password>
            <template #prefix>
              <el-icon class="el-input__icon">
                <lock/>
              </el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-button style="width: 100%" type="primary" @click="login">登 录</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="text" @click="$router.push('/register')">前往注册 >></el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import {User, Lock, Key} from "@element-plus/icons-vue";
import request from "../utils/request";
import {ElMessage} from "element-plus";

export default {
  name: "Login",
  components: {User, Lock, Key},
  data() {
    return {
      form: {},
      rules: {
        userName: [   // 与表单中的prop对应
          {required: true, message: '请输入用户名', trigger: 'blur'},
          // {min: 3, max: 5, message: 'Length should be 3 to 5', trigger: 'blur'},
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
        ],
      }
    }
  },
  methods: {
    login() {
      let formEl = this.$refs.form;
      // 将表单中的ref赋值给formEl，此处也可直接使用this.$refs.form，element plus的表单中使用的是formEl，不使用this.$refs.form的话需要将js改为ts，即可直接使用element plus模板
      if (!formEl) return
      formEl.validate((valid) => {
        if (valid) {
          console.log('输入验证成功')
          request.post("/user/login", this.form).then(res => {
            if (res.code === 0) {   // 判断操作是否成功
              ElMessage({
                type: "success",
                message: "登录成功",
                center: true
              })
              sessionStorage.setItem("user", JSON.stringify(res.data))   // 缓存用户信息
              this.$router.push("/")   // 登录成功后跳转到主页
            } else {
              ElMessage({
                type: "error",
                message: res.msg,
                center: true
              })
            }
          })
        } else {
          console.log('输入验证失败')
          return false
        }
      })
    }
  }
}

</script>

<!--prefix-icon="lock"-->