package com.example.demo.services;

import com.example.demo.models.dtos.GetUserDataDTO;
import com.example.demo.models.dtos.UserDataDTO;
import com.example.demo.models.entities.User;

public interface UserService {
	void register(UserDataDTO registerInfo) throws Exception;
	User findOneById(String id);
	void changeUserName(String id, String username) throws Exception;
	void deleteById(String id) throws Exception;
	void changePassword(String id, String password) throws Exception;
}
