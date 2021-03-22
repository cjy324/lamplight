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

@Controller
public class UsrReviewController {
	@Autowired
	private ReviewService reviewService;
	@Autowired
	private MemberService memberService;
	
	
	@PostMapping("/usr/review/doAdd")
	@ResponseBody
	public ResultData doAdd(@RequestParam Map<String, Object> param) {
		

		if (param.get("body") == null) {
			return new ResultData("F-1", "body를 입력해주세요.");
		}
		
		if (param.get("relTypeCode") == null) {
			return new ResultData("F-1", "relTypeCode를 입력해주세요.");
		}

		if (param.get("relId") == null) {
			return new ResultData("F-1", "relId를 입력해주세요.");
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
	
	@PostMapping("/usr/review/doDelete")
	@ResponseBody
	public ResultData doDelete(Integer id, HttpServletRequest req) {
		//int loginedMemberId = (int) req.getAttribute("loginedMemberId");
		Member loginedMember = (Member) req.getAttribute("loginedMember");

		if (id == null) {
			return new ResultData("F-1", "id를 입력해주세요.");
		}

		Review review = reviewService.getReview(id);

		if (review == null) {
			return new ResultData("F-1", "해당 리뷰는 존재하지 않습니다.");
		}

		ResultData actorCanDeleteRd = reviewService.getActorCanDeleteRd(review, loginedMember);

		if (actorCanDeleteRd.isFail()) {
			return actorCanDeleteRd;
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
