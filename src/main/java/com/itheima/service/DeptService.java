package com.itheima.service;

import java.util.List;

import com.itheima.pojo.Dept;

public interface DeptService {

	public List<Dept> findAll();

	public void deleteById(Integer id);

	public void save(Dept dept);

	public Dept getInof(Integer id);

	public void update(Dept dept);

}
