package com.ssafy.home.user.model.service;

import java.util.ArrayList;
import java.util.Map;

import com.ssafy.home.user.model.UserDTO;
import com.ssafy.home.user.model.UserInterestDTO;

public interface UserService {
	void registUser(UserDTO dto) throws Exception;

//	UserDTO loginUser(Map<String, String> map) throws Exception;
	
	///////////////////////
	UserDTO login(UserDTO userDTO) throws Exception;
	
	public void saveRefreshToken(String userid, String refreshToken) throws Exception;
	
	public Object getRefreshToken(String userid) throws Exception;
	
	public void deleRefreshToken(String userid) throws Exception;
	
	public UserDTO userInfo(String userid) throws Exception;
	
	int idCheck(String id) throws Exception;
	///////////////////////
	
	int updateUser(UserDTO dto) throws Exception;

	int deleteUser(UserDTO dto) throws Exception;
	
	ArrayList<UserInterestDTO> getUserInterest(String id) throws Exception;
}
