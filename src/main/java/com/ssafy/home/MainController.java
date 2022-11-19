package com.ssafy.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

//	@RequestMapping(value = "/", method = RequestMethod.GET)
	@GetMapping(value = {"", "/", "/index"})
	public String index() {
		return "index";
	}
	
	@GetMapping("/error/error")
	public String errorPage() {
		return "error/error";
	}
	
}
