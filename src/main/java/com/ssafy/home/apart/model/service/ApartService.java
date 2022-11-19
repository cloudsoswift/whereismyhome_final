package com.ssafy.home.apart.model.service;

import java.util.ArrayList;
import java.util.Map;

import com.ssafy.home.apart.model.HouseDTO;
import com.ssafy.home.apart.model.HouseLikeDTO;

public interface ApartService {
	ArrayList<HouseDTO> searchApart(Map map) throws Exception;
	
	void addInterestApart(HouseLikeDTO apartDTO) throws Exception;
	
	void deleteInterestApart(Map map) throws Exception;
}