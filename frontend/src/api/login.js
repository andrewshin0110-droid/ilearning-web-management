import request from "@/utils/request"

//登入
export const loginApi = (data) => request.post('/login', data)