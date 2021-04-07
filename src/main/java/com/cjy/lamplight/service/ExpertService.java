package com.cjy.lamplight.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cjy.lamplight.dao.ExpertDao;
import com.cjy.lamplight.dto.Expert;
import com.cjy.lamplight.dto.GenFile;
import com.cjy.lamplight.dto.ResultData;
import com.cjy.lamplight.dto.Review;
import com.cjy.lamplight.util.Util;

@Service
public class ExpertService {

	@Autowired
	ExpertDao expertDao;
	@Autowired
	GenFileService genFileService;
	@Autowired
	private ReviewService reviewService;

	public ResultData join(Map<String, Object> param) {
		expertDao.join(param);

		int id = Util.getAsInt(param.get("id"), 0);

		genFileService.changeInputFileRelIds(param, id);

		return new ResultData("S-1", param.get("name") + "님, 환영합니다.", "id", id);
	}

	public Expert getExpert(int id) {
		return expertDao.getExpert(id);
	}

	public Expert getExpertByLoginId(String loginId) {
		return expertDao.getExpertByLoginId(loginId);
	}

	public ResultData modifyExpert(Map<String, Object> param) {
		expertDao.modifyExpert(param);
		
		int id = Util.getAsInt(param.get("id"), 0);

		genFileService.changeInputFileRelIds(param, id);

		return new ResultData("S-1", "회원정보가 수정되었습니다.");
	}

	public Expert getExpertByAuthKey(String authKey) {
		return expertDao.getExpertByAuthKey(authKey);
	}

	public static String getAcknowledgmentStepName(Expert expert) {
		switch (expert.getAcknowledgment_step()) {
		case 3:
			return "가입실패";
		case 2:
			return "가입승인";
		case 1:
			return "가입대기";
		default:
			return "유형 정보 없음";
		}
	}

	public static String getAcknowledgmentStepNameColor(Expert expert) {
		switch (expert.getAcknowledgment_step()) {
		case 3:
			return "red";
		case 2:
			return "gray";
		case 1:
			return "blue";
		default:
			return "";
		}
	}

	public Expert getForPrintExpert(int id) {
		Expert expert = expertDao.getForPrintExpert(id);
		
		updateForPrint(expert);

		return expert;
	}

	public Expert getForPrintExpertByAuthKey(String authKey) {
		Expert expert = expertDao.getExpertByAuthKey(authKey);

		updateForPrint(expert);

		return expert;
	}

	public Expert getForPrintExpertByLoginId(String loginId) {
		Expert expert = expertDao.getExpertByLoginId(loginId);

		updateForPrint(expert);

		return expert;
	}

	// 기본멤버 정보에 추가 정보를 업데이트해서 리턴
	private void updateForPrint(Expert expert) {
		// 멤버의 섬네일 이미지 가져오기
		GenFile genFile = genFileService.getGenFile("expert", expert.getId(), "common", "attachment", 1);

		// 만약, 멤버의 섬네일 이미지가 있으면 extra__thumbImg 업데이트
		if (genFile != null) {
			String imgUrl = genFile.getForPrintUrl();
			expert.setExtra__thumbImg(imgUrl);
		}

	}


	public List<Expert> getExperts() {
		List<Expert> experts = expertDao.getExperts();
		
		for(Expert expert : experts) {
			updateForPrint(expert);
			addReviewList(expert); //각 expert객체마다 review리스트를 담아서 넘겨줌
		}
		
		return experts;
	}

	private void addReviewList(Expert expert) {
		String relTypeCode = "expert";
		List<Review> reviews = reviewService.getForPrintReviews(relTypeCode);
		
		for(Review review : reviews) {
			if(review != null && review.getRelId() == expert.getId()) {
				expert.getExtra__reviews().add(review);
			}
		}

	}

	public List<Expert> getForPrintExpertsByRegion(String region) {
		return expertDao.getForPrintExpertsByRegion(region);
	}

}
