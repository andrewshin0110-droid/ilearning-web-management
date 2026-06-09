<script setup>
import { ref , watch, onMounted} from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { queryPageApi } from '@/api/emp'
import { queryAllApi as queryAllDeptApi } from '@/api/dept'
import { addApi, queryInfoApi, updateApi, deleteApi } from '@/api/emp'


const searchEmp = ref({
  name: '',
  gender: '',
  date: [],
  begin: '',
  end: ''
})

//職位列表數據
const jobs = ref([{name: '班主任', value: 1}, {name: '講師', value: 2}, {name: '學工主管', value: 3}, {name: '教研主管', value: 4}, {name: '諮詢師', value: 5}, {name: '其他', value: 6}])
//性別列表數據
const genders = ref([{name: '男', value: 1}, {name: '女', value: 2}])
//部門列表數據
const deptList = ref([])
//員工列表數據
const empList = ref([])
//聲明token
const token = ref('')

//偵聽searchEmp中的data屬性
watch (
  () => searchEmp.value.date,
  (newValue, oldValue) => {
    if(newValue.length == 2){
      searchEmp.value.begin = newValue[0]
      searchEmp.value.end = newValue[1]
    }else{
      searchEmp.value.begin = ''
      searchEmp.value.end = ''
    }
  }
)



onMounted(async () => {
  search()

  //加载所有部门数据
  const result = await queryAllDeptApi();
  if(result.code){
    deptList.value = result.data
  }

  //獲取token
  getToken()
})

//獲取token
const getToken = () => {
  const loginUser = JSON.parse(localStorage.getItem('loginUser'))
  if(loginUser && loginUser.token){
    token.value = loginUser.token
  }
}

const search = async() => {
  console.log('Search:', searchEmp.value)
  const result = await queryPageApi(searchEmp.value.name, searchEmp.value.gender, searchEmp.value.begin, searchEmp.value.end, currentPage.value, pageSize.value)
  if(result.code){
    empList.value = result.data.rows
    total.value = result.data.total
  }
}

const clear = () => {
  searchEmp.value = {
    name: '',
    gender: '',
    date: [],
    begin: '',
    end: ''
  }
  search()
}

//文件上傳
//圖片上傳成功後觸發
const imageUrl = ref('')

const handleAvatarSuccess = (response, uploadFile) => {
  employee.value.image = response.data

  // 本地預覽
  imageUrl.value = URL.createObjectURL(uploadFile.raw)
}
//文件上傳之前觸發
const beforeAvatarUpload = (rawFile) => {
  if (rawFile.type !== 'image/jpeg' && rawFile.type !== 'image/png') {
    ElMessage.error('只支持上傳圖片!')
    return false
  } else if (rawFile.size / 1024 / 1024 > 2) {
    ElMessage.error('只能上傳10MB以內的圖片!')
    return false
  }
  return true
}

//工作經歷
//動態添加工作經歷
const addExprItem = (index) => {
  employee.value.exprList.push({exprDate: [], begin: '', end: '', company: '', job: ''})
}
//動態刪除工作經歷
const delExprItem = (index) => {
  employee.value.exprList.splice(index, 1)
}



//分頁配置
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)

//分頁處理
const handleSizeChange = (val) => {
  search()
}
const handleCurrentChange = (val) => {
  search()
} 


// 控制弹窗
const dialogVisible = ref(false)
const dialogTitle = ref('新增員工')

//新增/修改表单
const employeeFormRef = ref(null)
const employee = ref({
  username: '',
  name: '',
  gender: '',
  phone: '',
  job: '',
  salary: '',
  deptId: '',
  entryDate: '',
  image: '',
  exprList: []
})

//表單較驗規則
const rules = {
  username: [
    {
      required: true,
      message: '請輸入用戶名',
      trigger: 'blur',
    },
    {
      min: 2,
      max: 20,
      message: '用戶名長度需在2-20個字符之間',
      trigger: 'blur',
    },
  ],

  name: [
    {
      required: true,
      message: '請輸入姓名',
      trigger: 'blur',
    },
    {
      min: 2,
      max: 10,
      message: '姓名長度需在2-10個字符之間',
      trigger: 'blur',
    },
  ],

  gender: [
    {
      required: true,
      message: '請選擇性別',
      trigger: 'change',
    },
  ],

  phone: [
    {
      required: true,
      message: '請輸入手機號',
      trigger: 'blur',
    },
    {
      pattern: /^1[3-9]\d{9}$/,
      message: '請輸入正確的11個手機號',
      trigger: 'blur',
    },
  ],
}

