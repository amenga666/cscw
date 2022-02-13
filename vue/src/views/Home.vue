<template>
  <div style="padding: 10px">
    <div>
      <el-button type="primary">新增</el-button>
      <el-button type="primary">导入</el-button>
      <el-button type="primary">导出</el-button>
      <el-input v-model="search" placeholder="请输入关键字" style="padding: 0 12px;width: 20%"/>
      <el-button type="primary">查询</el-button>
    </div>
    <el-table :data="tableData" style="width: 100%">
      <el-table-column fixed prop="date" label="Date"/>
      <el-table-column prop="name" label="Name"/>
      <el-table-column prop="state" label="State"/>
      <el-table-column prop="city" label="City"/>
      <el-table-column prop="address" label="Address"/>
      <el-table-column prop="zip" label="Zip"/>
      <el-table-column fixed="right" label="Operations">
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
    handleEdit() {

    },
    handDelete() {

    },
    handleSizeChange() {

    },
    handleCurrentChange() {

    }
  }
}
</script>
