import request from "@/utils/request"

//列表查询
export const queryAllApi = () => request.get('/depts')

//新增部門
export const addDeptApi = (data) => request.post('/depts', data)

//根據id查詢
export const queryInfoApi = (id) => request.get(`/depts/${id}`)

//修改部門
export const updateDeptApi = (data) => request.put('/depts', data)

//刪除部門
export const deleteDeptApi = (id) => request.delete(`/depts?id=${id}`)