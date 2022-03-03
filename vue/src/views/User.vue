<template>
  <div style="padding: 10px">
    <!--    新增、查询、导入、导出-->
    <div>
      <el-button type="primary" @click="add">新增</el-button>
      <!--      <el-button type="primary">导入</el-button>-->
      <!--      <el-button type="primary">导出</el-button>-->
      <el-input v-model="search" placeholder="请输入昵称关键字" clearable @keyup.enter="load"
                style="margin: 0 12px;width: 20%"/>
      <el-button type="primary" @click="load">查询</el-button>
    </div>
    <!--    表格-->
    <el-table :data="tableData" style="width: 100%; margin: 10px 0" border>
      <el-table-column prop="userId" label="用户ID" sortable/>
      <el-table-column prop="userName" label="用户名"/>
      <el-table-column prop="nickname" label="昵称"/>
      <el-table-column prop="sex" label="性别"/>
      <el-table-column prop="age" label="年龄" sortable/>
      <el-table-column prop="address" label="住址"/>
      <el-table-column label="头像">
        <template #default="scope">
          <el-image
              style="width: 100px; height: 100px"
              :src="scope.row.avatar"
              :preview-src-list="[scope.row.avatar]"
              fit="cover"
          ></el-image>
        </template>
      </el-table-column>
      <el-table-column label="权限">
        <template #default="scope">
          <span v-if="scope.row.role === 1">管理员</span>
          <span v-if="scope.row.role === 2">普通用户</span>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <!--        编辑、删除-->

        <!--        scope 暂时没找到使用文档，但是不加scope编辑对话框不显示-->
        <template #default="scope">
          <el-button type="primary" size="small" @click="handleEdit(scope.row)">编辑</el-button>
          <el-popconfirm title="确认删除？" @confirm="handDelete(scope.row.userId)">
            <template #reference>
              <el-button type="danger" size="small">删除</el-button>
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
        :page-sizes="[5, 10, 15, 20]"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
    >
    </el-pagination>
    <!--    新增对话框-->
    <el-dialog
        v-model="dialogVisible"
        title="Tips"
        width="30%"
    >
      <!--      表单-->
      <el-form ref="form" :model="form" label-width="120px" @keyup.enter="save">
        <el-form-item label="用户名">
          <el-input v-model="form.userName" class="form-input"></el-input>
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
  name: 'User',
  components: {},
  data() {
    return {
      search: '',
      currentPage: 1,
      pageSize: 10,
      total: 0,
      dialogVisible: false,
      form: {},
      tableData: [],
    }
  },
  created() { // 页面加载时调用load方法，获取数据显示在表格中
    this.load()
  },
  methods: {
    add() {
      this.dialogVisible = true
      this.form = {}
    },
    save() {
      if (this.form.userId) {
        request.put("/user", this.form).then(res => { // 改
          console.log(res)
          if (res.code === 0) { // 判断操作是否成功
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
          this.load() // 更新表格数据
          this.dialogVisible = false
        })
      } else {
        request.post("/user", this.form).then(res => { //增
          console.log(res)
          if (res.code === 0) { // 判断操作是否成功
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
          this.load() // 更新表格数据
          this.dialogVisible = false
        })
      }
      // this.load() // 更新表格数据    这两行代码如果放在这里的话第二次执行才会刷新表格，原因未知
      // this.dialogVisible = false
    },
    load() {
      request.get("/user", {
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
      this.form = JSON.parse(JSON.stringify(row)) // 深拷贝form，使form成为一个独立的对象，避免输入数据后点击取消影响原数据，ES5
      this.dialogVisible = true
    },
    handDelete(userId) {
      console.log(userId)
      request.delete("/user/" + userId).then(res => {
        if (res.code === 0) { // 判断操作是否成功
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
        this.load() // 更新表格数据
      })
    },
    handleSizeChange(pageSize) { // 改变每页个数
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) { // 改变当前页码
      this.currentPage = pageNum
      this.load()
    },
  }
}
</script>

<style scoped>
.form-input {
  width: 66%;
}
</style>
