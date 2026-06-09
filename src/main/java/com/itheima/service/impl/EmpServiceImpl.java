package com.itheima.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.mapper.EmpExprMapper;
import com.itheima.mapper.EmpLogMapper;
import com.itheima.mapper.EmpMapper;
import com.itheima.pojo.Emp;
import com.itheima.pojo.EmpExpr;
import com.itheima.pojo.EmpLog;
import com.itheima.pojo.EmpQueryParam;
import com.itheima.pojo.LoginInfo;
import com.itheima.pojo.PageResult;
import com.itheima.service.EmpService;
import com.itheima.utils.JwtUtils;

@Service
public class EmpServiceImpl implements EmpService {

	@Autowired
	private EmpMapper empMapper;
	@Autowired
	private EmpExprMapper empExprMapper;
	@Autowired
	private EmpLogMapper empLogMapper;
	
	@Override
	public PageResult page(EmpQueryParam empQueryParam) {
		
		PageHelper.startPage(empQueryParam.getPage(),empQueryParam.getPageSize());
		
		List<Emp> listEmp = empMapper.list(empQueryParam);
		Page<Emp> p = (Page<Emp>)listEmp;
		
		return new PageResult(p.getTotal(), p.getResult());
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void save(Emp emp) {
		
		try{
			emp.setCreateTime(LocalDateTime.now());
			emp.setUpdateTime(LocalDateTime.now());
			
			empMapper.insert(emp);
			

			
			// 批量保存員工的工作經歷信息
			Integer empId = emp.getId();
			List<EmpExpr> exprList = emp.getExprList();
			if(!CollectionUtils.isEmpty(exprList)) {
				exprList.forEach(empExpr -> empExpr.setEmpId(empId));
				empExprMapper.insertBatch(exprList);
			}
		}finally {
			EmpLog empLog = new EmpLog(null, LocalDateTime.now(), emp.toString());
			empLogMapper.insert(empLog);
		}
	
	}
	
	public void deleteByIds(List<Integer> ids) {
		
		empMapper.deleteByIds(ids);
		
		empExprMapper.deleteByEmpIds(ids);
	}

	public Emp getInfo(Integer id) {
		return empMapper.getById(id);
	}

	@Override
	public void update(Emp emp) {
		//1. 根據ID更新員工基本信息
	    emp.setUpdateTime(LocalDateTime.now());
	    empMapper.updateById(emp);

	    //2. 根據員工ID删除員工的工作經歷信息 【删除舊的】
	    empExprMapper.deleteByEmpIds(Arrays.asList(emp.getId()));
	    
	  //3. 新增員工的工作經歷數據 【新增新的】
	    Integer empId = emp.getId();
		List<EmpExpr> exprList = emp.getExprList();
		if(!CollectionUtils.isEmpty(exprList)) {
			exprList.forEach(empExpr -> empExpr.setEmpId(empId));
			empExprMapper.insertBatch(exprList);
		}
	    
		
	}

	@Override
	public LoginInfo login(Emp emp) {
		
		Emp empLogin = empMapper.getUsernameAndPassword(emp);
		if(empLogin != null) {
			//1. 生成JWT令牌
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("id", empLogin.getId());
			dataMap.put("username", empLogin.getUsername());
			String jwt = JwtUtils.generateJwt(dataMap);
			
			LoginInfo loginInfo = new LoginInfo(empLogin.getId(), empLogin.getUsername(), empLogin.getName(), jwt);
			return loginInfo;
		}
		
		return null;
	}
}