//新增員工
const addEmp = () => {
  dialogVisible.value = true
  dialogTitle.value = '新增員工'
  employee.value = {
  username: '',
  name: '',
  gender: '',
  phone: '',
  job: '',
  salary: '',
  deptId: '',
  entryDate: '',
  image: '',
  exprList: []
}
if(employeeFormRef.value){
  employeeFormRef.value.resetFields()
}
}
//偵聽employee員工對象中的工作經歷數據
watch (
  () => employee.value.exprList,
  (newValue, oldValue) => {
    if(employee.value.exprList && employee.value.exprList.length > 0){
      employee.value.exprList.forEach(expr => {
        expr.begin = expr.exprDate[0]
        expr.end = expr.exprDate[1]
      })
    }
  }, {deep: true}
)

//保存員工信息
const save = async () => {
  //表單較驗
  if(!employeeFormRef.value) return
  employeeFormRef.value.validate(async (valid) => {
    if(valid){

      let result
      if(employee.value.id) {
        result = await updateApi(employee.value)
      }else {
        result = await addApi(employee.value);
      }
      
     if(result.code){
       ElMessage.success('新增員工成功!')
       dialogVisible.value = false
       search()
      }else {
       ElMessage.error(result.msg)
      }
    }else {
      ElMessage.error('表單較驗不通過!')
    }
  })
  
}

//編輯員工訊息
const edit = async (id) => {
  const result = await queryInfoApi(id);
  if(result.code) {
    dialogVisible.value = true;
    dialogTitle.value = '修改員工'
    employee.value = result.data

    //對工作經歷進行處裡
    let exprList = employee.value.exprList
    if(exprList && exprList.length > 0) {
      exprList.forEach((expr) => {
        expr.exprDate = [expr.begin, expr.end]
      })
    }
  }
}

//刪除員工訊息
const deleteById = async (id) => {
  //彈框確認
  ElMessageBox.confirm('確認刪除該員工嗎?', '提示', 
    {confirmButtonText: '確認', cancelButtonText: '取消', type: 'warning'}
  ).then(async () => {
    const result = await deleteApi(id)
    if(result.code){
      ElMessage.success('刪除成功!')
      search()
    }else {
      ElMessage.error(result.msg)
    }
  }).catch(() => {
    ElMessage.info('您已取消刪除')
  })
}

//紀錄勾選員工的id
const selectionIds = ref([])
//複選框勾選發生變化時觸發--selection:當前選中的紀錄(數組)
const handleSelectionChange = (selection) => {
  selectionIds.value = selection.map(item => item.id)
}

//批量刪除員工訊息
const deleteByIds = async (id) => {
  //彈框確認
  ElMessageBox.confirm('確認刪除該些員工嗎?', '提示', 
    {confirmButtonText: '確認', cancelButtonText: '取消', type: 'warning'}
  ).then(async () => {
    if(selectionIds.value && selectionIds.value.length > 0){
        const result = await deleteApi(selectionIds.value)
        if(result.code){
          ElMessage.success('刪除成功!')
          search()
        }else {
          ElMessage.error(result.msg)
        }
    }else {
      ElMessage.info('您沒有選擇任何要刪除的數據')
    }
  }).catch(() => {
    ElMessage.info('您已取消刪除')
  })
}
</script>

