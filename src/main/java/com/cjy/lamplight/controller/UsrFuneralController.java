package com.cjy.lamplight.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cjy.lamplight.dto.Funeral;
import com.cjy.lamplight.dto.ResultData;
import com.cjy.lamplight.service.FuneralService;

@Controller
public class UsrFuneralController {
	@Autowired
	private FuneralService funeralService;
	
	@GetMapping("/usr/funeral/myList")
	@ResponseBody
	public ResultData showMyList(HttpServletRequest req, int memberId, String memberType) {
		int expertId = 0;
		int assistantId = 0;

		if(memberType.equals("expert")) {
			expertId = memberId;
		}
		if(memberType.equals("assistant")) {
			assistantId = memberId;
		}
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("expertId", expertId);
		param.put("assistantId", assistantId);

		List<Funeral> funerals = funeralService.getForPrintFuneralsByMemberId(param);

		req.setAttribute("funerals", funerals);
		
		return new ResultData("S-1", "성공", "funerals", funerals);
	}
	
	@GetMapping("/usr/funeral/list")
	@ResponseBody
	public ResultData showList(HttpServletRequest req) {

		List<Funeral> funerals = funeralService.getForPrintFunerals();

		req.setAttribute("funerals", funerals);
		
		return new ResultData("S-1", "성공", "funerals", funerals);
	}
	
	@GetMapping("/usr/funeral/doApply")
	@ResponseBody
	public ResultData doApply(@RequestParam Map<String, Object> param) {
		
	
		if (param.get("funeralId") == null) {
			return new ResultData("F-1", "funeralId를 입력해주세요.");
		}
		if (param.get("asstId") == null) {
			return new ResultData("F-1", "asstId를 입력해주세요.");
		}

		return funeralService.asstApplyForFuneral(param);
	}

	
}
