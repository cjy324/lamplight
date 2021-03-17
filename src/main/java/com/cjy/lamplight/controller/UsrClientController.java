package com.cjy.lamplight.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cjy.lamplight.dto.Client;
import com.cjy.lamplight.dto.ResultData;
import com.cjy.lamplight.service.ClientService;
import com.cjy.lamplight.util.Util;

@Controller
public class UsrClientController {

	@Autowired
	private ClientService clientService;
	
	@GetMapping("/usr/client/list")
	@ResponseBody
	public ResultData showList(HttpServletRequest req) {

		List<Client> clients = clientService.getClients();

		req.setAttribute("clients", clients);	

		return new ResultData("S-1", "성공", "clients", clients);
	}

	@PostMapping("/usr/client/doJoin")
	@ResponseBody
	public ResultData doJoin(@RequestParam Map<String, Object> param) {

		if (param.get("loginId") == null) {
			return new ResultData("F-1", "loginId를 입력해주세요.");
		}

		Client existingClient = clientService.getClientByLoginId((String) param.get("loginId"));

		if (existingClient != null) {
			return new ResultData("F-2", String.format("%s (은)는 이미 사용중인 로그인아이디 입니다.", param.get("loginId")));
		}

		if (param.get("loginPw") == null) {
			return new ResultData("F-1", "loginPw를 입력해주세요.");
		}
		if (param.get("name") == null) {
			return new ResultData("F-1", "이름을 입력해주세요.");
		}
		if (param.get("nickname") == null) {
			return new ResultData("F-1", "nickname을 입력해주세요.");
		}
		if (param.get("cellphoneNo") == null) {
			return new ResultData("F-1", "연락처를 입력해주세요.");
		}
		if (param.get("email") == null) {
			return new ResultData("F-1", "email을 입력해주세요.");
		}

		return clientService.join(param);
	}

	@GetMapping("/usr/client/clientByAuthKey")
	@ResponseBody
	public ResultData showClientByAuthKey(String authKey) {
		if (authKey == null) {
			return new ResultData("F-1", "authKey를 입력해주세요.");
		}

		Client existingClient = clientService.getForPrintClientByAuthKey(authKey);

		if (existingClient == null) {
			return new ResultData("F-2", "유효하지 않은 authKey입니다.");
		}
		return new ResultData("S-1", String.format("유효한 회원입니다."), "client", existingClient);
	}

	@PostMapping("/usr/client/authKey")
	@ResponseBody
	public ResultData showAuthKey(String loginId, String loginPw) {
		if (loginId == null) {
			return new ResultData("F-1", "loginId를 입력해주세요.");
		}

		Client existingClient = clientService.getForPrintClientByLoginId(loginId);

		if (existingClient == null) {
			return new ResultData("F-2", "존재하지 않는 로그인아이디 입니다.", "loginId", loginId);
		}

		if (loginPw == null) {
			return new ResultData("F-1", "loginPw를 입력해주세요.");
		}

		if (existingClient.getLoginPw().equals(loginPw) == false) {
			return new ResultData("F-3", "비밀번호가 일치하지 않습니다.");
		}

		return new ResultData("S-1", String.format("%s님 환영합니다.", existingClient.getNickname()), "authKey",
				existingClient.getAuthKey(), "client", existingClient);
	}

	@PostMapping("/usr/client/doLogin")
	@ResponseBody
	public ResultData doLogin(String loginId, String loginPw, HttpSession session) {
		// HttpSession session
		// servlet에서와는 달리 스프링에선 session을 바로 요청해서 가져올 수 있다.
		// ex) servlet에서는 requst를 통해 session을 요청하고 다시 HttpSession로 session 값을 가져왔었다.

		if (loginId == null) {
			return new ResultData("F-1", "loginId를 입력해주세요.");
		}

		Client existingClient = clientService.getClientByLoginId(loginId);

		if (existingClient == null) {
			return new ResultData("F-2", "존재하지 않는 로그인아이디 입니다.", "loginId", loginId);
		}

		if (loginPw == null) {
			return new ResultData("F-1", "loginPw를 입력해주세요.");
		}

		if (existingClient.getLoginPw().equals(loginPw) == false) {
			return new ResultData("F-3", "비밀번호가 일치하지 않습니다.");
		}

		// 세션에 로그인 회원 id 등록
		session.setAttribute("loginedClientId", existingClient.getId());

		return new ResultData("S-1", String.format("%s님 환영합니다.", existingClient.getNickname()));
	}
	
	@RequestMapping("/usr/client/login")
	// @ResponseBody: @ResponseBody를 안하면 /WEB-INF/jsp/adm/client/login.jsp를 찾는다.
	public String showLogin() {
		return "usr/client/login";
	}

	@RequestMapping("/usr/client/doLogin")
	//@ResponseBody
	public String doLogin(String loginId, String loginPw, String redirectUrl, HttpSession session) {
		// HttpSession session
		// servlet에서와는 달리 스프링에선 session을 바로 요청해서 가져올 수 있다.
		// ex) servlet에서는 requst를 통해 session을 요청하고 다시 HttpSession로 session 값을 가져왔었다.

		if (loginId == null) {
			// return new ResultData("F-1", "loginId를 입력해주세요.");
			return Util.msgAndBack("loginId를 입력해주세요.");
		}

		Client existingClient = clientService.getClientByLoginId(loginId);

		if (existingClient == null) {
			// return new ResultData("F-2", "존재하지 않는 로그인아이디 입니다.", "loginId", loginId);
			return Util.msgAndBack("존재하지 않는 로그인아이디 입니다.");
		}

		if (loginPw == null) {
			// return new ResultData("F-1", "loginPw를 입력해주세요.");
			return Util.msgAndBack("loginPw를 입력해주세요.");
		}

		if (existingClient.getLoginPw().equals(loginPw) == false) {
			// return new ResultData("F-3", "비밀번호가 일치하지 않습니다.");\
			return Util.msgAndBack("비밀번호가 일치하지 않습니다.");
		}

		// 세션에 로그인 회원 id 등록
		session.setAttribute("loginedClientId", existingClient.getId());

		String msg = String.format("%s님 환영합니다.", existingClient.getNickname());

		redirectUrl = Util.ifEmpty(redirectUrl, "../home/main");

		// return new ResultData("S-1", String.format("%s님 환영합니다.",
		// existingClient.getNickname()));
		return Util.msgAndReplace(msg, redirectUrl);
	}

	@PostMapping("/usr/client/doLogout")
	@ResponseBody
	public ResultData doLogout(HttpSession session) {

		session.removeAttribute("loginedClientId");

		return new ResultData("S-1", "로그아웃 되었습니다.");
	}

	@PostMapping("/usr/client/doModify")
	@ResponseBody
	public ResultData doModify(@RequestParam Map<String, Object> param, HttpServletRequest req) {

		if (param.isEmpty()) {
			return new ResultData("F-2", "수정할 회원정보를 입력해주세요.");
		}

		int loginedClientId = (int) req.getAttribute("loginedClientId");
		param.put("id", loginedClientId);

		return clientService.modifyClient(param);
	}

}
