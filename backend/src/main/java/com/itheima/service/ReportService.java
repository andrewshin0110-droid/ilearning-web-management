package com.itheima.service;

import java.util.List;
import java.util.Map;

import com.itheima.pojo.JobOption;

public interface ReportService {

	JobOption getEmpJobData();

	List<Map> getEmpGenderData();

}
