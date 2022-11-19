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
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.board.model.BoardDTO;
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

	@Autowired
	public QNAController(QNAService qnaService, CommentService commentService) {
		this.qnaService = qnaService;
		this.commentService = commentService;
	}

	/*--------------------------------------QNA 관련------------------------------------*/

	// 글 목록 가져오기
	@GetMapping("/")
	public ResponseEntity<?> listQna() throws Exception {
		List<QNADTO> list = qnaService.listQNA();

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
	@PostMapping("/write")
	public ResponseEntity<?> writeQna(@RequestBody QNADTO qna, HttpSession session) {
		UserDTO user = (UserDTO) session.getAttribute("userInfo");
		if(user != null) {
			qna.setUserId(user.getUserId());
			try {
				int cnt = qnaService.writeArticle(qna);
				if(cnt == 1)
				{
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
	@PostMapping("/{qna}/write")
	public ResponseEntity<?> writeComment(@PathVariable("qna") int id, @RequestBody CommentDTO comment, HttpSession session) {
		UserDTO user = (UserDTO) session.getAttribute("userInfo");
		
		if(user != null) {
			comment.setUserId(user.getUserId());
			comment.setQnaNo(id);
			try {
				int cnt = commentService.writeComment(comment);
				if(cnt == 1)
				{
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
	public ResponseEntity<?> getArticle(@PathVariable("qna") int id) throws Exception {
		QNADTO dto = qnaService.viewArticle(id);

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
	public ResponseEntity<?> getComment(@PathVariable("qna") int id) throws Exception {
		List<CommentDTO> list = commentService.listComment(id);

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
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteQna(@PathVariable Integer id) {
		QNADTO dto = new QNADTO();
		dto.setQNANo(id);
		try {
			int cnt = qnaService.deleteArticle(dto);
			if(cnt == 1)
			{
				return new ResponseEntity<String>("/qna/", HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return exceptionHandling(e);
		}
	}

	// QNA 댓글 삭제
	@DeleteMapping("/{qna}/{comment}")
	public ResponseEntity<?> deleteComment(@PathVariable("qna") Integer qnaNo, @PathVariable("comment") Integer commentNo) {
		CommentDTO dto = new CommentDTO();
		dto.setQnaNo(qnaNo);
		dto.setCommentNo(commentNo);
		try {
			int cnt = commentService.deleteComment(dto);
			if(cnt == 1)
			{
				return new ResponseEntity<String>("/qna/", HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return exceptionHandling(e);
		}
	}

	// QNA 글 내용 수정 요청
	@PutMapping("/modify/{qna}")
	public ResponseEntity<?> updateQna(@PathVariable("qna") Integer qnaNo, @RequestBody QNADTO dto) {
		System.out.println(dto);
		try {
			dto.setQNANo(qnaNo);
			int cnt = qnaService.updateArticle(dto);
			if(cnt == 1)
			{
				return new ResponseEntity<Void>(HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return exceptionHandling(e);
		}
	}

	// QNA 댓글 내용 수정 요청
	@PutMapping("/modify/{qna}/{comment}")
	public ResponseEntity<?> updateComment(@PathVariable("qna") Integer qnaNo, @PathVariable("comment") Integer commentNo, @RequestBody CommentDTO dto) {
		System.out.println(dto);
		try {
			dto.setQnaNo(qnaNo);
			dto.setCommentNo(commentNo);
			int cnt = commentService.updateComment(dto);
			if(cnt == 1)
			{
				return new ResponseEntity<Void>(HttpStatus.OK);
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

	//	// QNA 글 내용 보기
	//	@GetMapping("/{id}")
	//	public String viewQNA(@PathVariable Integer id, Model model) {
	//		try {
	//			QNADTO dto = service.viewArticle(id);
	//			model.addAttribute("qna", dto);
	//			return "/qna/view";
	//		} catch (Exception e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//			model.addAttribute("msg", "글 읽기중 에러");
	//			return "error/error";
	//		}
	//	}

	//	// 글 쓰기
	//	@PostMapping("/write")
	//	public ResponseEntity<String> writeQna(@RequestBody QNADTO qna) throws Exception {
	//		if (service.writeArticle(qna)) {
	//			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	//		}
	//		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	//	}

	//	// 글 삭제
	//	@DeleteMapping("/{QNAno}")
	//	public ResponseEntity<String> deleteArticle(@PathVariable("QNAno") int QNAno) throws Exception {
	//		QNADTO dto = new QNADTO();
	//		dto.setQNANo(QNAno);
	//
	//		if (service.deleteArticle(dto)) {
	//			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	//		}
	//		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	//	}

	//	// 글 수정
	//	@PutMapping
	//	public ResponseEntity<String> modifyArticle(@RequestBody QNADTO dto) throws Exception {
	//		logger.info("modifyArticle - 호출 {}", dto);
	//
	//		if (service.updateArticle(dto)) {
	//			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	//		}
	//		return new ResponseEntity<String>(FAIL, HttpStatus.OK);
	//	}
}
