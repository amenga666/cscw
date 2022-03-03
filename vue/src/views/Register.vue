<template>
  <div class="register image">
    <div class="register-box">
      <div class="center-x register-font" style="padding: 30px;">欢迎注册</div>
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
        <el-form-item prop="confirm">
          <el-input v-model="form.confirm" placeholder="请确认密码" show-password>
            <template #prefix>
              <el-icon class="el-input__icon">
                <lock/>
              </el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-button style="width: 100%" type="primary" @click="register">注 册</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="text" @click="$router.push('/login')">&lt;&lt; 返回登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import {User, Lock} from "@element-plus/icons-vue";
import request from "../utils/request";
import {ElMessage} from "element-plus";

export default {
  name: "Register",
  components: {User, Lock},
  data() {
    return {
      form: {},
      rules: {
        userName: [   // 与表单中的prop对应
          {required: true, message: '请输入用户名', trigger: 'blur'},
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
        ],
        confirm: [
          {required: true, message: '请确认密码', trigger: 'blur'},
        ],
      }
    }
  },
  methods: {
    register() {
      if (this.form.password !== this.form.confirm) {
        ElMessage({
          type: "error",
          message: "两次输入密码不一致！",
          center: true
        })
        return
      }
      let formEl = this.$refs.form;
      if (!formEl) return
      formEl.validate((valid) => {
        if (valid) {
          console.log('输入验证成功')
          request.post("/user/register", this.form).then(res => {
            if (res.code === 0) {   // 判断操作是否成功
              ElMessage({
                type: "success",
                message: "注册成功",
                center: true
              })
              this.$router.push("/login")   // 登录成功后跳转到登录页面
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