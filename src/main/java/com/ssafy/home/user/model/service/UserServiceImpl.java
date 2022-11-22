package com.ssafy.home.user.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.home.user.model.UserDTO;
import com.ssafy.home.user.model.UserInterestDTO;
import com.ssafy.home.user.model.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {
	
	private UserMapper mapper = null;
	
	private UserServiceImpl(UserMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public void registUser(UserDTO dto) throws Exception {
		mapper.registUser(dto);
	}

	///////////////////////////////////
	@Override
	public UserDTO login(UserDTO userDTO) throws Exception {
		System.out.println(userDTO);
		if (userDTO.getUserId() == null || userDTO.getUserPassword() == null)
			return null;
		return mapper.login(userDTO);
	}
	
	public void saveRefreshToken(String userid, String refreshToken) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userid);
		map.put("token", refreshToken);
		mapper.saveRefreshToken(map);
	}
	
	@Override
	public Object getRefreshToken(String userid) throws Exception {
		return mapper.getRefreshToken(userid);
	}

	@Override
	public void deleRefreshToken(String userid) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userid);
		map.put("token", null);
		System.out.println(map);
		mapper.deleteRefreshToken(map);
	}
	
	@Override
	public UserDTO userInfo(String userid) throws Exception {
		return mapper.userInfo(userid);
	}
	//////////////////////////////////
	
//	@Override
//	public UserDTO loginUser(Map<String, String> map) throws Exception {
//		return mapper.loginUser(map);
//	}

	@Override
	public int updateUser(UserDTO dto) throws Exception {
		return mapper.updateUser(dto);
	}

	@Override
	public int deleteUser(UserDTO dto) throws Exception{
		return mapper.deleteUser(dto);
	}
	
	@Override
	public ArrayList<UserInterestDTO> getUserInterest(String id) throws Exception{
		ArrayList<UserInterestDTO> list = new ArrayList<UserInterestDTO>();
		list.addAll(mapper.getUserInterestArea(id));
		list.addAll(mapper.getUserInterestDeal(id));
		return list;
	}
	

}
