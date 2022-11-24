package com.ssafy.home.apart.model.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import com.ssafy.home.apart.model.CommercialDTO;
import com.ssafy.home.apart.model.HouseDTO;
import com.ssafy.home.apart.model.HouseLikeDTO;

public interface ApartService {
	ArrayList<HouseDTO> searchApart(Map map) throws Exception;
	ArrayList<CommercialDTO> searchCommercial(Map map) throws Exception;
	ArrayList<Map<String, String>> searchDeal(Map map) throws Exception;
	ArrayList<String> searchArea(String aptCode) throws Exception;
	void addInterestApart(HouseLikeDTO apartDTO) throws Exception;	
	void deleteInterestApart(Map map) throws Exception;
}