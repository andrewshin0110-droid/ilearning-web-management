import axios from 'axios'
import { ElMessage } from 'element-plus'
import router from '../router'

//創建axios實體對象
const request = axios.create({
  baseURL: '/api',
  timeout: 600000
})

//axios的請求request攔截器, 每次請求獲取localStorage中的loginUser, 從中獲取token, 再請求頭token中攜帶到服務端
request.interceptors.request.use(
  (config) => {
    let loginUser = JSON.parse(localStorage.getItem('loginUser'))
    console.log(localStorage.getItem('loginUser'))
    if(loginUser){
      config.headers.token = loginUser.token
    }
    return config
  }
)

//axios的響應 response 攔截器
request.interceptors.response.use(
  (response) => { //成功回調
    return response.data
  },
  (error) => { //失败回調
    //如果響應的狀態碼為401, 則路由道登入頁面
    if(error.response.status === 401){
      ElMessage.error('登入失效，請重新登入!')
      router.push('/login')
    }else {
      ElMessage.success('接口訪問異常!')
    }
    return Promise.reject(error)
  }
)

export default request