package com.ssafy.home.qna.model.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.home.board.model.BoardDTO;
import com.ssafy.home.board.model.mapper.BoardMapper;
import com.ssafy.home.qna.model.CommentDTO;
import com.ssafy.home.qna.model.mapper.CommentMapper;

@Service
public class CommentServiceImpl implements CommentService {
	
	private CommentMapper mapper;
	
	@Autowired
	private CommentServiceImpl(CommentMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public ArrayList<CommentDTO> listComment(int id) throws Exception {
		return mapper.listComment(id);
	}
	@Override
	public int writeComment(CommentDTO dto) throws Exception {
		return mapper.writeComment(dto);
	}
	@Override
	public int updateComment(CommentDTO dto) throws Exception {
		return mapper.updateComment(dto);
	}
	@Override
	public int deleteComment(CommentDTO dto) throws Exception {
		return mapper.deleteComment(dto);
	}

}
