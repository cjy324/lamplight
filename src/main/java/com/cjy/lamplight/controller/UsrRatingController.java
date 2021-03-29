package com.cjy.lamplight.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cjy.lamplight.dto.Member;
import com.cjy.lamplight.dto.Rating;
import com.cjy.lamplight.dto.ResultData;
import com.cjy.lamplight.service.RatingService;

@Controller
public class UsrRatingController {
	@Autowired
	private RatingService ratingService;
	
	@PostMapping("/usr/rating/doAdd")
	@ResponseBody
	public ResultData doAdd(@RequestParam Map<String, Object> param) {
		
	
		if (param.get("relTypeCode") == null) {
			return new ResultData("F-1", "relTypeCode를 입력해주세요.");
		}

		if (param.get("relId") == null) {
			return new ResultData("F-1", "relId를 입력해주세요.");
		}
		if (param.get("memberId") == null) {
			return new ResultData("F-1", "memberId를 입력해주세요.");
		}

		return ratingService.addRating(param);
	}

	
	@PostMapping("/usr/rating/doDelete")
	@ResponseBody
	public ResultData doDelete(Integer id, HttpServletRequest req) {
		//int loginedMemberId = (int) req.getAttribute("loginedMemberId");
		Member loginedMember = (Member) req.getAttribute("loginedMember");

		if (id == null) {
			return new ResultData("F-1", "id를 입력해주세요.");
		}

		Rating rating = ratingService.getRating(id);

		if (rating == null) {
			return new ResultData("F-1", "해당 리뷰는 존재하지 않습니다.");
		}

		ResultData actorCanDeleteRd = ratingService.getActorCanDeleteRd(rating, loginedMember);

		if (actorCanDeleteRd.isFail()) {
			return actorCanDeleteRd;
		}

		return ratingService.deleteRating(id);
	}
	
	@PostMapping("/usr/rating/doModify")
	@ResponseBody
	public ResultData doModify(Integer id, String body, HttpServletRequest req) {
		//int loginedMemberId = (int)req.getAttribute("loginedMemberId");
		Member loginedMember = (Member) req.getAttribute("loginedMember");
		
		if (id == null) {
			return new ResultData("F-1", "id를 입력해주세요.");
		}

		if (body == null) {
			return new ResultData("F-1", "body를 입력해주세요.");
		}

		Rating rating = ratingService.getRating(id);

		if (rating == null) {
			return new ResultData("F-1", "해당 리뷰는 존재하지 않습니다.");
		}

		ResultData actorCanModifyRd = ratingService.getActorCanModifyRd(rating, loginedMember);

		if (actorCanModifyRd.isFail()) {
			return actorCanModifyRd;
		}

		return ratingService.modifyRating(id, body);
	}
}
