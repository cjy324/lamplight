package com.cjy.lamplight.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cjy.lamplight.dto.Board;
import com.cjy.lamplight.dto.Funeral;

/* Mybatis 적용으로 삭제 */
//Mybatis틑 class가 아닌 interface를 인식함

//@Component
//public class FuneralDao {

@Mapper
public interface FuneralDao {
	/* Mybatis 적용으로 기존 내용 삭제 */
	//Mybatis에서 자동으로 만들어 줌
	Funeral getFuneral(@Param("id") int id);
	List<Funeral> getFunerals();
	List<Funeral> getForPrintFunerals();
	void addFuneral(Map<String, Object> param);
	void deleteFuneral(@Param("id") int id);
	void modifyFuneral(Map<String, Object> param);
	Funeral getForPrintFuneral(@Param("id") int id);
	Board getBoard(@Param("id") int id);

	List<Funeral> getForPrintFuneralsByMemberId(Map<String, Object> param);
}
