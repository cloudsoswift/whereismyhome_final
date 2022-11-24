package com.ssafy.home.area.model.service;

import java.util.ArrayList;
import java.util.Map;

import com.ssafy.home.area.model.AreaLikeDTO;

public interface AreaService {
	ArrayList<Map> getSido(String code) throws Exception;
	ArrayList<Map> getGugun(Map param) throws Exception;
	ArrayList<Map> getDong(String code) throws Exception;
	
	void addInterestArea(AreaLikeDTO dto) throws Exception;
	void deleteInterestArea(Map map) throws Exception;
}
