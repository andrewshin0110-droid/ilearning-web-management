package com.itheima.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itheima.mapper.DeptMapper;
import com.itheima.pojo.Dept;
import com.itheima.service.DeptService;

@Service
public class DeptServiceImpl implements DeptService {

	@Autowired
	private DeptMapper deptMapper;
	
	public List<Dept> findAll(){
		return deptMapper.findAll();
	}

	public void deleteById(Integer id) {
		deptMapper.deleteById(id);
	}

	public void save(Dept dept) {
		//補全基礎属性
		dept.setCreateTime(LocalDateTime.now());
		dept.setUpdateTime(LocalDateTime.now());
		deptMapper.insert(dept);
		
	}

	public Dept getInof(Integer id) {
		return deptMapper.getById(id);
	}

	public void update(Dept dept) {
		dept.setUpdateTime(LocalDateTime.now());
		deptMapper.update(dept);
		
	}
}
