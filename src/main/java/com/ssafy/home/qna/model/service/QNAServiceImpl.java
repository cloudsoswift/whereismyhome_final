package com.ssafy.home.qna.model.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.home.qna.model.QNADTO;
import com.ssafy.home.qna.model.mapper.QNAMapper;

@Service
public class QNAServiceImpl implements QNAService {
	
	private QNAMapper mapper;
	
	@Autowired
	private QNAServiceImpl(QNAMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public ArrayList<QNADTO> listQNA() throws Exception {
		return mapper.listQNA();
	}
	@Override
	public int writeArticle(QNADTO dto) throws Exception {
		return mapper.writeArticle(dto);
	}
	@Override
	public QNADTO viewArticle(int boardNo) throws Exception {
		return mapper.viewArticle(boardNo);
	}
	@Override
	public int updateArticle(QNADTO dto) throws Exception {
		return mapper.updateArticle(dto);
	}
	@Override
	public int deleteArticle(QNADTO dto) throws Exception {
		return mapper.deleteArticle(dto);
	}

}
