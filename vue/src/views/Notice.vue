<template>
  <div style="padding: 10px">
    <div>
      <el-button type="primary" @click="add" v-if="user.role === 1">新增</el-button>
      <el-input v-model="search" placeholder="请输入公告标题关键字" clearable @keyup.enter="load"
                style="margin: 0 12px;width: 20%"/>
      <el-button type="primary" @click="load">查询</el-button>
    </div>
    <!--    表格-->
    <el-table :data="tableData" style="width: 100%; margin: 10px 0" border>
      <el-table-column prop="noticeId" label="公告ID" sortable/>
      <el-table-column prop="title" label="标题"/>
      <el-table-column prop="publisher" label="发布者"/>
      <el-table-column prop="time" label="时间"/>
      <!--      <el-table-column prop="content" label="内容"/>-->
      <!--      设置操作列宽度为200px，避免三个按钮位置错乱-->
      <el-table-column label="操作" width="200px">
        <!--        编辑、删除-->
        <!--        scope 暂时没找到使用文档，但是不加scope编辑对话框不显示-->
        <template #default="scope">
          <el-button type="primary" size="small" @click="details(scope.row)">详情</el-button>
          <el-button type="primary" size="small" @click="handleEdit(scope.row)" v-if="user.role === 1">编辑</el-button>
          <el-popconfirm title="确认删除？" @confirm="handDelete(scope.row.noticeId)">
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
        :page-sizes="[5, 10, 15, 20]"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
    >
    </el-pagination>
    <!--    新增对话框-->
    <el-dialog v-model="dialogVisible" title="公告" width="60%">
      <!--      表单-->
      <el-form ref="form" :model="form">
        <el-form-item label="标题">
          <el-input v-model="form.title"></el-input>
        </el-form-item>
        <!--        <el-form-item label="发布者">-->
        <!--          <el-input v-model="form.publisher" style="width: 66%"></el-input>-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="时间">-->
        <!--          <el-input v-model="form.time" style="width: 66%"></el-input>-->
        <!--        </el-form-item>-->
        <el-form-item>
          <div id="div1"></div>
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

    <!--    公告详情-->
    <el-dialog v-model="vis" title="公告详情" width="60%">
      <el-card>
        <div v-html="detail.content" style="min-height: 200px"></div>
      </el-card>
    </el-dialog>
  </div>
</template>

<script>
import request from "../utils/request";
import {ElMessage} from "element-plus";
import E from 'wangeditor'

let editor;

export default {
  name: 'Notice',
  components: {},
  data() {
    return {
      user: {},
      search: '',
      currentPage: 1,
      pageSize: 10,
      total: 0,
      dialogVisible: false,
      form: {},
      tableData: [],
      detail: {},
      vis: false, // 控制公告详情弹窗
    }
  },
  created() { // 页面加载时调用load方法，获取数据显示在表格中 获取用户JSON
    this.load()
    let userStr = sessionStorage.getItem("user") || "{}"
    this.user = JSON.parse(userStr) // 转换为user对象user: {}

    // 请求服务端，确认当前登录用户的合法信息，防止通过修改缓存来更改用户权限
    request.get("/user/" + this.user.userId).then(res => {
      if (res.code === 0) {
        this.user = res.data
      }
    })
  },
  methods: {
    add() {
      this.dialogVisible = true
      this.form = {}
      this.$nextTick(() => { // 处理未来元素
        editor = new E('#div1')

        // 配置 server 接口地址 实现上传本地图片功能
        editor.config.uploadImgServer = '/upload-img'

        editor.create()
      })
    },
    save() {
      // console.log(editor.txt.html()) // 打印编辑器中的值
      this.form.content = editor.txt.html() // 获取编辑器中的值，给实体赋值

      if (this.form.noticeId) {
        request.put("/notice", this.form).then(res => { // 改
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
        // 设置发布者
        let userStr = sessionStorage.getItem("user") || "{}"
        let user = JSON.parse(userStr) // 转换为user对象user: {}
        this.form.publisher = user.nickname

        request.post("/notice", this.form).then(res => { //增
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
      request.get("/notice", {
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
    details(row) { // 公告详情
      this.detail = row
      this.vis = true
    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row)) // 深拷贝form，使form成为一个独立的对象，避免输入数据后点击取消影响原数据，ES5
      this.dialogVisible = true

      // 编辑富文本
      this.$nextTick(() => { // 处理未来元素
        editor = new E('#div1')
        editor.create()
        editor.txt.html(row.content)
      })
    },
    handDelete(noticeId) {
      console.log(noticeId)
      request.delete("/notice/" + noticeId).then(res => {
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
