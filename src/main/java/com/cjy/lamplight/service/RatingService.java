package com.cjy.lamplight.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cjy.lamplight.dao.RatingDao;
import com.cjy.lamplight.dto.Member;
import com.cjy.lamplight.dto.Rating;
import com.cjy.lamplight.util.Util;
import com.cjy.lamplight.dto.ResultData;

@Service
public class RatingService {
	@Autowired
	private RatingDao ratingDao;
	@Autowired
	private MemberService memberService;
	
	public ResultData addRating(Map<String, Object> param) {
		ratingDao.addRating(param);

		return new ResultData("S-1", "평점이 등록되었습니다.");
	}

	public Rating getRating(int id) {
		return ratingDao.getRating(id);
	}

	public ResultData getActorCanDeleteRd(Rating Rating, Member actor) {
		if (Rating.getMemberId() == actor.getId()) {
			return new ResultData("S-1", "가능합니다.");
		}

		if (memberService.isAdmin(actor)) {
			return new ResultData("S-2", "가능합니다.");
		}

		return new ResultData("F-1", "권한이 없습니다.");
	}

	public ResultData deleteRating(int id) {
		ratingDao.deleteRating(id);

		return new ResultData("S-1", "삭제하였습니다.", "id", id);
	}

	public ResultData getActorCanModifyRd(Rating Rating, Member actor) {
		return getActorCanDeleteRd(Rating, actor);
	}

	public ResultData modifyRating(int id, String body) {
		ratingDao.modifyRating(id, body);

		return new ResultData("S-1", "댓글을 수정하였습니다.", "id", id);
	}
}
