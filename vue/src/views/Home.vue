<template>
  <div style="padding: 10px">
<!--    新增、查询、导入、导出-->
    <div>
      <el-button type="primary" @click="add">新增</el-button>
      <el-button type="primary">导入</el-button>
      <el-button type="primary">导出</el-button>
      <el-input v-model="search" placeholder="请输入关键字" style="padding: 0 12px;width: 20%"/>
      <el-button type="primary">查询</el-button>
    </div>
<!--    表格-->
    <el-table :data="tableData" style="width: 100%">
      <el-table-column fixed prop="date" label="Date"/>
      <el-table-column prop="name" label="Name"/>
      <el-table-column prop="state" label="State"/>
      <el-table-column prop="city" label="City"/>
      <el-table-column prop="address" label="Address"/>
      <el-table-column prop="zip" label="Zip"/>
      <el-table-column fixed="right" label="Operations">
<!--        编辑、删除-->
        <template #default>
          <el-button type="primary" size="small" @click="handleEdit">编辑</el-button>
          <el-popconfirm title="确认删除？">
            <template #reference>
              <el-button type="danger" size="small" @click="handDelete">删除</el-button>
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
      <el-form ref="form" :model="form" label-width="120px">
        <el-form-item label="用户名">
          <el-input v-model="form.username" style="width: 66%"></el-input>
        </el-form-item>
      </el-form>
<!--      对话框按钮-->
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="dialogVisible = false">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>


export default {
  name: 'Home',
  components: {},
  data() {
    return {
      search: '',
      currentPage: '',
      pageSize: '',
      total: 10,
      dialogVisible: false,
      form: {},
      tableData: [
        {
          date: '2016-05-03',
          name: 'Tom',
          state: 'California',
          city: 'Los Angeles',
          address: 'No. 189, Grove St, Los Angeles',
          zip: 'CA 90036',
          tag: 'Home',
        },
      ],
    }
  },
  methods: {
    add() {
      this.dialogVisible = true
      this.form = {}
    },
    handleEdit() {

    },
    handDelete() {

    },
    handleSizeChange() {

    },
    handleCurrentChange() {

    },
  }
}
</script>
