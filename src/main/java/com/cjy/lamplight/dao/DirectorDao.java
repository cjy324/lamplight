package com.cjy.lamplight.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cjy.lamplight.dto.Director;

@Mapper
public interface DirectorDao {

	List<Director> getForPrintDirectors(Map<String, Object> param);

	void join(Map<String, Object> param);

	Director getDirector(@Param("id") int id);
	
	Director getForPrintDirector(@Param("id") int id);

	Director getDirectorByLoginId(@Param("loginId") String loginId);

	void modifyDirector(Map<String, Object> param);

	Director getDirectorByAuthKey(@Param("authKey") String authKey);

	List<Director> getDirectors();



}
