package com.itheima.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itheima.anno.LogOperation;
import com.itheima.pojo.Emp;
import com.itheima.pojo.EmpQueryParam;
import com.itheima.pojo.PageResult;
import com.itheima.pojo.Result;
import com.itheima.service.EmpService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/emps")
public class EmpController {
	
	@Autowired
	private EmpService empService;
	
	@LogOperation
	@GetMapping
	public Result page(EmpQueryParam empQueryParam) {
		log.info("查詢員工訊息,{},{},{},{},{},{} page, pagesize, name, gender, begin, end");
		PageResult pageResult = empService.page(empQueryParam);
		return Result.success(pageResult);
	}
	
	@LogOperation
	@PostMapping
	public Result save(@RequestBody Emp emp) {
		log.info("請求參數emp: {}", emp);
		empService.save(emp);
		return Result.success();
	}
	
	@LogOperation
	@DeleteMapping
	public Result delete(@RequestParam List<Integer> ids) {
		log.info("批量刪除員工: ids={}", ids);
		empService.deleteByIds(ids);
		return Result.success();
	}
	
	@GetMapping("/{id}")
	public Result getInfo(@PathVariable Integer id) {
		log.info("根據id查詢員工訊息");
		Emp emp = empService.getInfo(id);
		return Result.success(emp);
	}
	
	@LogOperation
	@PutMapping
	public Result update(@RequestBody Emp emp){
	    log.info("修改员工信息, {}", emp);
	    empService.update(emp);
	    return Result.success();
	}

}
