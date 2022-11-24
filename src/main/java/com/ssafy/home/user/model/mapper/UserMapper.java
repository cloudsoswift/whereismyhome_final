package com.ssafy.home.user.model.mapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import com.ssafy.home.user.model.UserDTO;
import com.ssafy.home.user.model.UserInterestDTO;

public interface UserMapper {
	void registUser(UserDTO dto) throws SQLException;

	// UserDTO loginUser(Map map) throws SQLException;
	
	//////
	public UserDTO login(UserDTO userDTO) throws SQLException;

	public void saveRefreshToken(Map<String, String> map) throws SQLException;
	
	public Object getRefreshToken(String userid) throws SQLException;
	
	public void deleteRefreshToken(Map<String, String> map) throws SQLException;
	
	public UserDTO userInfo(String userid) throws SQLException;
	///////
	
	int updateUser(UserDTO dto) throws SQLException;

	int deleteUser(UserDTO dto) throws SQLException;
	
	ArrayList<UserInterestDTO> getUserInterestArea(String id) throws SQLException;
	
	ArrayList<UserInterestDTO> getUserInterestApart(String id) throws SQLException;
}
