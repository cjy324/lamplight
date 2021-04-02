package com.cjy.lamplight.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cjy.lamplight.dao.FuneralDao;
import com.cjy.lamplight.dto.Board;
import com.cjy.lamplight.dto.Client;
import com.cjy.lamplight.dto.Expert;
import com.cjy.lamplight.dto.Funeral;
import com.cjy.lamplight.dto.ResultData;
import com.cjy.lamplight.util.Util;

//일반 자바에서는 다른 곳에서 FuneralService를 사용하려면
//FuneralService funeralService = new FuneralService();
//이런식으로 새로운 객체를 만들어 연결해 주었어야하지만
//스프링부트에서는 @Component를 달아주면  = new FuneralService();를 생략해도 된다.
//대신 이 객체를 연결하려는 장소에서 @Autowired를 달아주어야 한다.
//@Autowired는 이정표 같은 개념
//또한, 이 객체가 service라면 @Service만 달아주고 @Component를 생략한다.
//@Component
@Service
public class FuneralService {

	@Autowired
	private GenFileService genFileService;
	@Autowired
	private FuneralDao funeralDao;

	public Funeral getFuneral(int id) {
		return funeralDao.getFuneral(id);
	}

	public List<Funeral> getFunerals() {
		return funeralDao.getFunerals();
	}

	public ResultData addFuneral(Map<String, Object> param) {
		funeralDao.addFuneral(param);

		int id = Util.getAsInt(param.get("id"), 0);

		return new ResultData("S-1", "성공하였습니다.", "id", id);
	}

	public ResultData deleteFuneral(int id) {
		funeralDao.deleteFuneral(id);

		// 게시물에 달린 첨부파일도 같이 삭제
		// 1. DB에서 삭제
		// 2. 저장소에서 삭제
		genFileService.deleteGenFiles("funeral", id);

		return new ResultData("S-1", "삭제하였습니다.", "id", id);
	}

	public ResultData modifyFuneral(Map<String, Object> param) {
		funeralDao.modifyFuneral(param);

		int id = Util.getAsInt(param.get("id"), 0);

		return new ResultData("S-1", "요청서를 수정하였습니다.", "id", id);
	}

	public Funeral getForPrintFuneral(Integer id) {
		return funeralDao.getForPrintFuneral(id);
	}

	public List<Funeral> getForPrintFunerals() {
		return funeralDao.getForPrintFunerals();
	}

	public Board getBoard(int id) {
		return funeralDao.getBoard(id);
	}

	public List<Funeral> getForPrintFuneralsByMemberId(Map<String, Object> param) {
		return funeralDao.getForPrintFuneralsByMemberId(param);
	}

	public ResultData getClientCanDeleteRd(Funeral funeral, Client client) {
		return getActorCanModifyRd(funeral, client);
	}

	public ResultData getExpertanDeleteRd(Funeral funeral, Expert expert) {
		return getActorCanModifyRd(funeral, expert);
	}

	public ResultData getActorCanModifyRd(Funeral funeral, Object actor) {

		// 1. 의뢰인 본인인 경우
		if (actor instanceof Client) {
			Client client = (Client) actor;
			if (funeral.getClientId() == client.getId()) {
				return new ResultData("S-1", "가능합니다.");
			}
		}
		// 2. 전문가 본인인 경우
		if (actor instanceof Expert) {
			Expert expert = (Expert) actor;
			if (funeral.getExpertId() == expert.getId()) {
				return new ResultData("S-1", "가능합니다.");
			}
		}

		return new ResultData("F-1", "권한이 없습니다.");
	}

}
