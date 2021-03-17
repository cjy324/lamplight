package com.cjy.lamplight.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cjy.lamplight.dto.Board;
import com.cjy.lamplight.dto.Order;
import com.cjy.lamplight.dto.ResultData;

/* Mybatis 적용으로 삭제 */
//Mybatis틑 class가 아닌 interface를 인식함

//@Component
//public class OrderDao {

@Mapper
public interface OrderDao {
	/* Mybatis 적용으로 기존 내용 삭제 */
	//Mybatis에서 자동으로 만들어 줌
	Order getOrder(@Param("id") int id);
	List<Order> getOrders(@Param("searchKeywordType") String searchKeywordType, @Param("searchKeyword") String searchKeyword);
	void addOrder(Map<String, Object> param);
	void deleteOrder(@Param("id") int id);
	void modifyOrder(Map<String, Object> param);
	Order getForPrintOrder(@Param("id") int id);
	List<Order> getForPrintOrders(@Param("boardId") int boardId, @Param("searchKeywordType") String searchKeywordType, @Param("searchKeyword") String searchKeyword, @Param("limitStart") int limitStart,
			@Param("limitTake") int limitTake);
	Board getBoard(@Param("id") int id);
	
	ResultData addReply(Map<String, Object> param);
}
