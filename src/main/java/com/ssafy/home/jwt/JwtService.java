package com.ssafy.home.jwt;

import java.util.Map;

import com.ssafy.home.user.model.UserDTO;

public interface JwtService {

	<T> String createAccessToken(UserDTO loginUser);
	<T> String createRefreshToken(UserDTO loginUser);
	<T> String create(UserDTO loginUser, String subject, long expir);
	Map<String, Object> get(String type);
	boolean checkToken(String jwt);
	UserDTO getUser(String type);
}
