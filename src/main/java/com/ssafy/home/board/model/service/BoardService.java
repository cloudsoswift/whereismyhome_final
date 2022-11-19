package com.ssafy.home.board.model.service;

import java.util.ArrayList;

import com.ssafy.home.board.model.BoardDTO;

public interface BoardService {
	ArrayList<BoardDTO> listBoard() throws Exception;

	int writeArticle(BoardDTO dto) throws Exception;

	BoardDTO viewArticle(int articleNo) throws Exception;

	int deleteArticle(BoardDTO dto) throws Exception;
	
	int updateArticle(BoardDTO dto) throws Exception;
}
