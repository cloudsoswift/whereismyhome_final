package com.ssafy.home.area.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.apart.model.HouseDTO;
import com.ssafy.home.apart.model.HouseLikeDTO;
import com.ssafy.home.area.model.AreaLikeDTO;
import com.ssafy.home.area.model.service.AreaService;
import com.ssafy.home.board.model.service.BoardService;
import com.ssafy.home.jwt.JwtService;
import com.ssafy.home.user.model.UserDTO;



@RestController
@RequestMapping("/area")
public class AreaController extends HttpServlet {
	
	private AreaService service;
	private JwtService jwtService;

	@Autowired
	public AreaController(AreaService service, JwtService jwtService) {
		this.service = service;
		this.jwtService = jwtService;
	}

	@GetMapping("/{selid}/{regcode}")
	public ResponseEntity<?> getdongCode(@PathVariable String selid, @PathVariable String regcode) {
		System.out.println(selid + " " + regcode);
		List<Map> list = new ArrayList<Map>();
		if(selid.equals("sido")) {
			try {
				list = service.getSido(regcode);
//				System.out.println(list);
				if(list != null) {
					return new ResponseEntity<List<Map>>(list, HttpStatus.OK);
				} else {
					return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return exceptionHandling(e);
			}
		} else if(selid.equals("gugun")) {
			try {
				list = service.getGugun(regcode);
				if(list != null) {
					return new ResponseEntity<List<Map>>(list, HttpStatus.OK);
				} else {
					return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return exceptionHandling(e);
			}
		} else if(selid.equals("dong")) {
			try {
				list = service.getDong(regcode);
				if(list != null) {
					return new ResponseEntity<List<Map>>(list, HttpStatus.OK);
				} else {
					return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return exceptionHandling(e);
			}
		}
		return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/like/{dongCode}")
	public ResponseEntity<?> addInterestArea(@PathVariable String dongCode, HttpServletRequest req) {
		UserDTO user = jwtService.getUser("access-token");
		
		if(user != null) {
			AreaLikeDTO areaLikeDTO = new AreaLikeDTO();
			areaLikeDTO.setUser_id(user.getUserId());
			areaLikeDTO.setDongCode(dongCode + "00000");
			areaLikeDTO.setIp_address(req.getHeader("X-FORWARDED-FOR"));
			try {
				service.addInterestArea(areaLikeDTO);
				System.out.println("관심지역 등록 완료");
				return new ResponseEntity<Integer>(1, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return exceptionHandling(e);
			}
		}
		return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
	}
	
	@DeleteMapping("/like/{dongCode}")
	public ResponseEntity<?> removeInterestArea(@PathVariable String dongCode, HttpServletRequest req) {
		UserDTO user = jwtService.getUser("access-token");
		
		if(user != null) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("user_id", user.getUserId());
			map.put("dongCode", dongCode + "00000");
			try {
				service.deleteInterestArea(map);
				System.out.println("관심지역 삭제 완료");
				return new ResponseEntity<Integer>(1, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return exceptionHandling(e);
			}
		}
		return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
	}
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
