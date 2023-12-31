package com.ssafy.home.qna.model.service;

import java.util.ArrayList;

import com.ssafy.home.model.BoardParameterDTO;
import com.ssafy.home.qna.model.QNADTO;

public interface QNAService {
	ArrayList<QNADTO> listQNA(BoardParameterDTO boardParameterDTO) throws Exception;

	int writeArticle(QNADTO dto) throws Exception;

	QNADTO viewArticle(int articleNo) throws Exception;
	
	int getTotalCount() throws Exception;

	int deleteArticle(QNADTO dto) throws Exception;
	
	int updateArticle(QNADTO dto) throws Exception;
}
