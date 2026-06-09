package com.itheima.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itheima.anno.LogOperation;
import com.itheima.pojo.Dept;
import com.itheima.pojo.Result;
import com.itheima.service.DeptService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequestMapping("/depts")
@RestController
public class DeptController {

	@Autowired
	private DeptService deptService;
	
	@LogOperation
	@GetMapping
	public Result list() {
		log.info("查询部门列表");
		List<Dept> deptList = deptService.findAll();
		return Result.success(deptList);
	}
	
//	/**
//	* 根据ID删除部门 - 简单参数接收: 方式一 (HttpServletRequest)
//	*/
//	@DeleteMapping("/depts")
//	public Result delete(HttpServletRequest request){
//	    String idStr = request.getParameter("id");
//	    int id = Integer.parseInt(idStr);
//	    
//	    System.out.println("根据ID删除部门: " + id);
//	    return Result.success();
//	}
	
//	@DeleteMapping("/depts")
//	public Result delete(@RequestParam(value = "id", required = false) Integer deptId){
//	    System.out.println("根据ID删除部门: " + deptId);
//	    return Result.success();
//	    //@RequestParam注解required属性默认为true，代表该参数必须传递，如果不传递将报错。 如果参数可选，可以将属性设置为false。
//	}
	@LogOperation
	@DeleteMapping
	public Result delete(Integer id) {
		log.info("根据id删除部门, id: {}" , id);
		deptService.deleteById(id);
		return Result.success();
	}
	
	@LogOperation
	@PostMapping
	public Result add(@RequestBody Dept dept) {
		log.info("新增部门, dept: {}" , dept);
		deptService.save(dept);
		return Result.success();
	}
	
	@GetMapping("{id}")
	public Result getInof(@PathVariable Integer id) {
		log.info("根据ID查询, id: {}" , id);
		Dept dept = deptService.getInof(id);
		return Result.success(dept);
	}
	
	@LogOperation
	@PutMapping
	public Result update(@RequestBody Dept dept) {
		log.info("修改部门, dept: {}" , dept);
		deptService.update(dept);
		return Result.success();
	}
	
}
