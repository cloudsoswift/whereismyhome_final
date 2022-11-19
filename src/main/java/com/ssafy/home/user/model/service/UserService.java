package com.ssafy.home.user.model.service;

import java.util.ArrayList;
import java.util.Map;

import com.ssafy.home.user.model.UserDTO;
import com.ssafy.home.user.model.UserInterestDTO;

public interface UserService {
	void registUser(UserDTO dto) throws Exception;

	UserDTO loginUser(Map<String, String> map) throws Exception;

	int updateUser(UserDTO dto) throws Exception;

	int deleteUser(UserDTO dto) throws Exception;
	
	ArrayList<UserInterestDTO> getUserInterest(String id) throws Exception;
}
