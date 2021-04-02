package com.cjy.lamplight.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cjy.lamplight.dto.ResultData;
import com.cjy.lamplight.service.FuneralService;

@Controller
public class UsrFuneralController {
	@Autowired
	private FuneralService funeralService;
	
	@PostMapping("/usr/funeral/doAdd")
	@ResponseBody
	public ResultData doAdd(@RequestParam Map<String, Object> param) {
		
	
		if (param.get("relTypeCode") == null) {
			return new ResultData("F-1", "relTypeCode를 입력해주세요.");
		}

		if (param.get("relId") == null) {
			return new ResultData("F-1", "relId를 입력해주세요.");
		}
		if (param.get("clientId") == null) {
			return new ResultData("F-1", "clientId를 입력해주세요.");
		}

		return funeralService.addFuneral(param);
	}

	
}
