package com.ssafy.home.user.model.mapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import com.ssafy.home.user.model.UserDTO;
import com.ssafy.home.user.model.UserInterestDTO;

public interface UserMapper {
	void registUser(UserDTO dto) throws SQLException;

	UserDTO loginUser(Map map) throws SQLException;

	int updateUser(UserDTO dto) throws SQLException;

	int deleteUser(UserDTO dto) throws SQLException;
	
	ArrayList<UserInterestDTO> getUserInterestArea(String id) throws SQLException;
	
	ArrayList<UserInterestDTO> getUserInterestDeal(String id) throws SQLException;
}
