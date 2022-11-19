package com.ssafy.home.qna.model.mapper;

import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.home.board.model.BoardDTO;
import com.ssafy.home.qna.model.CommentDTO;

@Mapper
public interface CommentMapper {
	ArrayList<CommentDTO> listComment(int id) throws SQLException;

	int writeComment(CommentDTO dto) throws SQLException;

	int deleteComment(CommentDTO dto) throws SQLException;
	
	int updateComment(CommentDTO dto) throws SQLException;

}
