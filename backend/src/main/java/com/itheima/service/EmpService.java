package com.itheima.service;

import java.time.LocalDate;
import java.util.List;

import com.itheima.pojo.Emp;
import com.itheima.pojo.EmpQueryParam;
import com.itheima.pojo.LoginInfo;
import com.itheima.pojo.PageResult;

public interface EmpService {

	PageResult page(EmpQueryParam empQueryParam);

	void save(Emp emp) ;

	void deleteByIds(List<Integer> ids);

	Emp getInfo(Integer id);

	void update(Emp emp);

	LoginInfo login(Emp emp);

}
