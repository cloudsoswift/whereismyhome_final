package com.ssafy.home.qna.model.service;

import java.util.ArrayList;

import com.ssafy.home.board.model.BoardDTO;
import com.ssafy.home.qna.model.CommentDTO;

public interface CommentService {
	ArrayList<CommentDTO> listComment(int id) throws Exception;

	int writeComment(CommentDTO dto) throws Exception;

	int deleteComment(CommentDTO dto) throws Exception;
	
	int updateComment(CommentDTO dto) throws Exception;
}
