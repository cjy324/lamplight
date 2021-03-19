package com.cjy.lamplight.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartRequest;

import com.cjy.lamplight.dto.Member;
import com.cjy.lamplight.dto.Order;
import com.cjy.lamplight.dto.ResultData;
import com.cjy.lamplight.service.OrderService;
import com.cjy.lamplight.util.Util;

@Controller
public class UsrOrderController extends BaseController {

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

	@RequestMapping("/usr/order/list")
	public String showList(HttpServletRequest req) {

		List<Order> orders = orderService.getForPrintOrders();

		req.setAttribute("orders", orders);
		
		return "/usr/order/list";
	}

	@RequestMapping("/usr/order/add")
	public String showAdd(@RequestParam Map<String, Object> param, HttpServletRequest req) {
		return "usr/order/add";
	}

	@PostMapping("/usr/order/doAdd")
	public String doAdd(@RequestParam Map<String, Object> param, HttpServletRequest req, MultipartRequest multipartRequest, int directorId) {

		int clientId = (int)req.getAttribute("loginedMemberId");
		System.out.println(clientId);
		
		if(Util.getAsInt(directorId, 0) == 0) {
			return msgAndBack(req, "directorId를 입력해주세요.");
		}

		if (param.get("title") == null) {
			return msgAndBack(req, "title을 입력해주세요.");
		}
		
		if (param.get("body") == null) {
			return msgAndBack(req, "body를 입력해주세요.");
		}
		
		int option1qty = Util.getAsInt(param.get("option1qty"), 0);
		int option2qty = Util.getAsInt(param.get("option2qty"), 0);
		int option3qty = Util.getAsInt(param.get("option3qty"), 0);
		int option4qty = Util.getAsInt(param.get("option4qty"), 0);
		int option5qty = Util.getAsInt(param.get("option5qty"), 0);
		
		param.put("option1qty", option1qty);
		param.put("option2qty", option2qty);
		param.put("option3qty", option3qty);
		param.put("option4qty", option4qty);
		param.put("option5qty", option5qty);
		param.put("clientId", clientId);
		param.put("directorId", directorId);
		
		ResultData addOrderRd = orderService.addOrder(param);
		
		// addOrderRd map의 body에서 key값이 id인 것을 가져와라
		int newOrderId = (int) addOrderRd.getBody().get("id");
		
		
		/* 이미 ajax상에서 처리하므로 더이상 필요 없음
		//MultipartRequest : 첨부파일 기능 관련 요청
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap(); //MultipartRequest로 들어온 map 정보를 가져오기
				
		
		//fileMap.keySet() : file__order__0__common__attachment__1
		for (String fileInputName : fileMap.keySet()) {
			//fileInputName : file__order__0__common__attachment__1
			MultipartFile multipartFile = fileMap.get(fileInputName);
			
			if(multipartFile.isEmpty() == false) {
				//저장할 파일관련 정보를 넘김
				genFileService.save(multipartFile, newOrderId);
			}
			
		}
		*/
		return msgAndReplace(req, newOrderId + "번 게시물이 생성되었습니다.", "../order/list");
	}

	@PostMapping("/usr/order/doDelete")
	@ResponseBody
	public ResultData doDelete(Integer id, HttpServletRequest req) {
		// int 기본타입 -> null이 들어갈 수 없음
		// Integer 객체타입 -> null이 들어갈 수 있음
		//int loginedMemberId = (int)req.getAttribute("loginedMemberId");
		Member loginedMember = (Member) req.getAttribute("loginedMember");
		
		if (id == null) {
			return new ResultData("F-1", "id를 입력해주세요.");
		}

		Order order = orderService.getOrder(id);

		if (order == null) {
			return new ResultData("F-1", "해당 게시물은 존재하지 않습니다.");
		}
		
		ResultData actorCanDeleteRd = orderService.getActorCanDeleteRd(order, loginedMember);

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
		
		//int loginedMemberId = (int)req.getAttribute("loginedMemberId");
		Member loginedMember = (Member) req.getAttribute("loginedMember");
		
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

		ResultData actorCanModifyRd = orderService.getActorCanModifyRd(order, loginedMember);

		if (actorCanModifyRd.isFail()) {
			return actorCanModifyRd;
		}
		
		return orderService.modifyOrder(param);
	}
	
	@PostMapping("/usr/order/doAddReply")
	@ResponseBody
	public ResultData doAddReply(@RequestParam Map<String, Object> param, HttpServletRequest req) {
		int loginedMemberId = (int) req.getAttribute("loginedMemberId");

		if (param.get("body") == null) {
			return new ResultData("F-1", "body를 입력해주세요.");
		}

		if (param.get("orderId") == null) {
			return new ResultData("F-1", "orderId를 입력해주세요.");
		}

		param.put("memberId", loginedMemberId);

		return orderService.addReply(param);
	}
}
