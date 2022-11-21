package com.ssafy.home.apart.model.service;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.home.apart.model.HouseDTO;
import com.ssafy.home.apart.model.HouseLikeDTO;
import com.ssafy.home.apart.model.mapper.ApartMapper;

@Service
public class ApartServiceImpl implements ApartService {
	
	@Autowired
	private ApartMapper apartMapper;

	@Override
	public ArrayList<HouseDTO> searchApart(Map map) throws Exception {
		return apartMapper.searchApart(map);
	}
	
	@Override
	public ArrayList<Map<String, String>> searchDeal(Map map) throws Exception {
		return apartMapper.searchDeal(map);
	}

	@Override
	public void addInterestApart(HouseLikeDTO apartDTO) throws Exception {
		apartMapper.addInterestApart(apartDTO);
	}

	@Override
	public void deleteInterestApart(Map map) throws Exception {
		apartMapper.deleteInterestApart(map);
	}
	

}
