package com.itheima.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.itheima.pojo.Result;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class UploadController {

	private static final String UPLOAD_DIR = "D:\\images\\";
	
	@PostMapping("/upload")
	public Result upload(String username, Integer age, MultipartFile file) throws Exception, IOException {
		log.info("上傳文件: {}, {}, {}", username, age, file);
		
		
		if(!file.isEmpty()) {
			String originalFileName = file.getOriginalFilename();
			String extName = originalFileName.substring(originalFileName.lastIndexOf("."));
			String uniqueFileName = UUID.randomUUID().toString().replace("-", "") + extName;
			
			File targetFlie = new File(UPLOAD_DIR + uniqueFileName);
			
			if(!targetFlie.getParentFile().exists()) {
				targetFlie.getParentFile().mkdirs();
			}
			
			file.transferTo(targetFlie);
		}
		
		return Result.success();
	}
}
