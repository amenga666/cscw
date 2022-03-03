<template>
  <div>
    <el-card style="width: 30%; margin: 10px; text-align: center">修改个人信息
      <el-upload
          action="http://localhost:9090/files/upload"
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          list-type="picture-card"
          style="margin: 20px"
      >
        <img v-if="form.avatar" :src="form.avatar" class="avatar" />
        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-upload>
      <el-form ref="form" :model="form" label-width="60px">
        <el-form-item label="用户名">
          <el-input v-model="form.userName" class="form-input" disabled></el-input>
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="form.nickname" class="form-input"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <div style="margin-left: 40px;">
            <el-radio v-model="form.sex" label="男" size="large">男</el-radio>
            <el-radio v-model="form.sex" label="女" size="large">女</el-radio>
          </div>
        </el-form-item>
        <el-form-item label="年龄">
          <el-input v-model="form.age" class="form-input"></el-input>
        </el-form-item>
        <el-form-item label="住址">
          <el-input v-model="form.address" class="form-input"></el-input>
        </el-form-item>
      </el-form>
      <el-button type="primary" @click="update">保存修改</el-button>
      <el-button @click="$router.push('/')">返回主页</el-button>
    </el-card>
  </div>
</template>

<script>
import request from "../utils/request";

export default {
  data() {
    return {
      form: {},
    }
  },
  created() {   // 获取用户信息
    let str = sessionStorage.getItem("user") || "{}"
    this.form = JSON.parse(str)
  },
  methods: {
    handleAvatarSuccess(res) {
      this.form.avatar = res.data
      this.$message.success("上传成功")
    },
    update() {
      request.put("/user", this.form).then(res => {
        console.log(res)
        if (res.code === 0) {
          this.$message({
            type: "success",
            message: "更新成功"
          })
          sessionStorage.setItem("user", JSON.stringify(this.form))
          // 触发Layout更新用户信息
          this.$emit("userInfo")
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
      })
    }
  }
}

</script>

<style scoped>
.avatar {
  width: 148px;
  height: 148px;
  display: block;
}
</style>