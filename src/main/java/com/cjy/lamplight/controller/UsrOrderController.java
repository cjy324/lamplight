package com.cjy.lamplight.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cjy.lamplight.dto.Order;
import com.cjy.lamplight.dto.Board;
import com.cjy.lamplight.dto.Client;
import com.cjy.lamplight.dto.ResultData;
import com.cjy.lamplight.service.OrderService;
import com.cjy.lamplight.util.Util;

@Controller
public class UsrOrderController {

	@Autowired
	private OrderService orderService;

	@GetMapping("/usr/order/detail")
	@ResponseBody
	// 스프링부트: 알아서 json형태로 바꿔 출력값을 리턴해준다.
	public ResultData showDetail(Integer id) {
		if (id == null) {
			return new ResultData("F-1", "id를 입력해주세요.");
		}

		Order order = orderService.getForPrintOrder(id);

		if (order == null) {
			return new ResultData("F-2", "존재하지 않는 게시물번호 입니다.");
		}
	
		return new ResultData("S-1", "성공", "order", order);
	}

	@GetMapping("/usr/order/list")
	@ResponseBody
	public ResultData showList(@RequestParam(defaultValue = "1") int boardId, String searchKeywordType, String searchKeyword, @RequestParam(defaultValue = "1") int page) {
		// @RequestParam(defaultValue = "1") int page : page 파라미터의 값이 없으면 디폴트로 1이다.
		
		Board board = orderService.getBoard(boardId);

		if ( board == null ) {
			return new ResultData("F-1", "존재하지 않는 게시판 입니다.");
		}
		
		if (searchKeywordType != null) {
			searchKeywordType = searchKeywordType.trim();
		}

		if (searchKeywordType == null || searchKeywordType.length() == 0) {
			searchKeywordType = "titleAndBody";
		}

		if (searchKeyword != null && searchKeyword.length() == 0) {
			searchKeyword = null;
		}

		if (searchKeyword != null) {
			searchKeyword = searchKeyword.trim();
		}

		if ( searchKeyword == null ) {
			searchKeywordType = null;
		}
		
		int itemsInAPage = 10;
		
		List<Order> orders = orderService.getForPrintOrders(boardId, searchKeywordType, searchKeyword, page, itemsInAPage);
		

		return new ResultData("S-1", "성공", "orders", orders);
	}

	@PostMapping("/usr/order/doAdd")
	@ResponseBody
	public ResultData doAdd(@RequestParam Map<String, Object> param, HttpServletRequest req) {
		//HttpSession session을 HttpServletRequest req로 교체, 인터셉터에서 session 정보를 Request에 담음으로 
		//session을 가져올 필요 없이 req로 값을 받으면 됨
		
		int loginedClientId = (int)req.getAttribute("loginedClientId");

		if (param.get("title") == null) {
			return new ResultData("F-1", "title을 입력해주세요.");
		}
		if (param.get("body") == null) {
			return new ResultData("F-1", "body를 입력해주세요.");
		}

		param.put("clientId", loginedClientId);
		
		return orderService.addOrder(param);
	}

	@PostMapping("/usr/order/doDelete")
	@ResponseBody
	public ResultData doDelete(Integer id, HttpServletRequest req) {
		// int 기본타입 -> null이 들어갈 수 없음
		// Integer 객체타입 -> null이 들어갈 수 있음
		//int loginedClientId = (int)req.getAttribute("loginedClientId");
		Client loginedClient = (Client) req.getAttribute("loginedClient");
		
		if (id == null) {
			return new ResultData("F-1", "id를 입력해주세요.");
		}

		Order order = orderService.getOrder(id);

		if (order == null) {
			return new ResultData("F-1", "해당 게시물은 존재하지 않습니다.");
		}
		
		ResultData actorCanDeleteRd = orderService.getActorCanDeleteRd(order, loginedClient);

		if (actorCanDeleteRd.isFail()) {
			return actorCanDeleteRd;
		}

		return orderService.deleteOrder(id);
	}

	@PostMapping("/usr/order/doModify")
	@ResponseBody
	public ResultData doModify(@RequestParam Map<String, Object> param, HttpServletRequest req) {
		// int 기본타입 -> null이 들어갈 수 없음
		// Integer 객체타입 -> null이 들어갈 수 있음
		
		//int loginedClientId = (int)req.getAttribute("loginedClientId");
		Client loginedClient = (Client) req.getAttribute("loginedClient");
		
		int id = Util.getAsInt(param.get("id"), 0);

		if (id == 0) {
			return new ResultData("F-1", "id를 입력해주세요.");
		}

		if (Util.isEmpty(param.get("title"))) {
			return new ResultData("F-1", "title을 입력해주세요.");
		}
		if (Util.isEmpty(param.get("body"))) {
			return new ResultData("F-1", "body를 입력해주세요.");
		}

		Order order = orderService.getOrder(id);

		if (order == null) {
			return new ResultData("F-1", "해당 게시물은 존재하지 않습니다.", "id", id);
		}

		ResultData actorCanModifyRd = orderService.getActorCanModifyRd(order, loginedClient);

		if (actorCanModifyRd.isFail()) {
			return actorCanModifyRd;
		}
		
		return orderService.modifyOrder(param);
	}
	
	@PostMapping("/usr/order/doAddReply")
	@ResponseBody
	public ResultData doAddReply(@RequestParam Map<String, Object> param, HttpServletRequest req) {
		int loginedClientId = (int) req.getAttribute("loginedClientId");

		if (param.get("body") == null) {
			return new ResultData("F-1", "body를 입력해주세요.");
		}

		if (param.get("orderId") == null) {
			return new ResultData("F-1", "orderId를 입력해주세요.");
		}

		param.put("clientId", loginedClientId);

		return orderService.addReply(param);
	}
}
