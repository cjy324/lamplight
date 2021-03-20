package com.cjy.lamplight.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cjy.lamplight.dao.OrderDao;
import com.cjy.lamplight.dto.Order;
import com.cjy.lamplight.dto.Board;
import com.cjy.lamplight.dto.GenFile;
import com.cjy.lamplight.dto.Member;
import com.cjy.lamplight.dto.ResultData;
import com.cjy.lamplight.util.Util;

//일반 자바에서는 다른 곳에서 OrderService를 사용하려면
//OrderService orderService = new OrderService();
//이런식으로 새로운 객체를 만들어 연결해 주었어야하지만
//스프링부트에서는 @Component를 달아주면  = new OrderService();를 생략해도 된다.
//대신 이 객체를 연결하려는 장소에서 @Autowired를 달아주어야 한다.
//@Autowired는 이정표 같은 개념
//또한, 이 객체가 service라면 @Service만 달아주고 @Component를 생략한다.
//@Component
@Service
public class OrderService {

	@Autowired
	private GenFileService genFileService;
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private MemberService memberService;

	public Order getOrder(int id) {
		return orderDao.getOrder(id);
	}

	public List<Order> getOrders() {
		return orderDao.getOrders();
	}

	public ResultData addOrder(Map<String, Object> param) {
		orderDao.addOrder(param);

		int id = Util.getAsInt(param.get("id"), 0);
		
		return new ResultData("S-1", "성공하였습니다.", "id", id);
	}

	public ResultData deleteOrder(int id) {
		orderDao.deleteOrder(id);
		
		//게시물에 달린 첨부파일도 같이 삭제
		//1. DB에서 삭제
		//2. 저장소에서 삭제
		genFileService.deleteGenFiles("order", id);

		return new ResultData("S-1", "삭제하였습니다.", "id", id);
	}

	public ResultData modifyOrder(Map<String, Object> param) {
		orderDao.modifyOrder(param);

		int id = Util.getAsInt(param.get("id"), 0);

		return new ResultData("S-1", "게시물을 수정하였습니다.", "id", id);
	}

	public ResultData getActorCanModifyRd(Order order, Member actor) {
		//1. 작성인 본인인 경우
		if (order.getClientId() == actor.getId()) {
			return new ResultData("S-1", "가능합니다.");
		}
		//2. 관리자인 경우
		if (memberService.isAdmin(actor)) {
			return new ResultData("S-2", "가능합니다.");
		}
		//3. 작성인, 관리자 둘다 아닌 경우
		return new ResultData("F-1", "권한이 없습니다.");
	}

	public ResultData getActorCanDeleteRd(Order order, Member actor) {
		return getActorCanModifyRd(order, actor);
	}

	public Order getForPrintOrder(Integer id) {
		return orderDao.getForPrintOrder(id);
	}

	public List<Order> getForPrintOrders() {
		return orderDao.getForPrintOrders();
	}

	public Board getBoard(int id) {
		return orderDao.getBoard(id);
	}

	public ResultData addReply(Map<String, Object> param) {
		orderDao.addReply(param);

		int id = Util.getAsInt(param.get("id"), 0);

		return new ResultData("S-1", "성공하였습니다.", "id", id);
	}

	public List<Order> getForPrintOrdersByMemberId(int memberId) {
		return orderDao.getForPrintOrdersByMemberId(memberId);
	}

}
