<template>
  <div style="padding: 10px">
    <div>
      <el-input v-model="search" placeholder="请输入作品名关键字" clearable @keyup.enter="load"
                style="margin: 0 12px;width: 20%"/>
      <el-button type="primary" v-if="search !== ''" @click="load">查询</el-button>
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
      <el-table-column label="操作" width="100px">
        <template #default="scope">
          <el-button size="small" @click="downloadFile(scope.row)">查看作品</el-button>
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
  </div>
</template>

<script>
import request from "../utils/request";

export default {
  name: 'Score',
  components: {},
  data() {
    return {
      search: '',
      currentPage: 1,
      pageSize: 3,
      total: 0,
      form: {},
      tableData: [],
    }
  },
  methods: {
    downloadFile(row) {
      console.log(row.url)
      window.location.href = row.url
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
