package com.cjy.lamplight.dto;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class Order {

	private int id;
	private String regDate;
	private String updateDate;
	private String option1;
	private int option1qty;
	private String option2;
	private int option2qty;
	private String option3;
	private int option3qty;
	private String option4;
	private int option4qty;
	private String option5;
	private int option5qty;
	private String title;
	private String funeralHome;
	private String body;
	private int directorId;
	private int clientId;
	private int stepLevel;

	private String extra__member;

	private Map<String, Object> extra;

	public Map<String, Object> getExtraNotNull() {
		// 만약에 추가 정보가 없으면 새로운 Map 객체 생성 후 리턴
		if (extra == null) {
			extra = new HashMap<String, Object>();
		}

		return extra;
	}

	public Order(int id, String regDate, String updateDate, String option1, int option1qty, String option2,
			int option2qty, String option3, int option3qty, String option4, int option4qty, String option5,
			int option5qty, String title, String funeralHome, String body, int directorId, int clientId, int stepLevel) {

		this.id = id;
		this.regDate = regDate;
		this.updateDate = updateDate;
		this.option1 = option1;
		this.option1qty = option1qty;
		this.option2 = option2;
		this.option2qty = option2qty;
		this.option3 = option3;
		this.option3qty = option3qty;
		this.option4 = option4;
		this.option4qty = option4qty;
		this.option5 = option5;
		this.option5qty = option5qty;
		this.title = title;
		this.funeralHome = funeralHome;
		this.body = body;
		this.directorId = directorId;
		this.clientId = clientId;
		this.stepLevel = stepLevel;

	}

}
