import request from "@/utils/request"

//查詢員工列表數據
export const queryPageApi = (name, gender, begin, end, page, pageSize) =>
    request.get(`/emps?name=${name}&gender=${gender}&begin=${begin}&end=${end}&page=${page}&pageSize=${pageSize}`)

//新增員工信息
export const addApi = (data) => request.post('/emps', data)

//根據id查詢
export const queryInfoApi = (id) => request.get(`/emps/${id}`)

//修改員工訊息
export const updateApi = (data) => request.put('/emps', data)

//刪除員工訊息
export const deleteApi = (id) => request.delete(`/emps?ids=${id}`)