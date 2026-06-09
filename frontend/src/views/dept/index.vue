<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { queryAllApi, addDeptApi, queryInfoApi, updateDeptApi, deleteDeptApi } from '@/api/dept'



// 部門列表
const deptList = ref([])

// 查詢全部部門
const queryAll = async () => {
  const result = await queryAllApi()
  deptList.value = result.data
}

// 元件掛載後載入資料
onMounted(() => {
  queryAll()
})

const formTitle = ref('')

const showDialog = ref(false)

const deptForm = ref({name: ''})
//表單驗證規則
const formRules = ref({
  name:[
    {required: true, message: '請輸入部門名稱', trigger: 'blur'},
    {min: 2, max: 10, message: '長度在2到10個字符', trigger: 'blur'}
  ]
})

//表單引用
const deptFormRef = ref(null)
//重置表單
const resetForm = () => {
  deptFormRef.value.resetFields()
}
//新增部門
const add = () => {
  formTitle.value = '新增部門'
  showDialog.value = true
  deptForm.value = {name: ''}
}
//提交表單
const save = async() => {
  const valid = await deptFormRef.value.validate()
  if (!valid) return

  let result = null;
  if(deptForm.value.id){
    result = await updateDeptApi(deptForm.value)//修改
  }else {
    result = await addDeptApi(deptForm.value)//新增
  }
  //提交表單
  if (result.code) {
    ElMessage.success('操作成功')
    showDialog.value = false
    resetForm()
    queryAll()
    }else {
      ElMessage.error(result.msg)
    }
  }


//編輯部門-根據id查詢回顯數據
const handleEdit = async (id) => {
  console.log(`edit item with ID ${id}`)
  formTitle.value = '修改部門'
  showDialog.value = true
  deptForm.value = {name: ''}
  const result = await queryInfoApi(id)
  if (result.code) {
    deptForm.value = result.data
  }
}

//刪除部門-根據id刪除部門
const handleDelete = async (id) => {
  console.log(`Delete item with ID ${id}`);
  //刪除部門時，需彈出一個確認框，在進行確認
  ElMessageBox.confirm('此操作將永久刪除該部門，是否繼續?' , '提示' , {
    confirmButtonText: '確認',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    //刪除部門
    const result = await deleteDeptApi(id)
    if(result.code){
      ElMessage.success('刪除成功')
      queryAll()
    }
  })
}
</script>

<template>
  <h1>部門管理</h1>

  <div class="container">
    <el-button type="primary" @click="add()">
      + 新增部門
    </el-button>
  </div>

  <div class="container">
    <el-table :data="deptList" border style="width: 100%">
      
      <el-table-column
        type="index"
        label="序號"
        width="100"
        align="center"
      />

      <el-table-column
        prop="name"
        label="部門名稱"
        width="260"
        align="center"
      />

      <el-table-column
        prop="updateTime"
        label="最後操作時間"
        width="400"
        align="center"
      />

      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button type="primary" @click="handleEdit(scope.row.id)" size="small">
            <el-icon><Edit /></el-icon>
            編輯
          </el-button>

          <el-button type="danger" @click="handleDelete(scope.row.id)" size="small">
            <el-icon><Delete /></el-icon>
            刪除
          </el-button>
        </template>
      </el-table-column>

    </el-table>

     <el-dialog v-model="showDialog" :title="formTitle" width="500" @close="resetForm">
    <el-form :model="deptForm" :rules="formRules" ref="deptFormRef" >
      <el-form-item label="部門名稱" prop="name">
        <el-input v-model="deptForm.name" autocomplete="off" />
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="showDialog = false">取消</el-button>
        <el-button type="primary" @click="save()">
          確認
        </el-button>
      </div>
    </template>
  </el-dialog>
  </div>
</template>

<style scoped>
.container {
  margin: 10px 0;
}
</style>