<template>
  <h1>員工管理</h1> <br>
   <el-form :inline="true" :model="searchEmp" >

    <el-form-item label="姓名">
      <el-input v-model="searchEmp.name" placeholder="請輸入員工姓名" />
    </el-form-item>

    <el-form-item label="性別">
      <el-select v-model="searchEmp.gender" placeholder="請選擇">
        <el-option label="男" value="1"></el-option>
        <el-option label="女" value="2"></el-option>
      </el-select>
    </el-form-item>

    <el-form-item label="入職日期">
      <el-date-picker
        v-model="searchEmp.date"
        type="daterange"
        range-separator="至"
        start-placeholder="開始日期"
        end-placeholder="結束日期"
        value-format="YYYY-MM-DD"
      ></el-date-picker>
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="search()">查詢</el-button>
      <el-button type="info" @click="clear()">清空</el-button>
    </el-form-item>
  </el-form>

    <el-button type="primary" @click="addEmp()">+ 新增員工</el-button>
    <el-button type="danger" @click="deleteByIds()">- 批量刪除</el-button>

    <br><br>

    <el-table :data="empList" border style="width: 100%" @selection-change="handleSelectionChange">
    <el-table-column type="selection"  width="55" align="center" />
    <el-table-column prop="name" label="姓名"  width="120" align="center" />
    <el-table-column label="性別" width="170" align="center">
      <template #default="scope">
        {{scope.row.gender == 1 ? '男' : '女'}}
      </template>
    </el-table-column>
    <el-table-column label="頭像" width="170" align="center">
      <template #default="scope">
        <img :="scope.row.image" alt="Avatar" class="avatar">
      </template>
    </el-table-column>
    <el-table-column prop="deptName" label="所屬部門" width="170" align="center" />
    <el-table-column label="職位" width="120" align="center">
      <template #default="scope">
        <span v-if="scope.row.job == 1">班主任</span>
        <span v-else-if="scope.row.job == 2">講師</span>
        <span v-else-if="scope.row.job == 3">學工主任</span>
        <span v-else-if="scope.row.job == 4">教研主任</span>
        <span v-else-if="scope.row.job == 5">諮詢師</span>
        <span v-else>其他</span>
      </template>
    </el-table-column>
    <el-table-column prop="entryDate" label="入值日期" width="180" align="center" />
    <el-table-column prop="updateTime" label="最後操作時間" width="210" align="center" />
    <el-table-column prop="address" label="操作" fixed="right" align="center">
      <template #default="scope">
        <el-button type="primary" @click="edit(scope.row.id)">編輯</el-button>
        <el-button type="danger" @click="deleteById(scope.row.id)">刪除</el-button>
      </template>
    </el-table-column>
  </el-table>

  <br>
  
    <el-pagination
      v-model:current-page="currentPage"
      v-model:page-size="pageSize"
      :page-sizes="[10, 20, 30, 40]"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    >
    </el-pagination>

    <el-dialog v-model="dialogVisible" :title="dialogTitle">
    <el-form ref="employeeFormRef" :rules="rules" :model="employee" label-width="80px">
      <!-- 第一行 -->
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="用户名"  prop="username">
              <el-input v-model="employee.username" placeholder="請輸入員工用戶名，2-20個字"></el-input>
            </el-form-item>
          </el-col>
          
          <el-col :span="12">
            <el-form-item label="姓名"  prop="name">
              <el-input v-model="employee.name" placeholder="請輸入員工姓名，2-10個字"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

         <!-- 第二行 -->
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="性别"  prop="gender">
              <el-select v-model="employee.gender" placeholder="請選擇性別" style="width: 100%;">
                <el-option v-for="gender in genders" :key="gender.name" :label="gender.name" :value="gender.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="手機號"  prop="phone">
              <el-input v-model="employee.phone" placeholder="請輸入員工手機號"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

         <!-- 第三行 -->
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="職位">
              <el-select v-model="employee.job" placeholder="請選擇職位" style="width: 100%;">
                <el-option v-for="job in jobs" :key="job.name" :label="job.name" :value="job.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="薪資">
              <el-input v-model="employee.salary" placeholder="請輸入員工薪資"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 第四行 -->
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="所屬部門">
              <el-select v-model="employee.deptId" placeholder="請選擇部門" style="width: 100%;">
                <el-option v-for="dept in deptList" :key="dept.id" :label="dept.name" :value="dept.id"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="入職日期">
              <el-date-picker v-model="employee.entryDate" type="date" style="width: 100%;" placeholder="選擇日期" format="YYYY-MM-DD" value-format="YYYY-MM-DD"></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 第五行 -->
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="頭像">
              <el-upload
                class="avatar-uploader"
                action="/api/upload"
                :show-file-list="false"
                :headers="{'token': token}"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload"
                >
                <img v-if="imageUrl" :src="imageUrl" class="avatar" />
                <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
              </el-upload>
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 工作经历 -->
        <!-- 第六行 -->
        <el-row :gutter="10">
          <el-col :span="24">
            <el-form-item label="工作經歷">
              <el-button type="success" size="small" @click="addExprItem">+ 添加工作經歷</el-button>
            </el-form-item>
          </el-col>
        </el-row>

         <!-- 第七行 ...  工作经历 -->
        <el-row :gutter="3" v-for="(expr, index) in employee.exprList">
          <el-col :span="10">
            <el-form-item size="small" label="時間" label-width="80px">
              <el-date-picker type="daterange" v-model="expr.exprDate" range-separator="至" start-placeholder="開始日期" end-placeholder="結束日期" format="YYYY-MM-DD" value-format="YYYY-MM-DD" ></el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item size="small" label="公司" label-width="60px">
              <el-input placeholder="請輸入公司名稱" v-model="expr.company"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item size="small" label="職位" label-width="60px">
              <el-input placeholder="請輸入職位" v-model="expr.job"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="2">
            <el-form-item size="small" label-width="0px">
              <el-button type="danger" @click="delExprItem(index)">- 刪除</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      
        
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="save">
          保存
        </el-button>
      </div>
    </template>
  </el-dialog>
  
</template>

<style scoped>
.avatar { 
  height: 40px;
}

.avatar-uploader .avatar {
  width: 178px;
  height: 178px;
  display: block;
}

.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 78px;
  height: 78px;
  text-align: center;
  border: 1px dashed var(--el-border-color);
}
</style>