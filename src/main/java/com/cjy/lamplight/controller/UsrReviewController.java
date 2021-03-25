package com.cjy.lamplight.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cjy.lamplight.dto.Member;
import com.cjy.lamplight.dto.ResultData;
import com.cjy.lamplight.dto.Review;
import com.cjy.lamplight.service.MemberService;
import com.cjy.lamplight.service.ReviewService;
import com.cjy.lamplight.util.Util;

@Controller
public class UsrReviewController {
	@Autowired
	private ReviewService reviewService;
	@Autowired
	private MemberService memberService;
	
	
	@PostMapping("/usr/review/doAdd")
	@ResponseBody
	public ResultData doAdd(@RequestParam Map<String, Object> param) {
		int memberId = Util.getAsInt(param.get("memberId"), 0);
		if(memberId == 0) {
			return new ResultData("F-1", "memberId를 확인해주세요.");
		}
		
		if (param.get("relId") == null) {
			return new ResultData("F-1", "relId를 입력해주세요.");
		}
		
		int relId = Util.getAsInt(param.get("relId"), 0);
		
		boolean isMemberCanReview = reviewService.isMemberCanReview(memberId, relId);
		
		if(isMemberCanReview == false) {
			return new ResultData("F-2", "회원님은 이미 리뷰를 작성하셨습니다.");
		}

		if (param.get("body") == null) {
			return new ResultData("F-1", "body를 입력해주세요.");
		}
		
		if (param.get("relTypeCode") == null) {
			return new ResultData("F-1", "relTypeCode를 입력해주세요.");
		}

		

		return reviewService.addReview(param);
	}

	@GetMapping("/usr/review/list")
	@ResponseBody
	public ResultData showList(String relTypeCode) {

		if (relTypeCode == null) {
			return new ResultData("F-1", "relTypeCode를 입력해주세요.");
		}
		

		List<Review> reviews = reviewService.getForPrintReviews(relTypeCode);

		return new ResultData("S-1", "성공", "reviews", reviews);
	}
	
	@GetMapping("/usr/review/doDelete")
	@ResponseBody
	public ResultData doDelete(Integer id) {

		if (id == null) {
			return new ResultData("F-1", "id를 입력해주세요.");
		}

		Review review = reviewService.getReview(id);

		if (review == null) {
			return new ResultData("F-1", "해당 리뷰는 존재하지 않습니다.");
		}

		return reviewService.deleteReview(id);
	}
	
	@PostMapping("/usr/review/doModify")
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

		Review review = reviewService.getReview(id);

		if (review == null) {
			return new ResultData("F-1", "해당 리뷰는 존재하지 않습니다.");
		}

		ResultData actorCanModifyRd = reviewService.getActorCanModifyRd(review, loginedMember);

		if (actorCanModifyRd.isFail()) {
			return actorCanModifyRd;
		}

		return reviewService.modifyReview(id, body);
	}
}
