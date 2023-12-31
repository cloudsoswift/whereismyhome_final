package com.ssafy.home.board.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.model.BoardParameterDTO;
import com.ssafy.home.UnAuthorizedException;
import com.ssafy.home.board.model.BoardDTO;
import com.ssafy.home.board.model.service.BoardService;
import com.ssafy.home.board.model.service.BoardServiceImpl;
import com.ssafy.home.jwt.JwtService;
import com.ssafy.home.qna.model.QNADTO;
import com.ssafy.home.user.model.UserDTO;
import com.ssafy.home.user.model.service.UserService;

@RestController
@RequestMapping("/board")
public class BoardController{

	private BoardService service;
	private JwtService jwtService;

	@Autowired
	public BoardController(BoardService service, JwtService jwtService) {
		this.service = service;
		this.jwtService = jwtService;
	}

	// 글 목록 가져오기
	@GetMapping("/page/{pg}")
	public ResponseEntity<?> boardList(@PathVariable int pg, BoardParameterDTO boardParameterDTO) throws Exception {
		boardParameterDTO.setPg(pg);
		List<BoardDTO> list = service.listBoard(boardParameterDTO);
		int pgCount = ((service.getTotalCount() - 1) / boardParameterDTO.getSpp()) + 1;
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("pgCount", pgCount);

		try {
			if (list != null)
				return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
			else
				return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
		} catch (Exception e) {
			e.printStackTrace();
			return exceptionHandling(e);
		}
	}

	// 게시판 글 등록 요청
	@PostMapping("/")
	public ResponseEntity<?> writePost(@RequestBody BoardDTO board) {
		try {
			UserDTO user = jwtService.getUser("access-token");
			if(!user.equals(null) && user.getRole() == 1) {
				board.setUserId(user.getUserId());
				int cnt = service.writeArticle(board);
				if(cnt == 1)
				{
					return new ResponseEntity<String>("/board/", HttpStatus.OK);
				} else {
					return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return exceptionHandling(e);
		}
		return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
	}

	// 게시판 글 보기
	@GetMapping("/{id}")
	public ResponseEntity<?> getArticle(@PathVariable int id) throws Exception {
		BoardDTO dto = service.viewArticle(id);

		try {
			if (dto != null)
				return new ResponseEntity<BoardDTO>(dto, HttpStatus.OK);
			else
				return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
		} catch (Exception e) {
			e.printStackTrace();
			return exceptionHandling(e);
		}
	}

	// 게시판 글 삭제
	@DeleteMapping("/{id}") // 관리자 아무나
	public ResponseEntity<?> deletePost(@PathVariable Integer id) {
		try {
			UserDTO user = jwtService.getUser("access-token");
			
			if(!user.equals(null) && user.getRole() == 1) {
				BoardDTO dto = new BoardDTO();
				dto.setBoardNo(id);
				int cnt = service.deleteArticle(dto);
				if(cnt == 1)
				{
					return new ResponseEntity<String>("/board/", HttpStatus.OK);
				} else {
					return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
				}
			} 
		}catch (Exception e) {
			e.printStackTrace();
			return exceptionHandling(e);
		}
		return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
	}

	// 게시판 글 내용 수정 요청
	@PutMapping("/{id}") 
	public ResponseEntity<?> modifyPost(@RequestBody BoardDTO dto) { // boardNo, subject, content
		System.out.println(dto);
		try {
			UserDTO user = jwtService.getUser("access-token");
			if(!user.equals(null) && user.getRole() == 1) {
				dto.setUserId(user.getUserId());
				int cnt = service.updateArticle(dto);
				if(cnt == 1)
				{
					return new ResponseEntity<String>("/board/", HttpStatus.OK);
				} else {
					return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
				}
			} 
		} catch (Exception e) {
			e.printStackTrace();
			return exceptionHandling(e);
		}
		return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
	}

	private ResponseEntity<String> exceptionHandling(Exception e) {
		if(e instanceof UnAuthorizedException) {
			return new ResponseEntity<String>("로그인 토큰 만료.",HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
