package com.cjy.lamplight.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cjy.lamplight.dao.ClientDao;
import com.cjy.lamplight.dto.Client;
import com.cjy.lamplight.dto.GenFile;
import com.cjy.lamplight.dto.ResultData;
import com.cjy.lamplight.util.Util;

@Service
public class ClientService {
	
	@Autowired
	ClientDao clientDao;
	
	@Autowired
	GenFileService genFileService;

	public ResultData join(Map<String, Object> param) {
		clientDao.join(param);

		int id = Util.getAsInt(param.get("id"), 0);
		
		genFileService.changeInputFileRelIds(param, id);
		
		return new ResultData("S-1", param.get("nickname") + "님, 환영합니다.", "id", id);
	}
	
	public Client getClient(int id) {
		return clientDao.getClient(id);
	}

	public Client getClientByLoginId(String loginId) {
		return clientDao.getClientByLoginId(loginId);
	}

	public ResultData modifyClient(Map<String, Object> param) {
		clientDao.modifyClient(param);
		
		return new ResultData("S-1", "회원정보가 수정되었습니다.");
	}

	public boolean isAdmin(Client actor) {
		return actor.getAuthLevel() == 7;
	}

	public Client getClientByAuthKey(String authKey) {
		return clientDao.getClientByAuthKey(authKey);
	}

	public List<Client> getForPrintClients(String searchKeywordType, String searchKeyword, int page, int itemsInAPage, Map<String, Object> param) {
		int limitStart = (page - 1) * itemsInAPage;
		int limitTake = itemsInAPage;

		param.put("searchKeywordType", searchKeywordType);
		param.put("searchKeyword", searchKeyword);
		param.put("limitStart", limitStart);
		param.put("limitTake", limitTake);

		return clientDao.getForPrintClients(param);
	}

	public static String getAuthLevelName(Client client) {
		switch (client.getAuthLevel()) {
		case 7:
			return "관리자";
		case 3:
			return "일반";
		default:
			return "유형 정보 없음";
		}
	}

	public static String getAuthLevelNameColor(Client client) {
		switch (client.getAuthLevel()) {
		case 7:
			return "red";
		case 3:
			return "gray";
		default:
			return "";
		}
	}

	public Client getForPrintClient(int id) {
		return clientDao.getForPrintClient(id);
	}


	public Client getForPrintClientByAuthKey(String authKey) {
		Client client = clientDao.getClientByAuthKey(authKey);
		//기본 멤버에서 추가정보를 업데이트해서 리턴
		updateForPrint(client);
		
		return client;
	}


	public Client getForPrintClientByLoginId(String loginId) {
		Client client = clientDao.getClientByLoginId(loginId);
		//기본 멤버에서 추가정보를 업데이트해서 리턴
		updateForPrint(client);
		
		return client;
	}
	
	//기본멤버 정보에 추가 정보를 업데이트해서 리턴
	private void updateForPrint(Client client) {
		//멤버의 섬네일 이미지 가져오기
		GenFile genFile = genFileService.getGenFile("client", client.getId(), "common", "attachment", 1);
		
		//만약, 멤버의 섬네일 이미지가 있으면 extra__thumbImg 업데이트
		if(genFile != null) {
			String imgUrl = genFile.getForPrintUrl();
			client.setExtra__thumbImg(imgUrl);
		}
		
	}

	public List<Client> getClients() {
		return clientDao.getClients();
	}
	
}
