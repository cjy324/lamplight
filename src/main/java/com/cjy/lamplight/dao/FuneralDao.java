package com.cjy.lamplight.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cjy.lamplight.dto.Funeral;
import com.cjy.lamplight.dto.ResultData;

@Mapper
public interface FuneralDao {

	List<Funeral> getForPrintFuneralsByMemberId(Map<String, Object> param);

	List<Funeral> getForPrintFunerals();
	
	ResultData asstApplyForFuneral(Map<String, Object> param);

	List<Integer> getAssistantIdByFuneralId(@Param("funeralId") int funeralId);

	void addFuneral(Map<String, Object> param);

}
