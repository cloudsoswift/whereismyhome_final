package com.ssafy.home.board.model.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.home.board.model.BoardDTO;
import com.ssafy.home.board.model.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService {
	
	private BoardMapper mapper;
	
	@Autowired
	private BoardServiceImpl(BoardMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public ArrayList<BoardDTO> listBoard() throws Exception {
		return mapper.listBoard();
	}
	@Override
	public int writeArticle(BoardDTO dto) throws Exception {
		return mapper.writeArticle(dto);
	}
	@Override
	public BoardDTO viewArticle(int boardNo) throws Exception {
		return mapper.viewArticle(boardNo);
	}
	@Override
	public int updateArticle(BoardDTO dto) throws Exception {
		return mapper.updateArticle(dto);
	}
	@Override
	public int deleteArticle(BoardDTO dto) throws Exception {
		return mapper.deleteArticle(dto);
	}

}
