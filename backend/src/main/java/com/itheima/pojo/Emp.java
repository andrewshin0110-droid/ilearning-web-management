package com.itheima.pojo;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Emp {
    private Integer id; //ID,主建
    private String username; //用户名
    private String password; //密碼
    private String name; //姓名
    private Integer gender; //性别, 1:男, 2:女
    private String phone; //手機號
    private Integer job; //職位, 1:班主任,2:講师,3:學工主任,4:教研主任,5:諮詢師
    private Integer salary; //薪資
    private String image; //頭像
    private LocalDate entryDate; //入職日期
    private Integer deptId; //關聯的部門ID
    private LocalDateTime createTime; //創建時間
    private LocalDateTime updateTime; //修改時間

    //封裝部門名稱數
    private String deptName; //部門名稱
    //封裝員工經歷訊息
    private List<EmpExpr> exprList;
}
