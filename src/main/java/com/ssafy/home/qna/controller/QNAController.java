package com.ssafy.home.qna.controller;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.board.model.BoardDTO;
import com.ssafy.home.jwt.JwtService;
import com.ssafy.home.qna.model.CommentDTO;
import com.ssafy.home.qna.model.QNADTO;
import com.ssafy.home.qna.model.service.CommentService;
import com.ssafy.home.qna.model.service.QNAService;
import com.ssafy.home.user.model.UserDTO;
//import com.ssafy.vue.controller.BoardController;
//import com.ssafy.vue.model.BoardDto;
//import com.ssafy.vue.model.BoardParameterDto;
//
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/qna")
public class QNAController {

	private QNAService qnaService;
	private CommentService commentService;
	private JwtService jwtService;

	@Autowired
	public QNAController(QNAService qnaService, CommentService commentService, JwtService jwtService) {
		this.qnaService = qnaService;
		this.commentService = commentService;
		this.jwtService = jwtService;
	}

	/*--------------------------------------QNA 관련------------------------------------*/

	// 글 목록 가져오기
	@GetMapping("/page/{idx}")
	public ResponseEntity<?> listQna(@PathVariable int idx) throws Exception {
		List<QNADTO> list = qnaService.listQNA(idx);

		try {
			if (list != null)
				return new ResponseEntity<List<QNADTO>>(list, HttpStatus.OK);
			else
				return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
		} catch (Exception e) {
			e.printStackTrace();
			return exceptionHandling(e);
		}
	}

	// QNA 글 등록 요청
	@PostMapping("/")
	public ResponseEntity<?> writeQna(@RequestBody QNADTO qna) {
		UserDTO user = jwtService.getUser();

		if (user != null) {
			try {
				qna.setUserId(user.getUserId());
				int cnt = qnaService.writeArticle(qna);
				if (cnt == 1) {
					return new ResponseEntity<String>("/qna/", HttpStatus.OK);
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

	// QNA 댓글 등록 요청
	@PostMapping("/{qna}/comment")
	public ResponseEntity<?> writeComment(@PathVariable("qna") int id, @RequestBody CommentDTO comment) {
		UserDTO user = jwtService.getUser();

		if (user != null) {
			try {
				comment.setUserId(user.getUserId());
				int cnt = commentService.writeComment(comment);
				if (cnt == 1) {
					return new ResponseEntity<String>("/qna/", HttpStatus.OK);
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

	// QNA 글 보기
	@GetMapping("/{qna}")
	public ResponseEntity<?> getArticle(@PathVariable("qna") int qna) throws Exception {
		QNADTO dto = qnaService.viewArticle(qna);

		try {
			if (dto != null)
				return new ResponseEntity<QNADTO>(dto, HttpStatus.OK);
			else
				return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
		} catch (Exception e) {
			e.printStackTrace();
			return exceptionHandling(e);
		}
	}

	// QNA 댓글 보기
	@GetMapping("/{qna}/comment")
	public ResponseEntity<?> getComment(@PathVariable("qna") int qna) throws Exception {
		List<CommentDTO> list = commentService.listComment(qna);

		try {
			if (list != null)
				return new ResponseEntity<List<CommentDTO>>(list, HttpStatus.OK);
			else
				return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
		} catch (Exception e) {
			e.printStackTrace();
			return exceptionHandling(e);
		}
	}

	// QNA 글 삭제
	@DeleteMapping("/{qna}") // 같은 유저만
	public ResponseEntity<?> deleteQna(@PathVariable int qna, @RequestBody QNADTO dto) {
		UserDTO user = jwtService.getUser();
		
		if (!user.equals(null)) {
			try {
				dto.setUserId(user.getUserId());
				int cnt = qnaService.deleteArticle(dto);
				if (cnt == 1) {
					return new ResponseEntity<String>("/qna/", HttpStatus.OK);
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

	// QNA 댓글 삭제
	@DeleteMapping("/{qna}/comment/{comment}")
	public ResponseEntity<?> deleteComment(@RequestParam int qna, @RequestParam int comment,
			@RequestBody CommentDTO dto) {
		UserDTO user = jwtService.getUser();

		if (!user.equals(null)) {
			try {
				dto.setUserId(user.getUserId());
				int cnt = commentService.deleteComment(dto);
				if (cnt == 1) {
					return new ResponseEntity<String>("/qna/", HttpStatus.OK);
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

	// QNA 글 내용 수정 요청
	@PutMapping("/{qna}")
	public ResponseEntity<?> updateQna(@RequestParam int qna, @RequestBody QNADTO dto) {
		UserDTO user = jwtService.getUser();
		
		if (!user.equals(null)) {
			try {
				dto.setUserId(user.getUserId());
				int cnt = qnaService.updateArticle(dto);
				if (cnt == 1) {
					return new ResponseEntity<Void>(HttpStatus.OK);
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

	// QNA 댓글 내용 수정 요청
	@PutMapping("/{qna}/comment/{comment}")
	public ResponseEntity<?> updateComment(@PathVariable("qna") Integer qna, @PathVariable("comment") Integer comment,
			@RequestBody CommentDTO dto) {
		UserDTO user = jwtService.getUser();
		
		if (!user.equals(null)) {
			try {
				dto.setUserId(user.getUserId());
				int cnt = commentService.updateComment(dto);
				if (cnt == 1) {
					return new ResponseEntity<Void>(HttpStatus.OK);
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

	private ResponseEntity<String> exceptionHandling(Exception e) {
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// // QNA 글 내용 보기
	// @GetMapping("/{id}")
	// public String viewQNA(@PathVariable Integer id, Model model) {
	// try {
	// QNADTO dto = service.viewArticle(id);
	// model.addAttribute("qna", dto);
	// return "/qna/view";
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// model.addAttribute("msg", "글 읽기중 에러");
	// return "error/error";
	// }
	// }

	// // 글 쓰기
	// @PostMapping("/write")
	// public ResponseEntity<String> writeQna(@RequestBody QNADTO qna) throws
	// Exception {
	// if (service.writeArticle(qna)) {
	// return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	// }
	// return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	// }

	// // 글 삭제
	// @DeleteMapping("/{QNAno}")
	// public ResponseEntity<String> deleteArticle(@PathVariable("QNAno") int QNAno)
	// throws Exception {
	// QNADTO dto = new QNADTO();
	// dto.setQNANo(QNAno);
	//
	// if (service.deleteArticle(dto)) {
	// return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	// }
	// return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	// }

	// // 글 수정
	// @PutMapping
	// public ResponseEntity<String> modifyArticle(@RequestBody QNADTO dto) throws
	// Exception {
	// logger.info("modifyArticle - 호출 {}", dto);
	//
	// if (service.updateArticle(dto)) {
	// return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	// }
	// return new ResponseEntity<String>(FAIL, HttpStatus.OK);
	// }
}
