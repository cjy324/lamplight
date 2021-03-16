package com.cjy.lamplight.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cjy.lamplight.dao.DirectorDao;
import com.cjy.lamplight.dto.Director;
import com.cjy.lamplight.dto.GenFile;
import com.cjy.lamplight.dto.ResultData;
import com.cjy.lamplight.util.Util;

@Service
public class DirectorService {

	@Autowired
	DirectorDao directorDao;

	@Autowired
	GenFileService genFileService;

	public ResultData join(Map<String, Object> param) {
		directorDao.join(param);

		int id = Util.getAsInt(param.get("id"), 0);

		genFileService.changeInputFileRelIds(param, id);

		return new ResultData("S-1", param.get("nickname") + "님, 환영합니다.", "id", id);
	}

	public Director getDirector(int id) {
		return directorDao.getDirector(id);
	}

	public Director getDirectorByLoginId(String loginId) {
		return directorDao.getDirectorByLoginId(loginId);
	}

	public ResultData modifyDirector(Map<String, Object> param) {
		directorDao.modifyDirector(param);

		return new ResultData("S-1", "회원정보가 수정되었습니다.");
	}

	public boolean isAdmin(Director actor) {
		return actor.getAuthLevel() == 7;
	}

	public Director getdirectorByAuthKey(String authKey) {
		return directorDao.getDirectorByAuthKey(authKey);
	}

	public List<Director> getForPrintDirectors(String searchKeywordType, String searchKeyword, int page, int itemsInAPage,
			Map<String, Object> param) {
		int limitStart = (page - 1) * itemsInAPage;
		int limitTake = itemsInAPage;

		param.put("searchKeywordType", searchKeywordType);
		param.put("searchKeyword", searchKeyword);
		param.put("limitStart", limitStart);
		param.put("limitTake", limitTake);

		return directorDao.getForPrintDirectors(param);
	}

	public static String getAuthLevelName(Director director) {
		switch (director.getAuthLevel()) {
		case 7:
			return "관리자";
		case 3:
			return "일반";
		default:
			return "유형 정보 없음";
		}
	}

	public static String getAuthLevelNameColor(Director director) {
		switch (director.getAuthLevel()) {
		case 7:
			return "red";
		case 3:
			return "gray";
		default:
			return "";
		}
	}

	public Director getForPrintDirector(int id) {
		return directorDao.getForPrintDirector(id);
	}

	public Director getForPrintDirectorByAuthKey(String authKey) {
		Director director = directorDao.getDirectorByAuthKey(authKey);
		// 기본 멤버에서 추가정보를 업데이트해서 리턴
		updateForPrint(director);

		return director;
	}

	public Director getForPrintDirectorByLoginId(String loginId) {
		Director director = directorDao.getDirectorByLoginId(loginId);
		// 기본 멤버에서 추가정보를 업데이트해서 리턴
		updateForPrint(director);

		return director;
	}

	// 기본멤버 정보에 추가 정보를 업데이트해서 리턴
	private void updateForPrint(Director director) {
		// 멤버의 섬네일 이미지 가져오기
		GenFile genFile = genFileService.getGenFile("director", director.getId(), "common", "attachment", 1);

		// 만약, 멤버의 섬네일 이미지가 있으면 extra__thumbImg 업데이트
		if (genFile != null) {
			String imgUrl = genFile.getForPrintUrl();
			director.setExtra__thumbImg(imgUrl);
		}

	}

	public List<Director> getDirectors() {
		return directorDao.getDirectors();
	}


}
