package com.ssafy.home.user.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.jwt.JwtService;
import com.ssafy.home.user.model.UserDTO;
import com.ssafy.home.user.model.UserInterestDTO;
import com.ssafy.home.user.model.service.UserService;


@Controller
@RequestMapping("/user")
public class UserController{
	
	public static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	private JwtService jwtService;
	private UserService userService;

	@Autowired
	public UserController(UserService userService, JwtService jwtService) {
		this.userService = userService;
		this.jwtService = jwtService;
	}

	@GetMapping("/login")
	public String login() {
		return "user/login";
	}
//	
//	@PostMapping("/login")
//	public ResponseEntity<?> login(@RequestParam Map<String, String> map, HttpSession session) {
//		UserDTO user = null;
//		try {
//			user = userService.loginUser(map);
//			if(user != null) {
//				session.setAttribute("userInfo", user);
//				user.setUserPassword("");
//				return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
//			} else {
//				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return exceptionHandling(e);
//		}
//	}
	
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody UserDTO userDto) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			UserDTO loginUser = userService.login(userDto);
			if (loginUser != null) {
				String accessToken = jwtService.createAccessToken(loginUser);// key, data
				String refreshToken = jwtService.createRefreshToken(loginUser);// key, data
				System.out.println(accessToken);
				System.out.println(refreshToken);
				userService.saveRefreshToken(userDto.getUserId(), refreshToken);
				logger.debug("로그인 accessToken 정보 : {}", accessToken);
				logger.debug("로그인 refreshToken 정보 : {}", refreshToken);
				resultMap.put("access-token", accessToken);
				resultMap.put("refresh-token", refreshToken);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.OK;
			} else {
				resultMap.put("message", FAIL);
				status = HttpStatus.NOT_ACCEPTABLE;
			}
		} catch (Exception e) {
			logger.error("로그인 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@GetMapping("/info/{userid}")
	public ResponseEntity<Map<String, Object>> getInfo(@PathVariable("userid") String userId, HttpServletRequest request) {
//		logger.debug("userid : {} ", userid);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		if (jwtService.checkToken(request.getHeader("access-token"))) {
			logger.info("사용 가능한 토큰!!!");
			try {
//				로그인 사용자 정보.
				UserDTO memberDto = userService.userInfo(userId);
				resultMap.put("userInfo", memberDto);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				logger.error("정보조회 실패 : {}", e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			logger.error("사용 불가능 토큰!!!");
			resultMap.put("message", FAIL);
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@GetMapping("/logout")
	public ResponseEntity<?> removeToken() {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			userService.deleRefreshToken((String)jwtService.get("").get("userid"));
			resultMap.put("message", SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			logger.error("로그아웃 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);

	}

	@PostMapping("/refresh")
	public ResponseEntity<?> refreshToken(@RequestBody UserDTO userDto, HttpServletRequest request)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		String token = request.getHeader("refresh-token");
		logger.debug("token : {}, memberDto : {}", token, userDto);
		if (jwtService.checkToken(token)) {
			if (token.equals(userService.getRefreshToken(userDto.getUserId()))) {
				String accessToken = jwtService.createAccessToken(userDto);
				logger.debug("token : {}", accessToken);
				logger.debug("정상적으로 액세스토큰 재발급!!!");
				resultMap.put("access-token", accessToken);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			}
		} else {
			logger.debug("리프레쉬토큰도 사용불!!!!!!!");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
//	@GetMapping("/logout")
//	public ResponseEntity<?> logout(HttpSession session) {
//		session.invalidate();
//		return new ResponseEntity<Void>(HttpStatus.OK);
//	}
//	@GetMapping("/join")
//	public String mvJoin() {
//		return "user/signup";	
//	}
	
	@PostMapping("/join")
	public ResponseEntity<?> join(@RequestParam Map<String, String> map, Model model) {
		UserDTO user = new UserDTO();
		try {
			user.setUserId(map.get("ID"));
			user.setUserPassword(map.get("Password"));
			user.setUserName(map.get("Name"));
			user.setUserAddress(map.get("Address"));
			user.setUserPhone(map.get("Phone"));
			userService.registUser(user);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return exceptionHandling(e);
		}
	}
	
	@GetMapping("/mypage")
	public ResponseEntity<?> mypage(HttpSession session) {
		UserDTO user = (UserDTO) session.getAttribute("userInfo");
		if(user != null) {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("user", user);
			try {
				ArrayList<UserInterestDTO> list = userService.getUserInterest(user.getUserId());
				resultMap.put("list", list);
				return new ResponseEntity<Map>(resultMap, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return exceptionHandling(e);
			}			
		}
		return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
	}
	
	// 유저 정보 수정
	@PutMapping("/")
	@ResponseBody
	public ResponseEntity<?> modifyUser(@RequestBody Map<String, String> map, HttpSession session, Model model) {
		UserDTO user = (UserDTO) session.getAttribute("userInfo");
		if(user != null) {
			user.setUserPassword(map.get("Password"));
			user.setUserName(map.get("Name"));
			user.setUserAddress(map.get("Address"));
			user.setUserPhone(map.get("Phone"));
			int cnt = 0;
			try {
				cnt = userService.updateUser(user);
				if(cnt == 1) {
					session.setAttribute("userInfo", user);
					return new ResponseEntity<Integer>(cnt, HttpStatus.OK);
				} else {
					return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return exceptionHandling(e);
			}			
		}
		return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
	}
	
	// 유저 탈퇴
	@DeleteMapping("/")
	@ResponseBody
	public ResponseEntity<?> deleteUser(HttpSession session, Model model) {
		UserDTO user = (UserDTO) session.getAttribute("userInfo");
		if(user != null) {
			try {
				int cnt = userService.deleteUser(user);
				session.invalidate();
				if(cnt == 1) {
					return new ResponseEntity<Integer>(cnt, HttpStatus.OK);
				} else {
					return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
				}
			} catch (Exception e) {
				e.printStackTrace();	
				return exceptionHandling(e);
			}
		}
		return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
		
	}
	
	@ResponseBody
	private ResponseEntity<String> exceptionHandling(Exception e) {
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
