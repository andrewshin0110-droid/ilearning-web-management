package com.itheima.pojo;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dept {

	private Integer id;
	private String name;
	private LocalDateTime createTime;
	private LocalDateTime updateTime;
	
	
	
}
