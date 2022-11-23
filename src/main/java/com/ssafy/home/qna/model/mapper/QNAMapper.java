package com.ssafy.home.qna.model.mapper;

import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.home.BoardParameterDTO;
import com.ssafy.home.qna.model.QNADTO;

@Mapper
public interface QNAMapper {
	ArrayList<QNADTO> listQNA(BoardParameterDTO boardParameterDTO) throws SQLException;

	int writeArticle(QNADTO dto) throws SQLException;

	QNADTO viewArticle(int articleNo) throws SQLException;
	
	int getTotalCount() throws SQLException;

	int deleteArticle(QNADTO dto) throws SQLException;
	
	int updateArticle(QNADTO dto) throws SQLException;

}
