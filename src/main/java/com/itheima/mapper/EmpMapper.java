package com.itheima.mapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.itheima.pojo.Emp;
import com.itheima.pojo.EmpQueryParam;

@Mapper
public interface EmpMapper {

	
	List<Emp> list(EmpQueryParam empQueryParam);

	@Options(useGeneratedKeys = true, keyProperty = "id")
	@Insert("insert into emp(username, name, gender, phone, job, salary, image, entry_date, dept_id, create_time, update_time) " +
	        "values (#{username},#{name},#{gender},#{phone},#{job},#{salary},#{image},#{entryDate},#{deptId},#{createTime},#{updateTime})")
	void insert(Emp emp);

	void deleteByIds(List<Integer> ids);

	Emp getById(Integer id);

	void updateById(Emp emp);
	
	List<Map<String, Object>> countEmpJobData();

	List<Map> countEmpGenderData();

	@Select("select * from emp where username = #{username} and password = #{password}")
	Emp getUsernameAndPassword(Emp emp);

	
}
