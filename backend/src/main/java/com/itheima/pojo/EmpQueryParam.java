package com.itheima.pojo;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class EmpQueryParam {

	private Integer page = 1; //頁碼
    private Integer pageSize = 10; //每頁展示紀錄
    private String name; //姓名
    private Integer gender; //性别
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate begin; //入職開始時間
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate end; //入職結束時間
}
