package com.cjy.lamplight.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cjy.lamplight.dto.Order;
import com.cjy.lamplight.dto.Member;
import com.cjy.lamplight.dto.Reply;
import com.cjy.lamplight.dto.ResultData;
import com.cjy.lamplight.service.OrderService;
import com.cjy.lamplight.service.ReplyService;

@Controller
public class UsrReplyController {
	@Autowired
	private ReplyService replyService;
	@Autowired
	private OrderService orderService;

	@GetMapping("/usr/reply/list")
	@ResponseBody
	public ResultData showList(String relTypeCode, Integer relId) {

		if (relTypeCode == null) {
			return new ResultData("F-1", "relTypeCode를 입력해주세요.");
		}
		
		if (relId == null) {
			return new ResultData("F-1", "relId를 입력해주세요.");
		}

		if ( relTypeCode.equals("order") ) {
			Order order = orderService.getOrder(relId);

			if ( order == null ) {
				return new ResultData("F-1", "존재하지 않는 게시물 입니다.");
			}
		}

		List<Reply> replies = replyService.getForPrintReplies(relTypeCode, relId);

		return new ResultData("S-1", "성공", "replies", replies);
	}
	
	@PostMapping("/usr/reply/doDelete")
	@ResponseBody
	public ResultData doDelete(Integer id, HttpServletRequest req) {
		//int loginedMemberId = (int) req.getAttribute("loginedMemberId");
		Member loginedMember = (Member) req.getAttribute("loginedMember");

		if (id == null) {
			return new ResultData("F-1", "id를 입력해주세요.");
		}

		Reply reply = replyService.getReply(id);

		if (reply == null) {
			return new ResultData("F-1", "해당 댓글은 존재하지 않습니다.");
		}

		ResultData actorCanDeleteRd = replyService.getActorCanDeleteRd(reply, loginedMember);

		if (actorCanDeleteRd.isFail()) {
			return actorCanDeleteRd;
		}

		return replyService.deleteReply(id);
	}
	
	@PostMapping("/usr/reply/doModify")
	@ResponseBody
	public ResultData doModify(Integer id, String body, HttpServletRequest req) {
		//int loginedMemberId = (int)req.getAttribute("loginedMemberId");
		Member loginedMember = (Member) req.getAttribute("loginedMember");
		
		if (id == null) {
			return new ResultData("F-1", "id를 입력해주세요.");
		}

		if (body == null) {
			return new ResultData("F-1", "body를 입력해주세요.");
		}

		Reply reply = replyService.getReply(id);

		if (reply == null) {
			return new ResultData("F-1", "해당 댓글은 존재하지 않습니다.");
		}

		ResultData actorCanModifyRd = replyService.getActorCanModifyRd(reply, loginedMember);

		if (actorCanModifyRd.isFail()) {
			return actorCanModifyRd;
		}

		return replyService.modifyReply(id, body);
	}
}
