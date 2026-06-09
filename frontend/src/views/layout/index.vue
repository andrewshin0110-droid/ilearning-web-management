<script setup>
import {ref, onMounted} from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useRouter } from 'vue-router'

const loginName = ref('')
let router = useRouter()

onMounted(() => {
  let loginUser = JSON.parse(localStorage.getItem('loginUser'))
  if(loginUser){
    loginName.value = loginUser.name
  }
}) 

const logout = () => {
  ElMessageBox.confirm('確認登出嗎?', '提示', {
    confirmButtonText: '確認',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    ElMessage.success('登出成功!')
    localStorage.removeItem('loginUser')
    router.push('/login')
  })
}
</script>

<template>
  <div class="common-layout">
    <el-container>
      <!-- Header 區域 -->
      <el-header class="header">
        <span class="title">iLearing學習系統</span>
        <span class="right_tool">
          <a href="">
            <el-icon><EditPen /></el-icon> 修改密碼 &nbsp;&nbsp;&nbsp; |  &nbsp;&nbsp;&nbsp;
          </a>
          <a href="javascrip:void(0)" @click="logout">
            <el-icon><SwitchButton /></el-icon> 退出登入 【{{ loginName }}】
          </a>
        </span>
      </el-header>
      
      <el-container>
        <!-- 左側菜單 -->
        <el-aside width="200px" class="aside">
          <!-- 左側菜單欄 -->
          <el-menu router>
            <!-- 首頁菜單 -->
            <el-menu-item index="/index">
              <el-icon><Promotion /></el-icon> 首頁
            </el-menu-item>
            
            
            <!-- 班级管理菜單 -->
            <el-sub-menu index="/manage">
              <template #title>
                <el-icon><Menu /></el-icon> 班級學員管理
              </template>
              <el-menu-item index="/clazz">
                <el-icon><HomeFilled /></el-icon>班級管理
              </el-menu-item>
              <el-menu-item index="/stu">
                <el-icon><UserFilled /></el-icon>學員管理
              </el-menu-item>
            </el-sub-menu>
            
            <!-- 系统信息管理 -->
            <el-sub-menu index="/system">
              <template #title>
                <el-icon><Tools /></el-icon>系统信息管理
              </template>
              <el-menu-item index="/dept">
                <el-icon><HelpFilled /></el-icon>部門管理
              </el-menu-item>
              <el-menu-item index="/emp">
                <el-icon><Avatar /></el-icon>員工管理
              </el-menu-item>
            </el-sub-menu>

            <!-- 數據統計管理 -->
            <el-sub-menu index="/report">
              <template #title>
                <el-icon><Histogram /></el-icon>數據統計管理
              </template>
              <el-menu-item index="/empReport">
                <el-icon><InfoFilled /></el-icon>員工信息統計
              </el-menu-item>
              <el-menu-item index="/stuReport">
                <el-icon><Share /></el-icon>學員信息統計
              </el-menu-item>
              <el-menu-item index="/log">
                <el-icon><Document /></el-icon>日誌信息統計
              </el-menu-item>
            </el-sub-menu>
          </el-menu>
        </el-aside>
        
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
      
    </el-container>
  </div>
</template>

<style scoped>
.header {
  background-image: linear-gradient(to right, #00547d, #007fa4, #00aaa0, #00d072, #a8eb12);
}

.title {
  color: white;
  font-size: 40px;
  font-family: 楷体;
  line-height: 60px;
  font-weight: bolder;
}

.right_tool{
  float: right;
  line-height: 60px;
}

a {
  color: white;
  text-decoration: none;
}

.aside {
  width: 220px;
  border-right: 1px solid #ccc;
  height: 730px;
}
</style>
