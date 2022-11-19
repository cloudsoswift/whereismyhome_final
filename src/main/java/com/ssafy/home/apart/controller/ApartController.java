package com.ssafy.home.apart.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.resource.HttpResource;

import com.ssafy.home.apart.model.HouseDTO;
import com.ssafy.home.apart.model.HouseLikeDTO;
import com.ssafy.home.apart.model.service.ApartService;
import com.ssafy.home.apart.model.service.ApartServiceImpl;
import com.ssafy.home.user.model.UserDTO;


@Controller
@RequestMapping("/apart")
public class ApartController extends HttpServlet {
	
	ApartService service;
	@Autowired
	public ApartController(ApartService service) {
		this.service = service;
	}

	@GetMapping("/")
	public String apartList() {
		return "apart/aptlist";
	}
	
	@GetMapping("/list/{dongCode}/{year}/{month}")
	@ResponseBody
	public ResponseEntity<?> search(@PathVariable String dongCode, @PathVariable String year, @PathVariable String month) {
		Map<String, String> map = new HashMap<String, String>();
		List<HouseDTO> list = new ArrayList<HouseDTO>();
		map.put("code", dongCode.substring(0, 5));
		map.put("year", year);
		map.put("month", month);
		try {
			list = service.searchApart(map);
			System.out.println(list);
			if(list != null) {
//				for(HouseDTO dto : list) {
//					System.out.println(dto);
//				}
				return new ResponseEntity<List<HouseDTO>>(list, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return exceptionHandling(e);
		}
	}
	
	@PostMapping("/like/{no}")
	@ResponseBody
	public ResponseEntity<?> addInterestApart(@PathVariable String no, HttpSession session, HttpServletRequest req) {
		UserDTO user = (UserDTO) session.getAttribute("userInfo");
		if(user != null) {
			HouseLikeDTO houseLikeDTO = new HouseLikeDTO();
			houseLikeDTO.setUser_id(user.getUserId());
			houseLikeDTO.setNo(no);
			houseLikeDTO.setIp_address(req.getHeader("X-FORWARDED-FOR"));
			try {
				service.addInterestApart(houseLikeDTO);
				System.out.println("관심매매 등록 완료");
				return new ResponseEntity<Integer>(1, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return exceptionHandling(e);
			}
		}
		return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
	}
	
	@DeleteMapping("/like/{no}")
	@ResponseBody
	public ResponseEntity<?> removeInterestApart(@PathVariable String no, HttpSession session) {
		UserDTO user = (UserDTO) session.getAttribute("userInfo");
		if(user != null) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("user_id", user.getUserId());
			map.put("no", no);
			try {
				service.deleteInterestApart(map);
				System.out.println("관심매매 삭제 완료");
				return new ResponseEntity<Integer>(1, HttpStatus.OK);
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
