<template>
  <div>
    <el-card shadow="hover" class="card">
      填写报名信息
      <el-form ref="form" :model="form" label-width="60px" style="margin: 20px;">
        <el-form-item label="作品名">
          <el-input v-model="form.entriesname"></el-input>
        </el-form-item>
        <el-form-item label="作品">
          <el-upload ref="upload" action="http://localhost:9090/files/upload" :auto-upload="false"
                     :on-success="filesUploadSuccess" limit="1">
            <template #trigger>
              <el-button size="small" type="primary">添加文件</el-button>
            </template>
            <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传文件</el-button>
          </el-upload>
        </el-form-item>
        <el-button type="primary" @click="save">提交</el-button>
        <el-button type="danger" @click="reset">重置</el-button>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import {ElMessage} from "element-plus";
import request from "../utils/request";

export default {
  name: "UserEnroll",
  components: {
    form: {}
  },
  data() {
    return {
      form: {}
    }
  },
  methods: {
    submitUpload() {
      this.$refs.upload.submit()
    },
    reset() {
      this.form = {}
      this.$nextTick(() => {   // 在点击按钮前upload不存在，需要使用处理未来元素
        this.$refs["upload"].clearFiles()   // 清除历史文件列表
      })
    },
    filesUploadSuccess(res) {
      console.log(res)
      this.form.url = res.data   // 将上传成功返回的url赋值给表单
    },
    save() {
      // 设置发布者
      let userStr = sessionStorage.getItem("user") || "{}"
      let user = JSON.parse(userStr)   // 转换为user对象user: {}
      this.form.competitor = user.nickname

      request.post("/enroll", this.form).then(res => {   //增
        console.log(res)
        if (res.code === 0) {   // 判断操作是否成功
          ElMessage({
            type: "success",
            message: "报名成功",
            center: true
          })
        } else {
          ElMessage({
            type: "error",
            message: res.msg,
            center: true
          })
        }
      })

    },
  }
}
</script>

<style scoped>
.card {
  width: 30%;
  margin: 30px;
  text-align: center
}
</style>