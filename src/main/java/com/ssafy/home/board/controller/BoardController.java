package com.ssafy.home.board.controller;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.board.model.BoardDTO;
import com.ssafy.home.board.model.service.BoardService;
import com.ssafy.home.board.model.service.BoardServiceImpl;
import com.ssafy.home.qna.model.QNADTO;
import com.ssafy.home.user.model.UserDTO;

@RestController
@RequestMapping("/board")
public class BoardController{
	
	private BoardService service;

	@Autowired
	public BoardController(BoardService service) {
		this.service = service;
	}

	// 글 목록 가져오기
	@GetMapping("/")
	public ResponseEntity<?> boardList() throws Exception {
		List<BoardDTO> list = service.listBoard();
		
		try {
			if (list != null)
				return new ResponseEntity<List<BoardDTO>>(list, HttpStatus.OK);
			else
				return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
		} catch (Exception e) {
			e.printStackTrace();
			return exceptionHandling(e);
		}
	}

	// 게시판 글 등록 요청
	@PostMapping("/write")
	public ResponseEntity<?> writePost(@RequestBody BoardDTO board, HttpSession session, Model model) {
		UserDTO user = (UserDTO) session.getAttribute("userInfo");
		if(user != null) {
			board.setUserId(user.getUserId());
			try {
				int cnt = service.writeArticle(board);
				if(cnt == 1)
				{
					return new ResponseEntity<String>("/board/", HttpStatus.OK);
				} else {
					return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
				}
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("msg", "글 작성중 에러");
				return exceptionHandling(e);
			}
		}
		return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
	}

	// 게시판 글 보기
	@GetMapping("/{id}")
	public ResponseEntity<?> getArticle(@PathVariable("id") int id) throws Exception {
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
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletePost(@PathVariable Integer id) {
		BoardDTO dto = new BoardDTO();
		dto.setBoardNo(id);
		try {
			int cnt = service.deleteArticle(dto);
			if(cnt == 1)
			{
				return new ResponseEntity<String>("/board/", HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return exceptionHandling(e);
		}
	}

	// 게시판 글 내용 수정 요청
	@PutMapping("/modify") 
	public ResponseEntity<?> modifyPost(@RequestBody BoardDTO dto) {
		System.out.println(dto);
		try {
			int cnt = service.updateArticle(dto);
			if(cnt == 1)
			{
				return new ResponseEntity<String>("/board/", HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return exceptionHandling(e);
		}
	}

	private ResponseEntity<String> exceptionHandling(Exception e) {
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
