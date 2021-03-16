package com.cjy.lamplight.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cjy.lamplight.dto.Director;
import com.cjy.lamplight.dto.ResultData;
import com.cjy.lamplight.service.DirectorService;

@Controller
public class UsrDirectorController {

	@Autowired
	private DirectorService directorService;
	
	@GetMapping("/usr/director/list")
	@ResponseBody
	public ResultData showList(HttpServletRequest req) {

		List<Director> directors = directorService.getDirectors();

		req.setAttribute("directors", directors);	

		return new ResultData("S-1", "성공", "directors", directors);
	}

	@PostMapping("/usr/director/doJoin")
	@ResponseBody
	public ResultData doJoin(@RequestParam Map<String, Object> param) {

		if (param.get("loginId") == null) {
			return new ResultData("F-1", "loginId를 입력해주세요.");
		}

		Director existingDirector = directorService.getDirectorByLoginId((String) param.get("loginId"));

		if (existingDirector != null) {
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

		return directorService.join(param);
	}

	@GetMapping("/usr/director/directorByAuthKey")
	@ResponseBody
	public ResultData showDirectorByAuthKey(String authKey) {
		if (authKey == null) {
			return new ResultData("F-1", "authKey를 입력해주세요.");
		}

		Director existingDirector = directorService.getForPrintDirectorByAuthKey(authKey);

		if (existingDirector == null) {
			return new ResultData("F-2", "유효하지 않은 authKey입니다.");
		}
		return new ResultData("S-1", String.format("유효한 회원입니다."), "director", existingDirector);
	}

	@PostMapping("/usr/director/authKey")
	@ResponseBody
	public ResultData showAuthKey(String loginId, String loginPw) {
		if (loginId == null) {
			return new ResultData("F-1", "loginId를 입력해주세요.");
		}

		Director existingDirector = directorService.getForPrintDirectorByLoginId(loginId);

		if (existingDirector == null) {
			return new ResultData("F-2", "존재하지 않는 로그인아이디 입니다.", "loginId", loginId);
		}

		if (loginPw == null) {
			return new ResultData("F-1", "loginPw를 입력해주세요.");
		}

		if (existingDirector.getLoginPw().equals(loginPw) == false) {
			return new ResultData("F-3", "비밀번호가 일치하지 않습니다.");
		}

		return new ResultData("S-1", String.format("%s님 환영합니다.", existingDirector.getNickname()), "authKey",
				existingDirector.getAuthKey(), "director", existingDirector);
	}

	@PostMapping("/usr/director/doLogin")
	@ResponseBody
	public ResultData doLogin(String loginId, String loginPw, HttpSession session) {
		// HttpSession session
		// servlet에서와는 달리 스프링에선 session을 바로 요청해서 가져올 수 있다.
		// ex) servlet에서는 requst를 통해 session을 요청하고 다시 HttpSession로 session 값을 가져왔었다.

		if (loginId == null) {
			return new ResultData("F-1", "loginId를 입력해주세요.");
		}

		Director existingDirector = directorService.getDirectorByLoginId(loginId);

		if (existingDirector == null) {
			return new ResultData("F-2", "존재하지 않는 로그인아이디 입니다.", "loginId", loginId);
		}

		if (loginPw == null) {
			return new ResultData("F-1", "loginPw를 입력해주세요.");
		}

		if (existingDirector.getLoginPw().equals(loginPw) == false) {
			return new ResultData("F-3", "비밀번호가 일치하지 않습니다.");
		}

		// 세션에 로그인 회원 id 등록
		session.setAttribute("loginedDirectorId", existingDirector.getId());

		return new ResultData("S-1", String.format("%s님 환영합니다.", existingDirector.getNickname()));
	}

	@PostMapping("/usr/director/doLogout")
	@ResponseBody
	public ResultData doLogout(HttpSession session) {

		session.removeAttribute("loginedDirectorId");

		return new ResultData("S-1", "로그아웃 되었습니다.");
	}

	@PostMapping("/usr/director/doModify")
	@ResponseBody
	public ResultData doModify(@RequestParam Map<String, Object> param, HttpServletRequest req) {

		if (param.isEmpty()) {
			return new ResultData("F-2", "수정할 회원정보를 입력해주세요.");
		}

		int loginedDirectorId = (int) req.getAttribute("loginedDirectorId");
		param.put("id", loginedDirectorId);

		return directorService.modifyDirector(param);
	}

}
