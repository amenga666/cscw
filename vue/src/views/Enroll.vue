<template>
  <div style="padding: 10px">
    <div>
      <el-button type="primary" @click="add">新增报名</el-button>
      <el-input v-model="search" placeholder="请输入作品名关键字" clearable @keyup.enter="load"
                style="margin: 0 12px;width: 20%"/>
      <el-button type="primary" @click="load">查询</el-button>
    </div>
    <!--    表格-->
    <el-table :data="tableData" style="width: 100%; margin: 10px 0" border>
      <el-table-column prop="enrollId" label="报名ID" width="100px" sortable/>
      <el-table-column prop="entriesname" label="作品名"/>
      <el-table-column prop="competitor" label="参赛者"/>
      <el-table-column prop="enrolltime" label="报名时间"/>
      <!--      显示是否评分-->
      <el-table-column label="得分" width="100px">
        <template #default="scope">
          <span v-if="scope.row.score === null">未评分</span>
          <span v-if="scope.row.score !== null">
            {{ scope.row.score }}
          </span>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="300px">
        <!--        编辑、删除-->
        <!--        scope 暂时没找到使用文档，但是不加scope编辑对话框不显示-->
        <template #default="scope">
          <el-button size="small" @click="downloadFile(scope.row)">查看作品</el-button>
          <el-button type="primary" size="small" @click="handleEdit(scope.row)" v-if="user.role === 1">编辑</el-button>
          <el-popconfirm title="确认删除？" @confirm="handDelete(scope.row.enrollId)">
            <template #reference>
              <el-button type="danger" size="small" v-if="user.role === 1">删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <!--    分页-->
    <el-pagination
        style="margin: 10px"
        v-model:currentPage="currentPage"
        v-model:page-size="pageSize"
        :page-sizes="[2, 3, 4, 5]"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
    >
    </el-pagination>
    <!--    新增对话框-->
    <el-dialog v-model="dialogVisible" title="填写报名信息" width="20%">
      <!--      表单-->
      <el-form ref="form" :model="form" label-width="60px" :rules="rules">
        <el-form-item label="作品名">
          <el-input v-model="form.entriesname"></el-input>
        </el-form-item>
        <el-form-item label="作品">
          <el-upload ref="upload" action="http://localhost:9090/files/upload" :on-success="filesUploadSuccess">
            <el-button type="primary">点击上传</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="得分" prop="score">
          <el-input v-model.number="form.score"></el-input>
        </el-form-item>
      </el-form>
      <!--      对话框按钮-->
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="save">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import request from "../utils/request";
import {ElMessage} from "element-plus";

export default {
  name: 'Enroll',
  components: {},
  data() {
    const checkScore = (rule, value, callback) => {
      if (value < 0 || value > 100) {
        callback(new Error('得分范围0到100'))
      } else {
        callback()
      }
    };
    return {
      user: {},
      search: '',
      currentPage: 1,
      pageSize: 3,
      total: 0,
      dialogVisible: false,
      form: {},
      tableData: [],
      rules: {
        score: [
          {required: true, message: '得分不能为空，默认是0'},
          {type: 'number', message: '得分必须为整数'},
          {validator: checkScore},
        ]
      }
    }
  },
  created() {   // 页面加载时调用load方法，获取数据显示在表格中 获取用户JSON
    this.load()
    let userStr = sessionStorage.getItem("user") || "{}"
    this.user = JSON.parse(userStr)   // 转换为user对象user: {}

    // 请求服务端，确认当前登录用户的合法信息，防止通过修改缓存来更改用户权限
    request.get("/user/" + this.user.userId).then(res => {
      if (res.code === 0) {
        this.user = res.data
      }
    })
  },
  methods: {
    downloadFile(row) {
      console.log(row.url)
      window.location.href = row.url
    },
    filesUploadSuccess(res) {
      console.log(res)
      this.form.url = res.data   // 将上传成功返回的url赋值给表单
    },
    add() {
      this.dialogVisible = true
      this.form = {}
      this.$nextTick(() => {   // 在点击按钮前upload不存在，需要使用处理未来元素
        this.$refs["upload"].clearFiles()   // 清除历史文件列表
      })
    },
    save() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          console.log('表单验证成功')
          if (this.form.enrollId) {
            request.put("/enroll", this.form).then(res => { // 改
              console.log(res)
              if (res.code === 0) {   // 判断操作是否成功
                ElMessage({
                  type: "success",
                  message: "编辑成功",
                  center: true
                })
              } else {
                ElMessage({
                  type: "error",
                  message: res.msg,
                  center: true
                })
              }
              this.load()   // 更新表格数据
              this.dialogVisible = false
            })
          } else {
            // 设置发布者
            let userStr = sessionStorage.getItem("user") || "{}"
            let user = JSON.parse(userStr)   // 转换为user对象user: {}
            this.form.competitor = user.nickname

            request.post("/enroll", this.form).then(res => { //增
              console.log(res)
              if (res.code === 0) {   // 判断操作是否成功
                ElMessage({
                  type: "success",
                  message: "新增成功",
                  center: true
                })
              } else {
                ElMessage({
                  type: "error",
                  message: res.msg,
                  center: true
                })
              }
              this.load()   // 更新表格数据
              this.dialogVisible = false
            })
          }
          // this.load()   // 更新表格数据    这两行代码如果放在这里的话第二次执行才会刷新表格，原因未知
          // this.dialogVisible = false
        } else {
          console.log('表单验证失败')
          return false
        }
      })
    },
    load() {
      request.get("/enroll", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))   // 深拷贝form，使form成为一个独立的对象，避免输入数据后点击取消影响原数据，ES5
      this.dialogVisible = true
      this.$nextTick(() => {
        this.$refs["upload"].clearFiles()   // 清除历史文件列表
      })
    },
    handDelete(enrollId) {
      console.log(enrollId)
      request.delete("/enroll/" + enrollId).then(res => {
        if (res.code === 0) {   // 判断操作是否成功
          ElMessage({
            type: "success",
            message: "删除成功",
            center: true
          })
        } else {
          ElMessage({
            type: "error",
            message: res.msg,
            center: true
          })
        }
        this.load()   // 更新表格数据
      })
    },
    handleSizeChange(pageSize) {   // 改变每页个数
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {   // 改变当前页码
      this.currentPage = pageNum
      this.load()
    },
  }
}
</script>
