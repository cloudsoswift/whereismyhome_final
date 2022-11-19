package com.ssafy.home.board.model.mapper;

import java.sql.SQLException;
import java.util.ArrayList;

import com.ssafy.home.board.model.BoardDTO;

public interface BoardMapper {
	ArrayList<BoardDTO> listBoard() throws SQLException;

	int writeArticle(BoardDTO dto) throws SQLException;

	BoardDTO viewArticle(int articleNo) throws SQLException;

	int deleteArticle(BoardDTO dto) throws SQLException;
	
	int updateArticle(BoardDTO dto) throws SQLException;

}
