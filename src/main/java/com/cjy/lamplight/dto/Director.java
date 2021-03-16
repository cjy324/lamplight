package com.cjy.lamplight.dto;


import com.cjy.lamplight.service.DirectorService;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;


@Data
public class Director {
	private int id;
	private String regDate;
	private String updateDate;
	private String loginId;
	@JsonIgnore
	//@JsonIgnore: json으로 보이지 않게 할 수 있다.
	private String loginPw;
	@JsonIgnore
	private int authLevel;
	@JsonIgnore
	private String authKey;
	private String name;
	private String nickname;
	private String cellphoneNo;
	private String email;
	private String address_state;
	private String address_city;
	private String address_street;
	
	private String extra__thumbImg;
	
	public String getAuthLevelName() {
		return DirectorService.getAuthLevelName(this);
	}
	public String getAuthLevelNameColor() {
		return DirectorService.getAuthLevelNameColor(this);
	}
}