package com.ssafy.home.user.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.user.model.UserDTO;
import com.ssafy.home.user.model.UserInterestDTO;
import com.ssafy.home.user.model.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController{

	private UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/login")
	public String login() {
		return "user/login";
	}
//	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestParam Map<String, String> map, HttpSession session) {
		UserDTO user = null;
		try {
			user = userService.loginUser(map);
			if(user != null) {
				session.setAttribute("userInfo", user);
				user.setUserPassword("");
				return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return exceptionHandling(e);
		}
	}
	
	@GetMapping("/logout")
	public ResponseEntity<?> logout(HttpSession session) {
		session.invalidate();
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
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
