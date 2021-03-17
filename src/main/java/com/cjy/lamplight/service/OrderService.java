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
import com.cjy.lamplight.dto.Client;
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
	private ClientService clientService;

	public Order getOrder(int id) {
		return orderDao.getOrder(id);
	}

	public List<Order> getOrders(String searchKeywordType, String searchKeyword) {
		return orderDao.getOrders(searchKeywordType, searchKeyword);
	}

	public ResultData addOrder(Map<String, Object> param) {
		orderDao.addOrder(param);

		int id = Util.getAsInt(param.get("id"), 0);
		
		genFileService.changeInputFileRelIds(param, id);
		
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

	public ResultData getActorCanModifyRd(Order order, Client actor) {
		//1. 작성인 본인인 경우
		if (order.getClientId() == actor.getId()) {
			return new ResultData("S-1", "가능합니다.");
		}
		//2. 관리자인 경우
		if (clientService.isAdmin(actor)) {
			return new ResultData("S-2", "가능합니다.");
		}
		//3. 작성인, 관리자 둘다 아닌 경우
		return new ResultData("F-1", "권한이 없습니다.");
	}

	public ResultData getActorCanDeleteRd(Order order, Client actor) {
		return getActorCanModifyRd(order, actor);
	}

	public Order getForPrintOrder(Integer id) {
		return orderDao.getForPrintOrder(id);
	}

	public List<Order> getForPrintOrders(int boardId, String searchKeywordType, String searchKeyword, int page, int itemsInAPage) {
	
			int limitStart = (page - 1) * itemsInAPage;
			int limitTake = itemsInAPage;
			
			/* 게시물 리스트에서 첨부 이미지 가져오는 쿼리를 게시물 마다 1번씩 실행하지 않도록 로직 변경 */
			//1. order 리스트를 가져온다
			List<Order> orders = orderDao.getForPrintOrders(boardId, searchKeywordType, searchKeyword, limitStart, limitTake);
			//2. 가져온 order리스트에서 각 order들의 id만 가져와 Integer 리스트를 만든다
			List<Integer> orderIds = orders.stream().map(order -> order.getId()).collect(Collectors.toList());
			//3. Integer 리스트에 들어있는 id에 맞는(관련된) genFile들을 모두 map형태로 가져온다
			Map<Integer, Map<String, GenFile>> filesMap = genFileService.getFilesMapKeyRelIdAndFileNo("order", orderIds, "common", "attachment");

			for (Order order : orders) {
				Map<String, GenFile> mapByFileNo = filesMap.get(order.getId());

				if (mapByFileNo != null) {
					order.getExtraNotNull().put("file__common__attachment", mapByFileNo);
				}
			}

			return orders;
	}

	public Board getBoard(int id) {
		return orderDao.getBoard(id);
	}

	public ResultData addReply(Map<String, Object> param) {
		orderDao.addReply(param);

		int id = Util.getAsInt(param.get("id"), 0);

		return new ResultData("S-1", "성공하였습니다.", "id", id);
	}

}
