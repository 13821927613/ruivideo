package com.rui.service;

import com.rui.pojo.Users;
import com.rui.pojo.UsersReport;

public interface UserService {
	
	/**
	 * @Description: 判断用户名是否存在
	 */
	boolean queryUsernameIsExist(String username);
	
	/**
	 * @Description: 保存用户(用户注册)
	 */
	void saveUser(Users user);

	/**
	 * 根据用户名密码查询用户
	 * @param username 用户名
	 * @param password 密码
	 * @return
	 */
	Users queryUsernamePassword(String username, String password);

	/**
	 * 更新用户信息
	 * @param user
	 */
	void updateUserInfo(Users user);

	/**
	 * 查询用户信息
	 * @param userId 用户id
	 * @return
	 */
	Users queryUserInfo(String userId);
}
