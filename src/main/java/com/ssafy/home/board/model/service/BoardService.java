package com.ssafy.home.board.model.service;

import java.sql.SQLException;
import java.util.ArrayList;

import com.ssafy.home.model.BoardParameterDTO;
import com.ssafy.home.board.model.BoardDTO;

public interface BoardService {
	ArrayList<BoardDTO> listBoard(BoardParameterDTO boardParameterDTO) throws Exception;

	int writeArticle(BoardDTO dto) throws Exception;

	BoardDTO viewArticle(int articleNo) throws Exception;
	
	int getTotalCount() throws Exception;

	int deleteArticle(BoardDTO dto) throws Exception;
	
	int updateArticle(BoardDTO dto) throws Exception;
}
