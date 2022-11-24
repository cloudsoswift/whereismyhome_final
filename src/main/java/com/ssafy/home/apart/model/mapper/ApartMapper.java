package com.ssafy.home.apart.model.mapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import com.ssafy.home.apart.model.HouseLikeDTO;
import com.ssafy.home.apart.model.CommercialDTO;
import com.ssafy.home.apart.model.HouseDTO;


public interface ApartMapper {
	ArrayList<HouseDTO> searchApart(Map map) throws SQLException;
	ArrayList<CommercialDTO> searchCommercial(Map map) throws SQLException;
	ArrayList<Map<String, String>> searchDeal(Map map) throws SQLException;
	ArrayList<String> searchArea(String aptCode) throws SQLException;
	void addInterestApart(HouseLikeDTO apartDTO) throws SQLException;
	void deleteInterestApart(Map map) throws SQLException;
}
