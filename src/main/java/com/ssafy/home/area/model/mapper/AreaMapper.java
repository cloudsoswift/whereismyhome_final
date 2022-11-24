package com.ssafy.home.area.model.mapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import com.ssafy.home.area.model.AreaLikeDTO;


public interface AreaMapper {
	ArrayList<Map> getSido() throws SQLException;
	ArrayList<Map> getGugun(Map param) throws SQLException;
	ArrayList<Map> getDong(String code) throws SQLException;
	
	void addInterestArea(AreaLikeDTO dto) throws SQLException;
	void deleteInterestArea(Map map) throws SQLException;
}
