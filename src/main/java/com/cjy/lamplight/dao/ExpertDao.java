package com.cjy.lamplight.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cjy.lamplight.dto.Expert;

@Mapper
public interface ExpertDao {

	List<Expert> getForPrintExperts(Map<String, Object> param);

	void join(Map<String, Object> param);

	Expert getExpert(@Param("id") int id);
	
	Expert getForPrintExpert(@Param("id") int id);

	Expert getExpertByLoginId(@Param("loginId") String loginId);

	void modifyExpert(Map<String, Object> param);

	Expert getExpertByAuthKey(@Param("authKey") String authKey);

	List<Expert> getExperts();

	List<Expert> getForPrintExpertsByRegion(@Param("region") String region);


}
