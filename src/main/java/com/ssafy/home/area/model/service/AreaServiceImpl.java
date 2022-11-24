package com.ssafy.home.area.model.service;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.home.area.model.AreaLikeDTO;
import com.ssafy.home.area.model.mapper.AreaMapper;

@Service
public class AreaServiceImpl implements AreaService {
	
	@Autowired
	private AreaMapper areaMapper;
	
	@Override
	public ArrayList<Map> getSido(String code) throws Exception {
		return areaMapper.getSido();
	}

	@Override
	public ArrayList<Map> getGugun(Map param) throws Exception {
		return areaMapper.getGugun(param);
	}

	@Override
	public ArrayList<Map> getDong(String code) throws Exception {
		return areaMapper.getDong(code);
	}

	@Override
	public void addInterestArea(AreaLikeDTO dto) throws Exception {
		areaMapper.addInterestArea(dto);
	}

	@Override
	public void deleteInterestArea(Map map) throws Exception {
		areaMapper.deleteInterestArea(map);
	}
	

}
