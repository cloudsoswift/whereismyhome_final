package com.ssafy.home.qna.model.mapper;

import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.home.qna.model.QNADTO;

@Mapper
public interface QNAMapper {
	ArrayList<QNADTO> listQNA(int idx) throws SQLException;

	int writeArticle(QNADTO dto) throws SQLException;

	QNADTO viewArticle(int articleNo) throws SQLException;

	int deleteArticle(QNADTO dto) throws SQLException;
	
	int updateArticle(QNADTO dto) throws SQLException;

}
